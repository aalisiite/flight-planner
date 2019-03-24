package io.codelex.flightplanner.weather;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.codelex.flightplanner.api.Weather;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class ForecastCache {
    private final WeatherGateway gateway;
    private final Cache<CacheKey, Weather> cache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    public ForecastCache(WeatherGateway gateway) {
        this.gateway = gateway;
    }

    public Optional<Weather> fetchForecast(String city, LocalDate date) {

        CacheKey key = new CacheKey(city, date);
        Weather weather = cache.getIfPresent(key);

        if (weather != null) {
            return Optional.of(weather);
        }
        Optional<Weather> response = gateway.fetchForecast(city, date);
        response.ifPresent(it -> cache.put(key, it));
        return response;
    }

}
