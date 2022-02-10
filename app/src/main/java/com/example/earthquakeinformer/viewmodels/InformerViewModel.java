package com.example.earthquakeinformer.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.earthquakeinformer.models.EarthQuakeModel;
import com.example.earthquakeinformer.network.EarthQuakeService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformerViewModel extends ViewModel {
    private String startDate;
    private String endDate;
    private int mag;

    public void setMag(int mag) {
        this.mag = mag;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    private MutableLiveData<EarthQuakeModel> earthQuakeLiveData =
            new MutableLiveData<>();

    public LiveData<EarthQuakeModel> getEarthQuakeLiveData() {
        return earthQuakeLiveData;
    }


    public void loadData() {
        final String endUrl =
                String.format("format=geojson&starttime=%s&endtime=%s&minmagnitude=%c",startDate,endDate,mag);
        EarthQuakeService.getService().getEarthquakeInformation(endUrl)
                .enqueue(new Callback<EarthQuakeModel>() {
                    @Override
                    public void onResponse(Call<EarthQuakeModel> call, Response<EarthQuakeModel> response) {
                        if (response.code() == 200) {
                            earthQuakeLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<EarthQuakeModel> call, Throwable t) {

                    }


                });
    }

}
