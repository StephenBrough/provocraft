package com.blotcoo.provocraftcodechallenge.home;

import com.blotcoo.provocraftcodechallenge.util.network.models.Channel;
import com.blotcoo.provocraftcodechallenge.util.network.models.WeatherApiBaseModel;

import retrofit2.Callback;

public class HomeContract {
    interface View {
        /**
         * Shows a page with more details on the forecast
         */
        void showForecastDetails(Channel channel);
    }

    interface Presenter {

        /**
         * Calls up the Yahoo Weather API using a Forecast YQL query
         * @param searchQuery Location to search for
         * @return A callback containing the query results
         */
        void searchWeatherForecast(String searchQuery, Callback<WeatherApiBaseModel> cb);
    }
}
