package com.example.packvoyage.service;

import com.example.packvoyage.bindingModel.PackBindingModel;
import com.example.packvoyage.model.Pack;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PackService {
    String BASE_URL = "https://api-trip4students.azurewebsites.net/Pack/";

    @GET("imageOfThePack")
    Call<List<PackBindingModel>> getPacks();

    @GET("packs/{packId}")
    Call<PackBindingModel> getPackImage(@Path("packId") int packId);

    @GET("packs/{packId}")
    Call<PackBindingModel> getPackDescription(@Path("packId") int packId);

    @GET("packs/{packId}")
    Call<PackBindingModel> getPackActivities(@Path("packId") int packId);

    //@POST("packs")
    //Call<Pack> postUser(@Body Pack pack)
}
