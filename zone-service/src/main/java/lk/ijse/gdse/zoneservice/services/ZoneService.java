package lk.ijse.gdse.zoneservice.services;

import lk.ijse.gdse.zoneservice.dto.ZoneDTO;

import java.util.List;

public interface ZoneService {
    ZoneDTO saveZone(ZoneDTO zoneDTO);

    ZoneDTO getZoneById(String id);

    String updateZone(String id, ZoneDTO zoneDTO);

    boolean deleteZone(String id);

    List<ZoneDTO> findAll();
}
