package weather.pactera.com.weatherapp.view;

import weather.pactera.com.weatherapp.model.WeatherModel;

public interface WeatherView {

    void updateWeatherStatus(WeatherModel weatherModel);

    void updateError(String errorMessage);

    void dismissLoadingBar();
}
