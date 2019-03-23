package io.codelex.flightplanner.weather;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.codelex.flightplanner.api.Weather;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.*;

class WeatherGatewayTest {
    @Rule
    WireMockRule wireMock = new WireMockRule();

    private WeatherGateway gateway;
    private LocalDate date = LocalDate.of(2019, 10, 2);

    @BeforeEach
    void setUp() {

        wireMock.start();
        ApixuProperties props = new ApixuProperties();
        props.setApiUrl("http://localhost:" + wireMock.port());
        props.setApiKey("123");
        gateway = new WeatherGateway(props);
    }

    @AfterEach
    void tearDown() {
        wireMock.stop();
    }

    @Test
    void should_fetch_forecast() throws Exception {
        //given
        File file = ResourceUtils.getFile(this.getClass().getResource(("/stubs/successful-response.json")));
        assertTrue(file.exists());

        byte[] json = Files.readAllBytes(file.toPath());
        wireMock.stubFor(get(urlPathEqualTo("/v1/forecast.json"))
                .withQueryParam("key", equalTo("123"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withStatus(200)
                        .withBody(json)));

        //when
        Weather weather = gateway.fetchForecast("Riga", date).get();

        //then
        assertEquals(
                "Moderate or heavy rain shower",
                weather.getWeatherCondition()
        );
        assertEquals(
                1,
                weather.getPrecipitation()
        );
        assertEquals(
                0.0,
                weather.getTemperature()
        );
        assertEquals(
                23.8,
                weather.getWindSpeed()
        );
    }

    @Test
    void should_handle_external_service_error() {
        //given
        wireMock.stubFor(get(urlPathEqualTo("/v1/forecast.json"))
                .withQueryParam("key", equalTo("123"))
                .willReturn(aResponse()
                        .withStatus(500)));
        //when
        Optional<Weather> response = gateway.fetchForecast("Riga", date);
        //then
        assertFalse(response.isPresent());

    }

}