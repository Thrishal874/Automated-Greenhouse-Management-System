package lk.ijse.gdse.zoneservice.repository;

import lk.ijse.gdse.zoneservice.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone, String> {

}
