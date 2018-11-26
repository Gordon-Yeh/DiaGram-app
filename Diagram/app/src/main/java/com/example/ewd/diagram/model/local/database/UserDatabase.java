package com.example.ewd.diagram.model.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.ewd.diagram.model.local.dao.UserDao;
import com.example.ewd.diagram.model.local.entities.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase{

    private static final String LOG_TAG = UserDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "users";
    private static UserDatabase sInstance;

    public static UserDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        UserDatabase.class, UserDatabase.DATABASE_NAME)
                        .build();
            }
        }

        return sInstance;
    }

    public abstract UserDao userDao();

}
