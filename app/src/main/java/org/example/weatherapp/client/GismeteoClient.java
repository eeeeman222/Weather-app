package org.example.weatherapp.client;

import org.example.weatherapp.config.FeignConfig;
import org.example.weatherapp.dto.GismeteoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "gismeteoClient",
        url = "${gismeteo.api.url}",
        configuration = FeignConfig.class
)
public interface GismeteoClient {
    @GetMapping("/")
    GismeteoResponse getWeather(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude
    );
}