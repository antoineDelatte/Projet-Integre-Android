package com.example.packvoyage.service;

import com.example.packvoyage.bindingModel.ProfilePictureToSaveBindingModel;
import com.example.packvoyage.bindingModel.UserForEvaluationBindingModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Query;

public interface IAccountService {
    String BASE_URL = "https://trip4studentnathansurquin.azurewebsites.net/";

    @GET("Account")
    Call<UserForEvaluationBindingModel> getAccountInfo(@Query("userId") String userId);

    @PATCH("PictureForAUser")
    Call<ResponseBody> changeProfilePicture(@Body ProfilePictureToSaveBindingModel imageToSave);
}
