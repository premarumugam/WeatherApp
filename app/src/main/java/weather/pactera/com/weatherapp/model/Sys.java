package weather.pactera.com.weatherapp.model;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class Sys implements Serializable{
    private long sunrise;

    private long sunset;
}
