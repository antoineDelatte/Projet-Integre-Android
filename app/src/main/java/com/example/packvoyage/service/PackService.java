package com.example.packvoyage.service;

import com.example.packvoyage.bindingModel.PackBindingModel;
import com.example.packvoyage.model.Pack;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PackService {
    String BASE_URL = "https://localhost:5001";

    @GET("pack")
    Call<List<PackBindingModel>> getPacks();

    //@GET("packs")
    //Call<Pack> getUserById(@Query("id") Integer id);

    //@POST("packs")
    //Call<Pack> postUser(@Body Pack pack)
}
