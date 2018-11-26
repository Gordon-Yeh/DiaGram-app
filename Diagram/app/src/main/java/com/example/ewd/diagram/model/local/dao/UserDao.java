package com.example.ewd.diagram.model.local.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.ewd.diagram.model.local.entities.User;

@Dao
public interface UserDao {

    //Query to insert User to db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    //Query to update user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(User user);

    @Query("SELECT * FROM users WHERE id = :id")
    LiveData<User> loadUserById(String id);
}
