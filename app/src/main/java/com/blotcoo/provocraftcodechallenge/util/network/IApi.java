package com.blotcoo.provocraftcodechallenge.util.network;

import com.blotcoo.provocraftcodechallenge.util.network.models.WeatherApiBaseModel;

import retrofit2.Callback;

/**
 * Contract for all api calls
 */
public interface IApi {

    void searchForecast(String searchQuery, Callback<WeatherApiBaseModel> cb);
}
