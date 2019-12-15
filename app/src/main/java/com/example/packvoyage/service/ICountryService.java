package com.example.packvoyage.service;

import com.example.packvoyage.bindingModel.CountryBindingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ICountryService {
    String BASE_URL = "https://localhost:5001/Country";

    @GET() // todo
    Call<List<CountryBindingModel>> getCountry();
}
