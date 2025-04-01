package org.example.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GismeteoResponse {
    @JsonProperty("temperature")
    private Temperature temperature;

    @Data
    public static class Temperature {
        @JsonProperty("air")
        private Air air;

        @Data
        public static class Air {
            @JsonProperty("C")
            private Double celsius;
        }
    }
}