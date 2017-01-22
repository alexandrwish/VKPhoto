package com.vk.vkphoto.component;

import com.vk.vkphoto.activity.LoginActivity;
import com.vk.vkphoto.module.LoginModule;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {LoginModule.class})
public interface LoginComponent {

    void inject(LoginActivity activity);
}