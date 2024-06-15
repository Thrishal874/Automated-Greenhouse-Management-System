package lk.ijse.gdse.cropservice.service;

import lk.ijse.gdse.cropservice.dto.CropDTO;
import lk.ijse.gdse.cropservice.entity.CropStatus;

import java.util.List;


public interface CropService {
    CropDTO registerCrop(CropDTO cropDTO);

    CropDTO updateCropStatus(String id, CropStatus status);

    List<CropDTO> getAllCrops(String userId);
}
