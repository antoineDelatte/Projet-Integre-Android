package com.example.packvoyage.repository;

import android.content.Context;
import android.util.Log;

import com.example.packvoyage.Constant.Constants;
import com.example.packvoyage.Utils.ConnectionState;
import com.example.packvoyage.ViewModel.PackDetailVM;
import com.example.packvoyage.bindingModel.TagBindingModel;
import com.example.packvoyage.model.ActivityTag;
import com.example.packvoyage.service.IActivityService;
import com.example.packvoyage.service.PackService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreferencesDao {
    public void loadAllPreferences(PackDetailVM packVM, Context context){
        if(!ConnectionState.isNetworkAvailable(context)){
            packVM.setApiCallStatus(Constants.NO_CONNECTION);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PackService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IActivityService service = retrofit.create(IActivityService.class);
        Call<List<TagBindingModel>> call = service.getAllActivityTags();
        call.enqueue(new Callback<List<TagBindingModel>>() {
            @Override
            public void onResponse(Call<List<TagBindingModel>> call, Response<List<TagBindingModel>> response) {
                if (!response.isSuccessful()) {
                    packVM.setApiCallStatus(response.code());
                    return;
                }
                List<TagBindingModel>tagBindingModels = response.body();
                ArrayList<ActivityTag>activityTags = new ArrayList<>();

                if(tagBindingModels.size() == 0){
                    packVM.setAllActivityTags(activityTags);
                    return;
                }

                for(TagBindingModel tagBindingModel : tagBindingModels){
                    activityTags.add(new ActivityTag(tagBindingModel.getId(), tagBindingModel.getName()));
                }
                packVM.setAllActivityTags(activityTags);
            }

            @Override
            public void onFailure(Call<List<TagBindingModel>> call, Throwable t) {
                Log.e("Trip4Student", t.getMessage());
            }
        });
    }
}
