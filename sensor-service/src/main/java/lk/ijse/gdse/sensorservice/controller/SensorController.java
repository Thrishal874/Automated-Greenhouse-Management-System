package lk.ijse.gdse.sensorservice.controller;

import lk.ijse.gdse.sensorservice.dto.DeviceDTO;
import lk.ijse.gdse.sensorservice.dto.TelemetryData;
import lk.ijse.gdse.sensorservice.services.ExternalAuthService;
import lk.ijse.gdse.sensorservice.services.impl.ExternalAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/sensors")
@CrossOrigin({"*"})
public class SensorController {

    @Autowired
    private ExternalAuthService externalAuthService;

    private final Map<String, TelemetryData> latestReadings = new ConcurrentHashMap<>();

    @GetMapping("/latest")
    public ResponseEntity<Map<String, TelemetryData>> getLatest() {
        return ResponseEntity.ok(latestReadings);
    }

    public void updateReading(String zoneId, TelemetryData data) {
        latestReadings.put(zoneId, data);
    }

    @PostMapping("/register")
    public ResponseEntity<DeviceDTO> registerDevice(@RequestBody DeviceDTO deviceDTO) {
        DeviceDTO registeredDevice = externalAuthService.registerDeviceAtExternalApi(deviceDTO);
        return ResponseEntity.ok(registeredDevice);
    }
}
