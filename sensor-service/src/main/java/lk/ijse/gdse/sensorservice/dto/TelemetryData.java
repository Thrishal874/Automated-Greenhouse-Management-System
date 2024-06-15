package lk.ijse.gdse.sensorservice.dto;

public class TelemetryData {
    private String deviceId;
    private String zoneId;
    private TelemetryValue value;
    private String capturedAt;

    public TelemetryData(String deviceId, String zoneId, TelemetryValue value, String capturedAt) {
        this.deviceId = deviceId;
        this.zoneId = zoneId;
        this.value = value;
        this.capturedAt = capturedAt;
    }

    public TelemetryData() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public String getCapturedAt() {
        return capturedAt;
    }

    public void setCapturedAt(String capturedAt) {
        this.capturedAt = capturedAt;
    }

    public static class TelemetryValue {
        private double temperature;
        private String tempUnit;
        private double humidity;
        private String humidityUnit;

        public TelemetryValue(double temperature, String tempUnit, double humidity, String humidityUnit) {
            this.temperature = temperature;
            this.tempUnit = tempUnit;
            this.humidity = humidity;
            this.humidityUnit = humidityUnit;
        }

        public TelemetryValue() {
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public String getTempUnit() {
            return tempUnit;
        }

        public void setTempUnit(String tempUnit) {
            this.tempUnit = tempUnit;
        }

        public double getHumidity() {
            return humidity;
        }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        public String getHumidityUnit() {
            return humidityUnit;
        }

        public void setHumidityUnit(String humidityUnit) {
            this.humidityUnit = humidityUnit;
        }
    }
}
