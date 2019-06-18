package weather.pactera.com.weatherapp.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import weather.pactera.com.weatherapp.interceptor.ForceCacheInterceptor;
import weather.pactera.com.weatherapp.presenter.WeatherFragmentPresenter;
import weather.pactera.com.weatherapp.service.WeatherService;
import weather.pactera.com.weatherapp.utils.NetworkUtils;

import static weather.pactera.com.weatherapp.Constants.BASE_URI;

@Module
public class WeatherModule {

    String mBaseUrl;

    public WeatherModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    /*
     * The method returns the Gson object
     * */
    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }


    /*
     * The method returns the Cache object
     * */
    @Provides
    @Singleton
    Cache provideCache(Application application) {
        long cacheSize = 10 * 1024 * 1024; // 10 MB
        File httpCacheDirectory = new File(application.getCacheDir(), "http-cache");
        return new Cache(httpCacheDirectory, cacheSize);
    }

    /*
     * The method returns the Okhttp object
     * */
    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache, Application application) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.cache(cache);
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new ForceCacheInterceptor(application));

        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        return httpClient.build();
    }


    /*
     * The method returns the Retrofit object
     * */
    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URI)
                .client(okHttpClient)
                .build();
    }


    @Provides
    @Singleton
    WeatherService provideMovieApiService(Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }

    @Provides
    WeatherFragmentPresenter weatherFragmentPresenter(WeatherService weatherService){
        return new WeatherFragmentPresenter(weatherService);
    }


}
