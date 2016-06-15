package com.blotcoo.provocraftcodechallenge.util.network;

import com.blotcoo.provocraftcodechallenge.util.network.models.Results;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provides configuredc Retrofit instance to make API calls
 * to the Yahoo Weather API
 */
public class NetworkProvider {
    private static final String TAG = "NetworkProvider";

    private static final String BASE_URL = "https://query.yahooapis.com/";
    // The base query we work with when sending up a request
    private static final String BASE_QUERY = "select * from weather.forecast where woeid in (select woeid from geo.places(%s) where text=\"%s\")";
    // The max number of results that we will take
    private static final String MAX_RESULTS = "50";

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (null == retrofit) {
            // Setup network logging
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Attach the logging to a custom OkHttpClient
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logInterceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
        }
        return retrofit;
    }


    /**
     * Used to create a custom deserializer for the Result class, since the API is dumb
     * and dynamic and may return either a list or a single object for channel. DUMB.
     * @return A fully armed and operational battlest-...configured Gson object...
     */
    private static Gson getGson() {
        return new GsonBuilder().registerTypeAdapter(
                Results.class, new ResultsAdapter()).create();
    }

    /**
     * Format the string, replacing spaces, equal signs and quotation marks
     * @param query The query string
     * @return A string formatted to make the Yahoo API happy
     */
    public static String generateSearchQuery(String query) {
        String formatted = String.format(Locale.US, BASE_QUERY, MAX_RESULTS, query);
        formatted.replace(" ", "%20");
        formatted.replace("=", "%3D");
        formatted.replace("\"", "%22");
        return formatted;
    }
}
