package com.app.musicapp;

import android.app.Application;

import com.app.musicapp.util.PreferenceUtil;


public class App extends Application {
    private static App mInstance;

    public static synchronized App getInstance() {
        return mInstance;
    }

    public PreferenceUtil getPreferencesUtility() {
        return PreferenceUtil.getInstance(App.this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //    Nammu.init(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}