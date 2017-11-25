package com.redstar.alexandr.test_task_for_it_01;

import android.app.Application;

import com.vk.sdk.VKSdk;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        VKSdk.initialize(getApplicationContext());
    }
}
