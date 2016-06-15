package com.blotcoo.provocraftcodechallenge.util.network;

import com.blotcoo.provocraftcodechallenge.util.network.models.WeatherApiBaseModel;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Retrofit service interfaces for Yahoo Weather API endpoints
 */
public interface YahooWeatherService {
    @POST("v1/public/yql?format=json")
    Call<WeatherApiBaseModel>
        searchForecast(@Query("q") String query);
}
