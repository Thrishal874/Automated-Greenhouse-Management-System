package lk.ijse.gdse.automationservice.dto;

public class TelemetryData {
    private String zoneId;
    private TelemetryValue value;

    public TelemetryData(String zoneId, TelemetryValue value) {
        this.zoneId = zoneId;
        this.value = value;
    }

    public TelemetryData() {
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public TelemetryValue getValue() {
        return value;
    }

    public void setValue(TelemetryValue value) {
        this.value = value;
    }

    public static class TelemetryValue {

        private double temperature;

        public TelemetryValue(double temperature) {
            this.temperature = temperature;
        }

        public TelemetryValue() {
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

    }
}