package org.example.weather;

import org.example.weatherapp.dto.TemperatureResponse;
import org.example.weatherapp.exception.WeatherServiceException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeatherServiceImplTest {

    @Autowired
    private WeatherService weatherService;

    @BeforeAll
    void setup() throws Exception {
        MountebankTestUtils.startMountebank();
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("gismeteo.api.url", () -> "http://localhost:8081,http://localhost:8082");
        registry.add("gismeteo.fallback.enabled", () -> "true");
    }

    @Test
    void getCurrentTemperature_shouldReturnValueForMoscow() {
        TemperatureResponse response = weatherService.getCurrentTemperature(55.7558, 37.6176);
        
        assertThat(response)
            .isNotNull()
            .extracting(TemperatureResponse::getTemperature)
            .isEqualTo(22.5);
    }

    @Test
    void getCurrentTemperature_shouldUseFallbackWhenMainFails() {
        TemperatureResponse response = weatherService.getCurrentTemperature(59.9343, 30.3351);
        
        assertThat(response)
            .isNotNull()
            .extracting(TemperatureResponse::getTemperature)
            .isEqualTo(20.1);
    }

    @Test
    void getCurrentTemperature_shouldThrowForInvalidCoordinates() {
        assertThatThrownBy(() -> weatherService.getCurrentTemperature(0, 0))
            .isInstanceOf(WeatherServiceException.class)
            .hasMessageContaining("Failed to get temperature data");
    }

    @Test
    void getCurrentTemperature_shouldHandleTimeout() {
        // Тест с моком таймаута (добавить в конфиг)
        assertThatThrownBy(() -> weatherService.getCurrentTemperature(90, 0))
            .isInstanceOf(WeatherServiceException.class)
            .hasMessageContaining("Timeout");
    }

    @AfterAll
    void teardown() {
        MountebankTestUtils.stopMountebank();
    }
}