package com.example.ewd.diagram.viewmodel;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.ewd.diagram.model.local.database.UserDatabase;

public class UserViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final UserDatabase mDb;
    private final String mUserId;

    public UserViewModelFactory(UserDatabase database, String userId) {
        mDb = database;
        mUserId = userId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new UserViewModel(mDb, mUserId);
    }
}