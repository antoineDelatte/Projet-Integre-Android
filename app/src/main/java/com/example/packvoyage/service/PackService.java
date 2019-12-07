package com.example.packvoyage.service;

import com.example.packvoyage.model.Pack;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PackService {
    String BASE_URL = "https://localhost:";

    @GET("packs")
    Call<List<Pack>> getPacks();

    @GET("packs")
    Call<Pack> getUserById(@Query("id") Integer id);

    //@POST("packs")
    //Call<Pack> postUser(@Body Pack pack)
}
