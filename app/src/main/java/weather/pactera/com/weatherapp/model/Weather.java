package weather.pactera.com.weatherapp.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Weather implements Serializable{

    private String main;

    private String description;

    private String icon;

    private int id;
}
