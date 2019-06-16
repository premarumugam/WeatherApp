package weather.pactera.com.weatherapp.presenter;

import android.util.Log;

import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import weather.pactera.com.weatherapp.model.WeatherModel;
import weather.pactera.com.weatherapp.service.WeatherService;
import weather.pactera.com.weatherapp.view.WeatherView;

import static weather.pactera.com.weatherapp.Constants.API_KEY;
import static weather.pactera.com.weatherapp.Constants.DEFAULT_ICON;
import static weather.pactera.com.weatherapp.Constants.ICON_2;
import static weather.pactera.com.weatherapp.Constants.ICON_3;
import static weather.pactera.com.weatherapp.Constants.ICON_5;
import static weather.pactera.com.weatherapp.Constants.ICON_6;
import static weather.pactera.com.weatherapp.Constants.ICON_7;
import static weather.pactera.com.weatherapp.Constants.ICON_8;
import static weather.pactera.com.weatherapp.Constants.ICON_DAY;
import static weather.pactera.com.weatherapp.Constants.METRIC;
import static weather.pactera.com.weatherapp.Constants.WEATHER;

public class WeatherFragmentPresenter extends BasePresenter<WeatherView>{
    private WeatherService weatherService;

    public WeatherFragmentPresenter(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public void getWeatherForCity(String cityName){
        this.weatherService.getWeather(cityName, METRIC, API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeatherModel weatherModel) {
                        getView().updateWeatherStatus(weatherModel);
                        Log.i(WEATHER, "onNext: success response received");
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().updateError();
                        Log.e(WEATHER, "onError: Error response received " + e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public  String getWeatherIcon(WeatherModel weatherModel){

        int actualId = weatherModel.getWeather().get(0).getId();
        long sunrise = weatherModel.getSys().getSunrise() * 1000;
        long sunset = weatherModel.getSys().getSunset() * 1000;

        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = ICON_DAY;
            } else {
                icon = DEFAULT_ICON;
            }
        } else {
            switch(id) {
                case 2 : icon = ICON_2;
                    break;
                case 3 : icon = ICON_3;
                    break;
                case 7 : icon = ICON_7;
                    break;
                case 8 : icon = ICON_8;
                    break;
                case 6 : icon = ICON_6;
                    break;
                case 5 : icon = ICON_5;
                    break;
            }
        }
        return icon;
    }
}
