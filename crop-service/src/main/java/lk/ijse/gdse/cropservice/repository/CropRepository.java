package lk.ijse.gdse.cropservice.repository;

import lk.ijse.gdse.cropservice.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CropRepository extends JpaRepository<Crop, String> {
    List<Crop> findByUserId(String userId);

}
