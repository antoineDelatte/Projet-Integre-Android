package com.example.packvoyage.service;

import com.example.packvoyage.bindingModel.FlightOfPackBindingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IFlightService {
    String BASE_URL = "https://trip4studentnathansurquin.azurewebsites.net/";

    @GET("FlightOfPackWithAirport")
    Call<List<FlightOfPackBindingModel>> getPackFlights(@Query("packId") int packId);
}
