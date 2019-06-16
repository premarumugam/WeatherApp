package weather.pactera.com.weatherapp;


import javax.inject.Singleton;

import dagger.Component;
import weather.pactera.com.weatherapp.activity.WeatherActivity;
import weather.pactera.com.weatherapp.module.AppModule;
import weather.pactera.com.weatherapp.module.WeatherModule;
import weather.pactera.com.weatherapp.fragment.WeatherFragment;

@Singleton
@Component(modules = {AppModule.class, WeatherModule.class})
public interface AppComponent {

    void inject(WeatherFragment fragment);

    void inject(WeatherActivity weatherActivity);
}
