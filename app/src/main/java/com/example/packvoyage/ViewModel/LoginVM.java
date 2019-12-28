package com.example.packvoyage.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.packvoyage.model.User;

public class LoginVM extends androidx.lifecycle.ViewModel{
    private MutableLiveData<User> loggedUser = new MutableLiveData<>();

    public LiveData<User> getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser.setValue(loggedUser);
    }
}
