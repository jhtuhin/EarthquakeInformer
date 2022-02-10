package com.example.earthquakeinformer.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EarthQuakeService {
    public static EarthQuakeApi getService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://earthquake.usgs.gov/fdsnws/event/1/query?")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(EarthQuakeApi.class);
    }
}
