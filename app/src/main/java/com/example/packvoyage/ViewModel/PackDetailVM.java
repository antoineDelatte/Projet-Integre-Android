package com.example.packvoyage.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PackDetailVM extends androidx.lifecycle.ViewModel {

    private MutableLiveData<Integer>selectedPackId = new MutableLiveData<>();
    private MutableLiveData<String>selectedPackName= new MutableLiveData<>();

    public LiveData<String>getSelectedPackName(){
        return selectedPackName;
    }

    public void setSelectedPackName(String selectedPackName){
        this.selectedPackName.setValue(selectedPackName);
    }

    public LiveData<Integer> getSelectedPackId() {
        return selectedPackId;
    }

    public void setSelectedPackId(Integer selectedPackId) {
        this.selectedPackId.setValue(selectedPackId);
    }

}
