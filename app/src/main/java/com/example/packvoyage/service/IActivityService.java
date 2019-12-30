package com.example.packvoyage.service;

import com.example.packvoyage.bindingModel.PackBindingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IActivityService {
    String BASE_URL = "https://trip4studentnathansurquin.azurewebsites.net/";

    @GET("PackWithActivity")
    Call<List<PackBindingModel>> getActivitiesOfPack(@Query("packId") int packId);
}
