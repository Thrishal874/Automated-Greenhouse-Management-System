package lk.ijse.gdse.cropservice.service.impl;

import lk.ijse.gdse.cropservice.dto.CropDTO;
import lk.ijse.gdse.cropservice.entity.Crop;
import lk.ijse.gdse.cropservice.entity.CropStatus;
import lk.ijse.gdse.cropservice.repository.CropRepository;
import lk.ijse.gdse.cropservice.service.CropService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CropServiceImpl implements CropService {

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CropDTO registerCrop(CropDTO cropDTO) {
        if (cropDTO.getCropName() == null || cropDTO.getCropName().isEmpty()) {
            throw new IllegalArgumentException("Crop name cannot be null or empty");
        }

        if (cropDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        if (cropDTO.getUserId() == null || cropDTO.getUserId().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        if (cropDTO.getStatus() == null) {
            throw new IllegalArgumentException("Crop status cannot be null");
        }

        cropDTO.setPlantedDate(LocalDateTime.now());

        Crop crop = modelMapper.map(cropDTO, Crop.class);
        Crop savedCrop = cropRepository.save(crop);
        return modelMapper.map(savedCrop, CropDTO.class);
    }

    @Override
    public CropDTO updateCropStatus(String id, CropStatus status) {
        Crop crop = cropRepository.findById(id).orElse(null);
        if (crop == null) {
            throw new IllegalArgumentException("Crop with id " + id + " not found");
        }

        if (status == null) {
            throw new IllegalArgumentException("Crop status cannot be null");
        }

        if (crop.getStatus() == CropStatus.HARVESTED) {
            throw new IllegalStateException("Cannot change status of a harvested crop");
        }

        if (crop.getStatus() == CropStatus.VEGETATIVE && status == CropStatus.SEEDLING) {
            throw new IllegalStateException("Cannot go back to SEEDLING from VEGETATIVE");
        }

        crop.setStatus(status);
        Crop updatedCrop = cropRepository.save(crop);
        return modelMapper.map(updatedCrop, CropDTO.class);
    }

    @Override
    public List<CropDTO> getAllCrops(String userId) {

        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        List<Crop> crops = cropRepository.findByUserId(userId);
        return crops.stream()
                .map(crop -> modelMapper.map(crop, CropDTO.class))
                .toList();

    }


}
