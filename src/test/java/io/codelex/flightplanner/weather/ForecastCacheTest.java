package io.codelex.flightplanner.weather;

import io.codelex.flightplanner.api.Weather;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ForecastCacheTest {

    private WeatherGateway weatherGateway = mock(WeatherGateway.class);
    private ForecastCache forecastCache = new ForecastCache(weatherGateway);
    private Weather weather = mock(Weather.class);
    private LocalDate date = LocalDate.of(2019, 10, 2);

    @Test
    void get_weather_from_gateway_if_cache_is_empty() {
        //given
        when(weatherGateway.fetchForecast("Riga", date)).thenReturn(Optional.of(weather));
        //when
        Optional<Weather> weather = forecastCache.fetchForecast("Riga", date);
        //then
        assertSame(this.weather, weather);
    }

    @Test
    void get_weather_from_cache_if_there_is_value() {
        //given
        when(weatherGateway.fetchForecast("Riga", date)).thenReturn(Optional.of(weather));
        //when
        forecastCache.fetchForecast("Riga", date);
        forecastCache.fetchForecast("Riga", date);
        //then
        verify(weatherGateway, times(1)).fetchForecast("Riga", date);
    }
}