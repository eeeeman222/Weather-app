package org.example.weatherapp.service;


import org.example.weatherapp.dto.TemperatureResponse;
import org.springframework.context.annotation.Bean;

public interface WeatherService {
    TemperatureResponse getCurrentTemperature(double latitude, double longitude);
}