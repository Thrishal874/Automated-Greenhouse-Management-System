package lk.ijse.gdse.zoneservice.dto;

import jakarta.persistence.*;

public class ZoneDTO {
    private String id;
    @Column(unique = true)
    private String name;
    private double minTemp;
    private double maxTemp;
    private String deviceId;
    private String userId;

    public ZoneDTO(String id, String name, double minTemp, double maxTemp, String deviceId, String userId) {
        this.id = id;
        this.name = name;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.deviceId = deviceId;
        this.userId = userId;
    }

    public ZoneDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
