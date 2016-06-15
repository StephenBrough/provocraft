package com.blotcoo.provocraftcodechallenge.home;

import com.blotcoo.provocraftcodechallenge.util.network.IApi;
import com.blotcoo.provocraftcodechallenge.util.network.models.WeatherApiBaseModel;

import retrofit2.Callback;

public class HomePresenterImpl implements HomeContract.Presenter {

    // Implementation of our API interface
    IApi api;

    public HomePresenterImpl(IApi api) {
        this.api = api;
    }

    @Override
    public void searchWeatherForecast(String searchQuery, Callback<WeatherApiBaseModel> cb) {
        api.searchForecast(searchQuery, cb);
    }
}
