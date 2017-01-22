package com.vk.vkphoto.component;

import com.vk.vkphoto.VkApplication;
import com.vk.vkphoto.module.LoginModule;
import com.vk.vkphoto.module.PreviewModule;
import com.vk.vkphoto.module.VkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {VkModule.class})
public interface VkComponent {

    void inject(VkApplication application);

    LoginComponent plus(LoginModule module);

    PreviewComponent plus(PreviewModule module);
}