package org.example.weather;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeatherControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    void setup() throws Exception {
        MountebankTestUtils.startMountebank();
    }

    @Test
    void getTemperature_shouldReturn200ForValidRequest() {
        String url = buildUrl(55.7558, 37.6176);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"temperature\":22.5");
    }

    @Test
    void getTemperature_shouldReturn400ForMissingParams() {
        ResponseEntity<String> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/weather/temperature",
            String.class
        );
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void getTemperature_shouldReturn424ForFailedDependency() {
        String url = buildUrl(0, 0);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FAILED_DEPENDENCY);
    }

    private String buildUrl(double lat, double lon) {
        return String.format(
            "http://localhost:%d/api/weather/temperature?latitude=%s&longitude=%s",
            port, lat, lon
        );
    }

    @AfterAll
    void teardown() {
        MountebankTestUtils.stopMountebank();
    }
}