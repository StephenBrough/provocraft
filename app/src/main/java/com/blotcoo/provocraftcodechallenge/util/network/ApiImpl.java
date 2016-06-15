package com.blotcoo.provocraftcodechallenge.util.network;

import com.blotcoo.provocraftcodechallenge.util.network.models.WeatherApiBaseModel;

import retrofit2.Callback;

public class ApiImpl implements IApi {

    @Override
    public void searchForecast(String searchQuery, Callback<WeatherApiBaseModel> cb) {
        searchQuery = NetworkProvider.generateSearchQuery(searchQuery);
        NetworkProvider.getRetrofit().create(YahooWeatherService.class)
                .searchForecast(searchQuery).enqueue(cb);
    }
}
