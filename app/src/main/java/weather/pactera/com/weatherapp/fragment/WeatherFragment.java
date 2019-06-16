package weather.pactera.com.weatherapp.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import weather.pactera.com.weatherapp.AppController;
import weather.pactera.com.weatherapp.R;
import weather.pactera.com.weatherapp.model.WeatherModel;
import weather.pactera.com.weatherapp.presenter.WeatherFragmentPresenter;
import weather.pactera.com.weatherapp.view.WeatherView;

import static weather.pactera.com.weatherapp.Constants.ERROR_CHECK_CITY;

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

    @BindView(R.id.wind_field)
    TextView windFieldView;

    @BindView(R.id.details_field)
    TextView detailsFieldView;

    @BindView(R.id.updated_field)
    TextView updatedDateView;

    @BindView(R.id.weather_icon)
    TextView weatherIconView;

    @BindView(R.id.loader)
    ProgressBar progressBar;

    Typeface weatherFont;

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
        ButterKnife.bind(this, rootView);
        int posistion = getArguments().getInt(ARG_SECTION_NUMBER);
        weatherFragmentPresenter.getWeatherForCity(getResources().getStringArray(R.array.city_array)[posistion-1]);
        weatherFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        weatherIconView.setTypeface(weatherFont);
        return rootView;
    }

    public void updateWeatherStatus(WeatherModel weatherModel){
        progressBar.setVisibility(View.GONE);
        DateFormat df = DateFormat.getDateTimeInstance();
        updatedDateView.setText(df.format(new Date(weatherModel.getDt() * 1000)));
        cityTextView.setText(weatherModel.getName());
        currentTemperatureView.setText(weatherModel.getMain().getTemp() + "°");
        pressureFieldView.setText("Pressure: " + weatherModel.getMain().getPressure() + " hPa");
        windFieldView.setText("Wind: " + weatherModel.getWind().getSpeed() + " km/h");
        detailsFieldView.setText(weatherModel.getWeather().get(LOC).getDescription().toUpperCase());
        weatherIconView.setText(Html.fromHtml(weatherFragmentPresenter.getWeatherIcon(weatherModel)));
    }

    public void updateError(){
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this.getContext(), ERROR_CHECK_CITY, Toast.LENGTH_LONG).show();
    }

}
