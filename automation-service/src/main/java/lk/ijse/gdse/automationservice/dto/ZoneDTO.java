package lk.ijse.gdse.automationservice.dto;

public class ZoneDTO {
    private String id;
    private double minTemp;
    private double maxTemp;
    private String userId;

    public ZoneDTO(String id, double minTemp, double maxTemp, String userId) {
        this.id = id;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.userId = userId;
    }

    public ZoneDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}