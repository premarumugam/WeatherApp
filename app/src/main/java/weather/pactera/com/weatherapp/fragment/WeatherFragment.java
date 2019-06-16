package weather.pactera.com.weatherapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import weather.pactera.com.weatherapp.AppController;
import weather.pactera.com.weatherapp.R;
import weather.pactera.com.weatherapp.activity.WeatherActivity;
import weather.pactera.com.weatherapp.model.WeatherModel;
import weather.pactera.com.weatherapp.presenter.WeatherFragmentPresenter;
import weather.pactera.com.weatherapp.view.WeatherView;

public class WeatherFragment extends Fragment implements WeatherView{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final int LOC = 0;


    @Inject
    WeatherFragmentPresenter weatherFragmentPresenter;

    @BindView(R.id.city_field)
    TextView cityTextView;

    @BindView(R.id.current_temperature_field)
    TextView currentTemperatureView;

    @BindView(R.id.pressure_field)
    TextView pressureFieldView;

    @BindView(R.id.humidity_field)
    TextView humidityFieldView;

    @BindView(R.id.details_field)
    TextView detailsFieldView;

    @BindView(R.id.weather_icon)
    TextView weatherIconView;

    @Override
    public void onAttach(Context context) {
        ((AppController) context.getApplicationContext()).getAppComponent().inject(this);
        weatherFragmentPresenter.setView(this);
        super.onAttach(context);
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static WeatherFragment newInstance(int sectionNumber) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        int posistion = getArguments().getInt(ARG_SECTION_NUMBER);
        weatherFragmentPresenter.getWeatherForCity("Melbourne");
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void updateWeatherStatus(WeatherModel weatherModel){
        cityTextView.setText(weatherModel.getName());
        currentTemperatureView.setText(weatherModel.getMain().getTemp());
        pressureFieldView.setText("Pressure: " + weatherModel.getMain().getPressure() + " hPa");
        humidityFieldView.setText("Humidity: " + weatherModel.getMain().getHumidity() + " %");
        detailsFieldView.setText(weatherModel.getWeather().get(LOC).getMain());
        //weatherIconView.setText(weatherFragmentPresenter.getWeatherIcon(weatherModel));
    }

}
