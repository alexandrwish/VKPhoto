package com.vk.vkphoto;

import android.app.Application;
import android.content.SharedPreferences;

import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;
import com.vk.vkphoto.holder.DaggerHolder;

import javax.inject.Inject;

public class VkApplication extends Application {

    private static VkApplication sInstance;

    @Inject
    protected VKAccessTokenTracker mTokenTracker;
    @Inject
    protected SharedPreferences mPreferences;

    private DaggerHolder mHolder;

    public static VkApplication getInstance() {
        return sInstance;
    }

    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mHolder = new DaggerHolder();
        mTokenTracker.startTracking();
        VKSdk.initialize(this);
    }

    public DaggerHolder getHolder() {
        return mHolder;
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }
}