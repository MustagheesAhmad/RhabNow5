package com.example.rehabnow;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.rehabnow.data.User;
import com.example.rehabnow.db.AppDatabase;

public class MyApplication extends Application {

    public static MyApplication instance = null;

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    private AppDatabase db;
    private User currentUser;
    @Override
    public void onCreate() {
        super.onCreate();
        initDb(this);
    }

    private void initDb(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "rehab-now").allowMainThreadQueries().build();
    }


    public AppDatabase getDb(Context context) {
        if(db == null) {
            initDb(context);
        }
        return db;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
