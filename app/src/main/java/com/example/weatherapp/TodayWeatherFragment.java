package com.example.weatherapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.Model.WeatherResult;
import com.example.weatherapp.Retrofit.IOpenWeatherMap;
import com.example.weatherapp.Retrofit.RetrofitClient;
import com.example.weatherapp.common.common;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayWeatherFragment extends Fragment {

    ImageView img_weather;
    TextView txt_city_name, txt_temperature, txt_description, txt_date_time, txt_wind,
            txt_pressure, txt_humidity, txt_sunrise, txt_sunset, txt_geo_coords;

    LinearLayout weather_panel;
    ProgressBar loading;

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    static TodayWeatherFragment instance;

    public static TodayWeatherFragment getInstance() {
        if (instance == null)
            instance = new TodayWeatherFragment();
        return instance;

    }


    public TodayWeatherFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_today_weather, container, false);

        img_weather = (ImageView) itemView.findViewById(R.id.img_weather);
        txt_city_name = (TextView) itemView.findViewById(R.id.txt_city_name);
        txt_temperature = (TextView) itemView.findViewById(R.id.txt_temperature);
        txt_description = (TextView) itemView.findViewById(R.id.txt_description);
        txt_date_time = (TextView) itemView.findViewById(R.id.txt_date_time);
        txt_wind = (TextView) itemView.findViewById(R.id.txt_wind);
        txt_pressure = (TextView) itemView.findViewById(R.id.txt_pressure);
        txt_humidity = (TextView) itemView.findViewById(R.id.txt_humidity);
        txt_sunrise = (TextView) itemView.findViewById(R.id.txt_sunrise);
        txt_sunset = (TextView) itemView.findViewById(R.id.txt_sunset);
        txt_geo_coords = (TextView) itemView.findViewById(R.id.txt_geo_coords);

        weather_panel = (LinearLayout) itemView.findViewById(R.id.weather_panel);
        loading = (ProgressBar) itemView.findViewById(R.id.loading);

        getWeatherInformation();


        return itemView;

        // Inflate the layout for this fragment

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void getWeatherInformation() {
        IOpenWeatherMap iOpenWeatherMap = RetrofitClient.getInstance().create(IOpenWeatherMap.class);
        Call<WeatherResult> weatherResultCall = iOpenWeatherMap.getWeatherResult();

        weatherResultCall.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
              WeatherResult weatherResult = response.body();
               // System.out.println("connect with " + weatherResult);
                Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                                .append(weatherResult.getWeather().get(0).getIcon())
                                .append(".png").toString()).into(img_weather);

                        txt_city_name.setText(weatherResult.getName());
                        txt_description.setText("Weather in " + weatherResult.getName());
                        txt_temperature.setText(weatherResult.getMain().getTemp() + "°F");
                        txt_date_time.setText(common.convertUnixToDate(weatherResult.getDt()));
                        txt_pressure.setText(weatherResult.getMain().getPressure() + " hpa");
                        txt_humidity.setText(weatherResult.getMain().getHumidity() + " %");
                        txt_sunrise.setText(common.convertUnixToHour(weatherResult.getSys().getSunrise()));
                        txt_sunset.setText(common.convertUnixToHour(weatherResult.getSys().getSunset()));
                        txt_geo_coords.setText("[" + weatherResult.getCoord().toString() + "]");
                        txt_wind.setText(String.valueOf(weatherResult.getWind().getSpeed()));

                        weather_panel.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                System.out.println("failed " +t.getMessage());
            }
        });

//        compositeDisposable.add(mService.getWeatherByLatLag(String.valueOf(common.current_location.getLatitude()),
//                String.valueOf(common.current_location.getLongitude()),
//                common.API_ID,
//                "metric")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<WeatherResult>() {
//                    @Override
//                    public void accept(WeatherResult weatherResult) throws Exception {
//                        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/wn/")
//                                .append(weatherResult.getWeather().get(0).getIcon())
//                                .append(".png").toString()).into(img_weather);
//
//                        txt_city_name.setText(weatherResult.getName());
//                        txt_description.setText(new StringBuilder("Weather in")
//                                .append(weatherResult.getName()).toString());
//                        txt_temperature.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append("°C").toString());
//                        txt_date_time.setText(common.convertUnixToDate(weatherResult.getDt()));
//                        txt_pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append(" hpa").toString());
//                        txt_humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append(" %").toString());
//                        txt_sunrise.setText(common.convertUnixToHour(weatherResult.getSys().getSunrise()));
//                        txt_sunset.setText(common.convertUnixToHour(weatherResult.getSys().getSunset()));
//                        txt_geo_coords.setText(new StringBuilder("[").append(weatherResult.getCoord().toString()).append("]").toString());
//
//
//                        weather_panel.setVisibility(View.VISIBLE);
//                        loading.setVisibility(View.GONE);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(getActivity(), "" + throwable.getMessage(),
//                                Toast.LENGTH_LONG).show();
//
//                    }
//                })
//
//        );


    }


}
