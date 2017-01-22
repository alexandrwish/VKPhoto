package com.vk.vkphoto.module;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.vkphoto.VkApplication;
import com.vk.vkphoto.common.Constants;
import com.vk.vkphoto.thread.VkThreadPool;

import java.util.concurrent.LinkedBlockingDeque;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class VkModule {

    @Provides
    @Singleton
    public VKAccessTokenTracker provideTokenTracker() {
        return new VKAccessTokenTracker() {
            public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
                if (newToken == null) {
                    VkApplication.getInstance().getPreferences().edit()
                            .putString(Constants.USER_ID, "")
                            .putString(Constants.ACCESS_TOKEN, "")
                            .apply();
                }
            }
        };
    }

    @Provides
    @Singleton
    public SharedPreferences providePreferences() {
        return PreferenceManager.getDefaultSharedPreferences(VkApplication.getInstance());
    }

    @Provides
    @Singleton
    public VkThreadPool provideThreadPool() {
        return new VkThreadPool(Runtime.getRuntime().availableProcessors(), new LinkedBlockingDeque<Runnable>());
    }
}