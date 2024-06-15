package lk.ijse.gdse.zoneservice.services.impl;

import lk.ijse.gdse.zoneservice.Client.SensorClient;
import lk.ijse.gdse.zoneservice.dto.ZoneDTO;
import lk.ijse.gdse.zoneservice.entity.Zone;
import lk.ijse.gdse.zoneservice.repository.ZoneRepository;
import lk.ijse.gdse.zoneservice.services.ZoneService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SensorClient sensorClient;

    @Override
    public ZoneDTO saveZone(ZoneDTO zoneDTO) {


        if (zoneDTO.getName() == null || zoneDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Zone name cannot be null or empty");
        }

        if (zoneDTO.getMinTemp() >= zoneDTO.getMaxTemp()) {
            throw new IllegalArgumentException("Minimum temperature must be strictly less than maximum temperature");
        }

        String uniqueZoneId = UUID.randomUUID().toString();
        zoneDTO.setId(uniqueZoneId);

        try {

            Map<String, String> request = new HashMap<>();
            request.put("name", zoneDTO.getName() + "-Sensor");
            request.put("zoneId", uniqueZoneId);


            Map<String, Object> response = sensorClient.registerDevice(request);

            if (response != null && response.get("deviceId") != null) {
                String generatedDeviceId = response.get("deviceId").toString();
                zoneDTO.setDeviceId(generatedDeviceId);

                if (response.get("userId") != null) {
                    zoneDTO.setUserId(response.get("userId").toString());
                }

            } else {
                throw new RuntimeException("Sensor Service did not return a valid Device ID");
            }

        } catch (Exception e) {

            throw new RuntimeException("Communication with Sensor Service failed: " + e.getMessage());
        }

        Zone zone = modelMapper.map(zoneDTO, Zone.class);

        Zone savedZone = zoneRepository.save(zone);

        return modelMapper.map(savedZone, ZoneDTO.class);

    }

    @Override
    public ZoneDTO getZoneById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid zone ID");
        }

        Zone zone = zoneRepository.findById(id.trim())
                .orElseThrow(() -> new IllegalArgumentException("Zone not found with ID: " + id));

        return modelMapper.map(zone, ZoneDTO.class);
    }

    @Override
    public String updateZone(String id, ZoneDTO zoneDTO) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid zone ID");
        }

        if (zoneDTO.getName() == null || zoneDTO.getName().isEmpty() || zoneDTO.getDeviceId() == null || zoneDTO.getDeviceId().isEmpty() ) {
            throw new IllegalArgumentException("Zone name or deviceId cannot be null or empty");
        }

        if (zoneDTO.getMinTemp() >= zoneDTO.getMaxTemp()) {
            throw new IllegalArgumentException("Minimum temperature must be strictly less than maximum temperature");
        }

        Zone existingZone = zoneRepository.findById(id.trim())
                .orElseThrow(() -> new IllegalArgumentException("Zone not found with ID: " + id));

        existingZone.setName(zoneDTO.getName());
        existingZone.setMinTemp(zoneDTO.getMinTemp());
        existingZone.setMaxTemp(zoneDTO.getMaxTemp());
        existingZone.setDeviceId(zoneDTO.getDeviceId());

        zoneRepository.save(existingZone);

        return "Zone updated successfully";
    }

    @Override
    public boolean deleteZone(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid zone ID");
        }

        if (!zoneRepository.existsById(id)) {
            throw new IllegalArgumentException("Zone not found with ID: " + id);
        }

        zoneRepository.deleteById(id.trim());
        return true;
    }

    @Override
    public List<ZoneDTO> findAll() {
        List<Zone> zones = zoneRepository.findAll();
        return zones.stream()
                .map(zone -> modelMapper.map(zone, ZoneDTO.class))
                .toList();
    }
}
