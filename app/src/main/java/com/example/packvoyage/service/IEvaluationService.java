package com.example.packvoyage.service;

import com.example.packvoyage.bindingModel.EvaluationBindingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IEvaluationService {
    String BASE_URL = "https://trip4studentnathansurquin.azurewebsites.net/Evaluation/";

    @GET("{packId}")
    Call<List<EvaluationBindingModel>> get(@Query("packId") int packId);
}
