package com.example.ibrahimelhout.bakingapp_project4.Utils;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApp extends Application {

    private static Retrofit retrofit = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();


    }



}
