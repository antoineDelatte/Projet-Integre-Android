package com.example.packvoyage.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.packvoyage.model.Pack;

import java.util.ArrayList;

public class PackDetailVM extends androidx.lifecycle.ViewModel {

    private MutableLiveData<Integer>selectedPackId = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Pack>>packs = new MutableLiveData<>();
    private MutableLiveData<String>currentPackDescription = new MutableLiveData<>();

    public LiveData<Integer> getSelectedPackId() {
        return selectedPackId;
    }

    public void setSelectedPackId(Integer selectedPackId) {
        this.selectedPackId.setValue(selectedPackId);
    }

    public LiveData<ArrayList<Pack>> getPacks() {
        return packs;
    }

    public void setPacks(ArrayList<Pack>packs) {
        this.packs.setValue(packs);
    }

    public LiveData<String> getCurrentPackDescription() {
        return currentPackDescription;
    }

    public void setCurrentPackDescription(String currentPackDescription) {
        this.currentPackDescription.setValue(currentPackDescription);
    }
}
