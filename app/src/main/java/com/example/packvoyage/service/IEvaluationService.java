package com.example.packvoyage.service;

import com.example.packvoyage.bindingModel.EvaluationBindingModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IEvaluationService {
    String BASE_URL = "https://trip4studentnathansurquin.azurewebsites.net/";

    @GET("Evaluation")
    Call<List<EvaluationBindingModel>> get(@Query("packId") int packId);

    @DELETE("Evaluation")
    Call<ResponseBody> deleteComment(@Query("evaluationId") int evaluationId);
}
