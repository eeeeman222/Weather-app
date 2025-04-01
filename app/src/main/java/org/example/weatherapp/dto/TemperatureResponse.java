package org.example.weatherapp.dto;

import lombok.Data;

@Data
public class TemperatureResponse {
    private Double temperature;
    private String unit;

    public TemperatureResponse(Double temperature) {
        this.temperature = temperature;
        this.unit = "C";
    }
}