package lk.ijse.gdse.sensorservice.dto;

public class DeviceDTO {
    private String deviceId;
    private String name;
    private String zoneId;
    private String userId;

    public DeviceDTO(String deviceId, String name, String zoneId, String userId) {
        this.deviceId = deviceId;
        this.name = name;
        this.zoneId = zoneId;
        this.userId = userId;
    }

    public DeviceDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
