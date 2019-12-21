package com.example.packvoyage.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.packvoyage.model.Comment;
import com.example.packvoyage.model.Pack;
import com.example.packvoyage.model.User;

import java.util.ArrayList;

public class PackDetailVM extends androidx.lifecycle.ViewModel {

    private MutableLiveData<Integer>selectedPackId = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Pack>>packs = new MutableLiveData<>();
    private MutableLiveData<String>currentPackDescription = new MutableLiveData<>();
    private MutableLiveData<String>selectedPackName = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Pack>>myBookedPacks = new MutableLiveData<>();
    private MutableLiveData<String>selectedBookedPackName = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Comment>>selectedBookedPackComments = new MutableLiveData<>();
    private MutableLiveData<User>currentUser = new MutableLiveData<>();

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser.setValue(currentUser);
    }

    public LiveData<ArrayList<Comment>> getSelectedBookedPackComments() {
        return selectedBookedPackComments;
    }

    public void setSelectedBookedPackComments(ArrayList<Comment> selectedBookedPackComments) {
        this.selectedBookedPackComments.setValue(selectedBookedPackComments);
    }

    public LiveData<String> getSelectedBookedPackName() {
        return selectedBookedPackName;
    }

    public void setSelectedBookedPackName(String selectedBookedPackName) {
        this.selectedBookedPackName.setValue(selectedBookedPackName);
    }

    public LiveData<Integer> getSelectedBookedPackId() {
        return selectedBookedPackId;
    }

    public void setSelectedBookedPackId(Integer selectedBookedPackId) {
        this.selectedBookedPackId.setValue(selectedBookedPackId);
    }

    private MutableLiveData<Integer>selectedBookedPackId = new MutableLiveData<>();

    public LiveData<ArrayList<Pack>> getMyBookedPacks() {
        return myBookedPacks;
    }

    public void setMyBookedPacks(ArrayList<Pack> myBookedPacks) {
        this.myBookedPacks.setValue(myBookedPacks);
    }

    public LiveData<String> getSelectedPackName() {
        return selectedPackName;
    }

    public void setSelectedPackName(String selectedPackName) {
        this.selectedPackName.setValue(selectedPackName);
    }

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
