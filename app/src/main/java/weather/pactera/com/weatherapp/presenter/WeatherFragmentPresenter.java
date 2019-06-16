package weather.pactera.com.weatherapp.presenter;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import weather.pactera.com.weatherapp.model.WeatherModel;
import weather.pactera.com.weatherapp.service.WeatherService;
import weather.pactera.com.weatherapp.view.WeatherView;

import static weather.pactera.com.weatherapp.Constants.API_KEY;
import static weather.pactera.com.weatherapp.Constants.METRIC;

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
                        Log.i("Subscripter", "onNext: success response received");
                        getView().updateWeatherStatus(weatherModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Subscripter", "onError: ");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



}
