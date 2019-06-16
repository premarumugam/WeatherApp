package weather.pactera.com.weatherapp.model;


import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WeatherModel implements Serializable{

    private String name;

    private long dt;

    private List<Weather> weather;

    private Wind wind;

    private Main main;

    private Sys sys;
}
