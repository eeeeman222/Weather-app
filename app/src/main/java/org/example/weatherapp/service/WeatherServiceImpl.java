package org.example.weatherapp.service;


import org.example.weatherapp.exception.WeatherServiceException;
import lombok.RequiredArgsConstructor;
import org.example.weatherapp.client.GismeteoClient;
import org.example.weatherapp.dto.GismeteoResponse;
import org.example.weatherapp.dto.TemperatureResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final GismeteoClient gismeteoClient;

    @Override
    public TemperatureResponse getCurrentTemperature(double latitude, double longitude) {
        try {
            GismeteoResponse response = gismeteoClient.getWeather(latitude, longitude);
            if (response == null || response.getTemperature() == null ||
                    response.getTemperature().getAir() == null) {
                throw new WeatherServiceException("Invalid response from weather service");
            }

            Double temperature = response.getTemperature().getAir().getCelsius();
            return new TemperatureResponse(temperature);
        } catch (Exception e) {
            throw new WeatherServiceException("Failed to get temperature data", e);
        }
    }
}