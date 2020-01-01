package com.example.packvoyage.repository;

import android.content.Context;
import android.util.Log;

import com.example.packvoyage.Constant.Constants;
import com.example.packvoyage.Utils.ConnectionState;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.bindingModel.ProfilePictureToSaveBindingModel;
import com.example.packvoyage.bindingModel.UserForEvaluationBindingModel;
import com.example.packvoyage.model.User;
import com.example.packvoyage.service.IAccountService;
import com.example.packvoyage.service.PackService;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountDao {

    public void loadAccountInformations(PackDetailVM packVM, Context context, String userId){
        if(!ConnectionState.isNetworkAvailable(context)){
            packVM.setApiCallStatus(Constants.NO_CONNECTION);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PackService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IAccountService service = retrofit.create(IAccountService.class);
        Call<UserForEvaluationBindingModel> call = service.getAccountInfo(userId);
        call.enqueue(new Callback<UserForEvaluationBindingModel>() {
            @Override
            public void onResponse(Call<UserForEvaluationBindingModel> call, Response<UserForEvaluationBindingModel> response) {
                if (!response.isSuccessful()) {
                    packVM.setApiCallStatus(response.code());
                    return;
                }
                UserForEvaluationBindingModel userForEvaluationBindingModel = response.body();
                User user = new User(userId, userForEvaluationBindingModel.getFirstName(),
                        userForEvaluationBindingModel.getLastName(),
                        userForEvaluationBindingModel.getPictureOrVideo().get(0).getContent());
                user.setUsername(userForEvaluationBindingModel.getUserName());
                packVM.setCurrentUser(user);
            }

            @Override
            public void onFailure(Call<UserForEvaluationBindingModel> call, Throwable t) {
                Log.e("Trip4Student", t.getMessage());
            }
        });
    }

    public void setNewUserProfilePicture(ProfilePictureToSaveBindingModel imageToSave, Context context, PackDetailVM packVM){
        if(!ConnectionState.isNetworkAvailable(context)){
            packVM.setApiCallStatus(Constants.NO_CONNECTION);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PackService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IAccountService pack = retrofit.create(IAccountService.class);
        Call<ResponseBody> call = pack.changeProfilePicture(imageToSave);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    packVM.setApiCallStatus(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Trip4Student", t.getMessage());
            }
        });
    }
}
