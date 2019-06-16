package weather.pactera.com.weatherapp.service;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import weather.pactera.com.weatherapp.model.WeatherModel;

import static weather.pactera.com.weatherapp.Constants.URL_WEATHER_CITY;

public interface WeatherService {

    @GET(URL_WEATHER_CITY)
    Observable<WeatherModel> getWeather(@Query(value = "q") String cityName,
                                        @Query(value = "units") String unit,
                                        @Query(value = "appid") String appId);
}
