package com.example.ewd.diagram.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ewd.diagram.model.local.database.UserDatabase;
import com.example.ewd.diagram.model.local.entities.User;

public class UserViewModel extends ViewModel {

    private LiveData<User> user;

    public UserViewModel(UserDatabase database, String userId) {
        user = database.userDao().loadUserById(userId);
    }

    public LiveData<User> getUser() {
        return user;
    }
}
