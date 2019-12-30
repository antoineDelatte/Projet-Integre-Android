package com.example.packvoyage.service;

import com.example.packvoyage.bindingModel.AccommodationOfPackBindingModel;
import com.example.packvoyage.bindingModel.PackBindingModel;
import com.example.packvoyage.model.Activity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PackService {
    String BASE_URL = "https://trip4studentnathansurquin.azurewebsites.net/";

    @GET("AllPacksWithPicture")
    Call<List<PackBindingModel>> getPacks(@Query("index")int index, @Query("number")int number);

    @GET("PackWithDescription")
    Call<PackBindingModel> getPackDescription(@Query("packId") int packId, @Query("languageCode")String languageCode);

    @GET("AccommodationOfPack")
    Call<List<AccommodationOfPackBindingModel>> getAccommodationsOfPack(@Query("packId") int packId);
}