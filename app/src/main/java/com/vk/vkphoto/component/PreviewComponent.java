package com.vk.vkphoto.component;

import com.vk.vkphoto.activity.PreviewActivity;
import com.vk.vkphoto.module.PreviewModule;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {PreviewModule.class})
public interface PreviewComponent {

    void inject(PreviewActivity activity);
}