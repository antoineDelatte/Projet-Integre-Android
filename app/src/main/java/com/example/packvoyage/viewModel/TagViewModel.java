package com.example.packvoyage.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.packvoyage.model.Tag;

import java.util.List;

public class TagViewModel extends ViewModel {
    private MutableLiveData<List<Tag>> users;

    public LiveData<List<Tag>> getTags() {
        if (users == null) {
            users = new MutableLiveData<List<Tag>>();
            loadUsers();
        }
        return users;
    }

    private void loadUsers() {
        // Network call
    }
}
