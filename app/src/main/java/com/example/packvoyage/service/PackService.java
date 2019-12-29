package com.example.packvoyage.service;

import com.example.packvoyage.bindingModel.PackBindingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PackService {
    String BASE_URL = "https://trip4studentnathansurquin.azurewebsites.net/Pack/";

    @GET("imageOfThePack")
    Call<List<PackBindingModel>> getPacks();

    @GET("description/{packId}")
    Call<PackBindingModel> getPackDescription(@Query("packId") int packId, @Query("languageCode")String languageCode);

    @GET("packs/{packId}")
    Call<PackBindingModel> getPackActivities(@Path("packId") int packId);

    //@POST("packs")
    //Call<Pack> postUser(@Body Pack pack)
}
