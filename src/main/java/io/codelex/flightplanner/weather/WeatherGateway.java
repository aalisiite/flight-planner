package io.codelex.flightplanner.weather;

import io.codelex.flightplanner.api.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static java.util.Optional.empty;

@Component
public class WeatherGateway {

    private static final Logger log = LoggerFactory.getLogger(WeatherGateway.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final ApixuProperties props;
    
    public WeatherGateway(ApixuProperties props) {
        this.props = props;
    }

    Optional<Weather> fetchForecast(String city, LocalDate date) {

        try {
            String formattedDate = date.format(DateTimeFormatter.ISO_DATE);
            URI uri = UriComponentsBuilder.fromHttpUrl(props.getApiUrl())
                    .path("/v1/forecast.json")
                    .queryParam("key", props.getApiKey())
                    .queryParam("q", city)
                    .queryParam("dt", formattedDate)
                    .build()
                    .toUri();
            ForecastResponse response = restTemplate.getForObject(
                    uri,
                    ForecastResponse.class);
            if (response == null) {
                throw new IllegalStateException();
            }

            ForecastResponse.Day day = response.getForecast().getForecastDays().get(0).getDay();
            return Optional.of(new Weather(day.getAverageTemperature(), day.getTotalPrecipation(), day.getMaxWind(), day.getCondition().getText()));
        } catch (Exception o_O) {
            log.warn("exception", o_O);
            return empty();
        }
    }
}
