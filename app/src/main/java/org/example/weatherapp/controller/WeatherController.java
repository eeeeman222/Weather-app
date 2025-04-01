package org.example.weatherapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.weatherapp.dto.TemperatureResponse;
import org.example.weatherapp.service.WeatherService;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@EnableFeignClients
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/temperature")
    public TemperatureResponse getTemperature(
            @RequestParam double latitude,
            @RequestParam double longitude) {
        return weatherService.getCurrentTemperature(latitude, longitude);
    }
}