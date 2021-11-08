package com.example.guidepage;

import android.app.Application;

import com.tencent.mmkv.MMKV;

public class App extends Application {

    @Override
    public void onCreate() {
     super.onCreate();
        //chus初始化MMKV
        MMKV.initialize(this);
    }
}
