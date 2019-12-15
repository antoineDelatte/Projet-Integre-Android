package com.example.packvoyage.service;

import com.example.packvoyage.bindingModel.BedRoomBindingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IBedRoomService {
    String BASE_URL = "https://localhost:5001/BedRoom";

    @GET("packId")
    Call<List<BedRoomBindingModel>> getPackBedRooms(@Path("packId")int packId);
}
