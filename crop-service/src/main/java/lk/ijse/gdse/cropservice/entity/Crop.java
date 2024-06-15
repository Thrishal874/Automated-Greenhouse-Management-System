package lk.ijse.gdse.cropservice.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String cropName;
    private int quantity;
    private String userId;
    @Enumerated(EnumType.STRING)
    private CropStatus status;
    private LocalDateTime plantedDate;

    public Crop(String id, String cropName, int quantity, String userId, CropStatus status, LocalDateTime plantedDate) {
        this.id = id;
        this.cropName = cropName;
        this.quantity = quantity;
        this.userId = userId;
        this.status = status;
        this.plantedDate = plantedDate;
    }

    public Crop() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CropStatus getStatus() {
        return status;
    }

    public void setStatus(CropStatus status) {
        this.status = status;
    }

    public LocalDateTime getPlantedDate() {
        return plantedDate;
    }

    public void setPlantedDate(LocalDateTime plantedDate) {
        this.plantedDate = plantedDate;
    }
}