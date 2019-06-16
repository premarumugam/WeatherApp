package weather.pactera.com.weatherapp.model;


import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;

@Getter
public class WeatherModel implements Serializable{

    private String name;

    private long dt;

    private List<Weather> weather;

    private Main main;

    private Sys sys;
}
