package weather.pactera.com.weatherapp;

import android.app.Application;

import weather.pactera.com.weatherapp.module.AppModule;
import weather.pactera.com.weatherapp.module.WeatherModule;

import static weather.pactera.com.weatherapp.Constants.BASE_URI;

/*
 * we use our AppComponent (now prefixed with Dagger)
 * to inject our Application class.
 * This way a DispatchingAndroidInjector is injected which is
 * then returned when an injector for an activity is requested.
 * */
public class AppController extends Application  {


    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .weatherModule(new WeatherModule(BASE_URI))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}