package com.example.packvoyage.repository;

import android.util.Log;

import com.example.packvoyage.bindingModel.ImageOrVideoBindingModel;
import com.example.packvoyage.bindingModel.PackBindingModel;
import com.example.packvoyage.model.Pack;
import com.example.packvoyage.service.PackService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PackDao {

    private ArrayList<Pack> packs;

    public void loadPacks() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PackService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PackService pack = retrofit.create(PackService.class);
        Call<List<PackBindingModel>> call = pack.getPacks();
        call.enqueue(new Callback<List<PackBindingModel>>() {
            @Override
            public void onResponse(Call<List<PackBindingModel>> call, Response<List<PackBindingModel>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<PackBindingModel> packList = response.body();
                Pack pack;
                for(PackBindingModel packBindingModel : packList){
                    pack = new Pack(packBindingModel.getId(), packBindingModel.getName(), null, packBindingModel.getImageOrVideoBindingModels().get(0).getContent());
                    PackDao.this.packs.add(pack);
                }

            }

            @Override
            public void onFailure(Call<List<PackBindingModel>> call, Throwable t) {
                Log.i("erreur", t.getMessage());
            }
        });
    }

    public ArrayList<Pack> getPacks() {
        this.loadPacks();
        return packs;
    }

}
