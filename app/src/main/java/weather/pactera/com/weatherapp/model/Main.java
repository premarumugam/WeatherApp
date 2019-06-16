package weather.pactera.com.weatherapp.model;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class Main implements Serializable{

    private String temp;

    private String pressure;

    private String humidity;

}
