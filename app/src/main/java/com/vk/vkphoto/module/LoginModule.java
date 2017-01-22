package com.vk.vkphoto.module;

import com.vk.vkphoto.activity.LoginActivity;
import com.vk.vkphoto.component.ActivityScope;
import com.vk.vkphoto.listener.LoginListener;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule extends ActivityModule<LoginActivity> {

    public LoginModule(LoginActivity activity) {
        super(activity);
    }

    @Provides
    @ActivityScope
    public LoginListener provideLoginListener() {
        return mActivity;
    }
}