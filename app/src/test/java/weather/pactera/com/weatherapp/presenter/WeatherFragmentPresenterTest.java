package weather.pactera.com.weatherapp.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.ConnectException;
import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import weather.pactera.com.weatherapp.model.Sys;
import weather.pactera.com.weatherapp.model.Weather;
import weather.pactera.com.weatherapp.model.WeatherModel;
import weather.pactera.com.weatherapp.service.WeatherService;
import weather.pactera.com.weatherapp.view.WeatherView;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static weather.pactera.com.weatherapp.Constants.API_DOWN;
import static weather.pactera.com.weatherapp.Constants.API_KEY;
import static weather.pactera.com.weatherapp.Constants.ERROR_CHECK_CITY;
import static weather.pactera.com.weatherapp.Constants.HTTP_ERROR;
import static weather.pactera.com.weatherapp.Constants.ICON_2;
import static weather.pactera.com.weatherapp.Constants.ICON_3;
import static weather.pactera.com.weatherapp.Constants.ICON_5;
import static weather.pactera.com.weatherapp.Constants.ICON_6;
import static weather.pactera.com.weatherapp.Constants.ICON_7;
import static weather.pactera.com.weatherapp.Constants.ICON_8;
import static weather.pactera.com.weatherapp.Constants.METRIC;

@RunWith(MockitoJUnitRunner.class)
public class WeatherFragmentPresenterTest {

    public static final String MELBOURNE = "Melbourne";
    private WeatherFragmentPresenter presenter;

    @Mock
    private WeatherService weatherService;

    @Mock
    private WeatherView weatherView;

    @Before
    public void setUp() throws Exception {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        this.presenter = new WeatherFragmentPresenter(weatherService);
        this.presenter.setView(weatherView);
    }

    @Test
    public void shoudlReturnIcon2WhenIdIs2xx() throws Exception {

        WeatherModel weatherModel = WeatherModel.builder()
                .weather(Arrays.asList(
                        Weather.builder()
                                .id(203)
                                .build()
                ))
                .sys(Sys.builder()
                .sunrise(1234)
                .sunset(2344).build())
                .build();


        String weatherIcon = presenter.getWeatherIcon(weatherModel);

        assertThat(weatherIcon, is(ICON_2));
    }

    @Test
    public void shoudlReturnIcon3WhenIdIs3xx() throws Exception {

        WeatherModel weatherModel = WeatherModel.builder()
                .weather(Arrays.asList(
                        Weather.builder()
                                .id(323)
                                .build()
                ))
                .sys(Sys.builder()
                        .sunrise(1234)
                        .sunset(2344).build())
                .build();


        String weatherIcon = presenter.getWeatherIcon(weatherModel);

        assertThat(weatherIcon, is(ICON_3));
    }

    @Test
    public void shoudlReturnIcon7WhenIdIs7xx() throws Exception {

        WeatherModel weatherModel = WeatherModel.builder()
                .weather(Arrays.asList(
                        Weather.builder()
                                .id(723)
                                .build()
                ))
                .sys(Sys.builder()
                        .sunrise(1234)
                        .sunset(2344).build())
                .build();


        String weatherIcon = presenter.getWeatherIcon(weatherModel);

        assertThat(weatherIcon, is(ICON_7));
    }

    @Test
    public void shoudlReturnIcon8WhenIdIs8xx() throws Exception {

        WeatherModel weatherModel = WeatherModel.builder()
                .weather(Arrays.asList(
                        Weather.builder()
                                .id(823)
                                .build()
                ))
                .sys(Sys.builder()
                        .sunrise(1234)
                        .sunset(2344).build())
                .build();


        String weatherIcon = presenter.getWeatherIcon(weatherModel);
        assertThat(weatherIcon, is(ICON_8));
    }

    @Test
    public void shoudlReturnIcon6WhenIdIs6xx() throws Exception {

        WeatherModel weatherModel = WeatherModel.builder()
                .weather(Arrays.asList(
                        Weather.builder()
                                .id(623)
                                .build()
                ))
                .sys(Sys.builder()
                        .sunrise(1234)
                        .sunset(2344).build())
                .build();


        String weatherIcon = presenter.getWeatherIcon(weatherModel);
        assertThat(weatherIcon, is(ICON_6));
    }

    @Test
    public void shoudlReturnIcon5WhenIdIs5xx() throws Exception {

        WeatherModel weatherModel = WeatherModel.builder()
                .weather(Arrays.asList(
                        Weather.builder()
                                .id(523)
                                .build()
                ))
                .sys(Sys.builder()
                        .sunrise(1234)
                        .sunset(2344).build())
                .build();

        String weatherIcon = presenter.getWeatherIcon(weatherModel);
        assertThat(weatherIcon, is(ICON_5));
    }

    @Test
    public void shouldMakeNetworkCallToFetchWeatherAndUpdateUIWithValues() throws Exception {

        WeatherModel weatherModel = WeatherModel.builder()
                .build();
        when(weatherService.getWeather(MELBOURNE, METRIC, API_KEY)).thenReturn(Observable.just(weatherModel));

        presenter.getWeatherForCity(MELBOURNE);

        verify(weatherService).getWeather(MELBOURNE, METRIC, API_KEY);
    }

    @Test
    public void shouldMakeNetworkCallToFetchWeatherAndUpdateUIWithError() throws Exception {

        WeatherModel weatherModel = WeatherModel.builder()
                .build();
        when(weatherService.getWeather(MELBOURNE, METRIC, API_KEY)).thenReturn(Observable.error(new Throwable()));

        presenter.getWeatherForCity(MELBOURNE);

        verify(weatherService).getWeather(MELBOURNE, METRIC, API_KEY);
        verify(weatherView, never()).updateError(HTTP_ERROR);
        verify(weatherView, never()).updateWeatherStatus(weatherModel);
    }

    @Test
    public void shouldMakeNetworkCallToFetchWeatherAndUpdateUIzzWithHttpErrorMessage() throws Exception {

        WeatherModel weatherModel = WeatherModel.builder()
                .build();
        when(weatherService.getWeather(MELBOURNE, METRIC, API_KEY)).thenReturn(Observable.error(new HttpException(Response.error(403,
                ResponseBody.create(
                        MediaType.parse("application/json"),
                        "{\"error\":[\"message\"]}"
                )))));

        presenter.getWeatherForCity(MELBOURNE);

        verify(weatherService).getWeather(MELBOURNE, METRIC, API_KEY);
        verify(weatherView, never()).updateError(API_DOWN);
        verify(weatherView, never()).updateWeatherStatus(weatherModel);
    }

    @Test
    public void shouldMakeNetworkCallToFetchWeatherAndUpdateUIWithApiErrorMessage() throws Exception {

        WeatherModel weatherModel = WeatherModel.builder()
                .build();


        when(weatherService.getWeather(MELBOURNE, METRIC, API_KEY)).thenReturn(Observable.error(new ConnectException("Network is not available")));

        presenter.getWeatherForCity(MELBOURNE);

        verify(weatherService).getWeather(MELBOURNE, METRIC, API_KEY);
        verify(weatherView, never()).updateWeatherStatus(weatherModel);
        verify(weatherView, never()).updateError(ERROR_CHECK_CITY);
    }
}