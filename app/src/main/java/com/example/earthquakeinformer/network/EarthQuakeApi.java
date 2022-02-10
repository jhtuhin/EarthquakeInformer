package com.example.earthquakeinformer.network;

import com.example.earthquakeinformer.models.EarthQuakeModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface EarthQuakeApi {
    @GET()
    Call<EarthQuakeModel> getEarthquakeInformation(@Url String endUrl);

}
