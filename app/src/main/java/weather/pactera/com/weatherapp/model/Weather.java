package weather.pactera.com.weatherapp.model;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class Weather implements Serializable{

    private String main;

    private String icon;

    private int id;
}
