package com.microlend.microlend;

import android.app.Application;

import org.litepal.LitePalApplication;

/**
 * Created by young on 2017/5/11.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        LitePalApplication.initialize(this);
        super.onCreate();
    }
}