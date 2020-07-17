package com.example.teachersinformations;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class TeachersInformations extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
