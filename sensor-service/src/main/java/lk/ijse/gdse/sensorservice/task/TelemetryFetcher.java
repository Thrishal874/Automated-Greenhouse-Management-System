package lk.ijse.gdse.sensorservice.task;

import lk.ijse.gdse.sensorservice.client.AutomationClient;
import lk.ijse.gdse.sensorservice.client.ZoneClient;
import lk.ijse.gdse.sensorservice.controller.SensorController;
import lk.ijse.gdse.sensorservice.dto.TelemetryData;
import lk.ijse.gdse.sensorservice.dto.ZoneDTO;
import lk.ijse.gdse.sensorservice.services.ExternalAuthService;
import lk.ijse.gdse.sensorservice.services.impl.ExternalAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class TelemetryFetcher {

    @Autowired
    private ExternalAuthService authService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SensorController sensorController;

    @Autowired
    private ZoneClient zoneClient;

    @Autowired
    private AutomationClient automationClient;

    @Value("${external.iot.base-url}")
    private String baseUrl;

    @Scheduled(fixedRate = 10000)
    public void fetch() {
        List<ZoneDTO> zones;
        try {
            zones = zoneClient.getAllZones();
        } catch (Exception e) {
            System.err.println("Could not fetch zones from zone-service: " + e.getMessage());
            return;
        }

        if (zones == null || zones.isEmpty()) return;

        String token = authService.getAccessToken();
        if (token == null) return;

        for (ZoneDTO zone : zones) {
            String deviceId = zone.getDeviceId();

            if (deviceId == null || deviceId.isEmpty()) continue;

            String url = baseUrl + "/devices/telemetry/" + deviceId;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            try {
                ResponseEntity<TelemetryData> response = restTemplate.exchange(url, HttpMethod.GET, entity, TelemetryData.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    TelemetryData data = response.getBody();

                    data.setZoneId(zone.getId());

                    sensorController.updateReading(zone.getId(), data);

                    automationClient.sendToAutomation(data);

                    System.out.println("Zone: " + zone.getName() + " | Temp: " + data.getValue().getTemperature());

                }
            } catch (HttpClientErrorException.Unauthorized e) {
                authService.refreshAccessToken();
                System.out.println("Token expired. Refreshing...");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}