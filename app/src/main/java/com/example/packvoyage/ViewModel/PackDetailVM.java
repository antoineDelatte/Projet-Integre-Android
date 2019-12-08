package com.example.packvoyage.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.packvoyage.model.Pack;

public class PackDetailVM extends androidx.lifecycle.ViewModel {
    private MutableLiveData<Pack> currentPack = new MutableLiveData<>();

    public LiveData<Pack>getCurrentPack(){
        return currentPack;
    }

    public void setCurrentPack(Pack pack){
        currentPack.setValue(pack);
    }
}
