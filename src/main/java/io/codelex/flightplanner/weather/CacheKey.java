package io.codelex.flightplanner.weather;

import java.time.LocalDate;
import java.util.Objects;

class CacheKey {
     private final String city;
     private final LocalDate date;
     
     CacheKey(String city, LocalDate date){
         this.city = city;
         this.date = date;
     }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheKey cacheKey = (CacheKey) o;
        return city.equals(cacheKey.city) &&
                date.equals(cacheKey.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, date);
    }
}
