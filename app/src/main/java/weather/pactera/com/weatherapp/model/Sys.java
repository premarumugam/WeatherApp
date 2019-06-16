package weather.pactera.com.weatherapp.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Sys implements Serializable{
    private long sunrise;

    private long sunset;
}
