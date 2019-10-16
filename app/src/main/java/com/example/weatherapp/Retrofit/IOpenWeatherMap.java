package com.example.weatherapp.Retrofit;

import com.example.weatherapp.Model.WeatherResult;
import com.example.weatherapp.R;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("weather?lat=35&lon=139&appid=be90a6a587bed58a08f9557aeaaf47cb")
    Call<WeatherResult> getWeatherResult();

    //Call<WeatherResult> getGetWeatherResult;




    //@GET("weather?lat=35&lon=139&appid=be90a6a587bed58a08f9557aeaaf47cb")
 //   Call<WeatherResult> getWeatherResult;
   // Observable<WeatherResult> getWeatherByLatLag(@Query("lat") String lat,
//                                                 @Query("lon") String lng,
//                                                 @Query("appid") String appid,
//                                                 @Query("units") String unit);
}
