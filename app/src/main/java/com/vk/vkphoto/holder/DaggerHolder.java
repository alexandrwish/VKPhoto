package com.vk.vkphoto.holder;

import com.vk.vkphoto.VkApplication;
import com.vk.vkphoto.activity.LoginActivity;
import com.vk.vkphoto.activity.PreviewActivity;
import com.vk.vkphoto.component.DaggerVkComponent;
import com.vk.vkphoto.component.LoginComponent;
import com.vk.vkphoto.component.PreviewComponent;
import com.vk.vkphoto.component.VkComponent;
import com.vk.vkphoto.module.LoginModule;
import com.vk.vkphoto.module.PreviewModule;
import com.vk.vkphoto.module.VkModule;

public class DaggerHolder {

    private final VkComponent mComponent;
    private LoginComponent mLoginComponent;
    private PreviewComponent mPreviewComponent;

    public DaggerHolder() {
        mComponent = DaggerVkComponent.builder().vkModule(new VkModule()).build();
        mComponent.inject(VkApplication.getInstance());
    }

    public void plusLoginComponent(LoginActivity activity) {
        if (mLoginComponent == null) {
            mLoginComponent = mComponent.plus(new LoginModule(activity));
        }
        mLoginComponent.inject(activity);
    }

    public void removeLoginComponent() {
        mLoginComponent = null;
    }

    public void plusPreviewComponent(PreviewActivity activity) {
        if (mPreviewComponent == null) {
            mPreviewComponent = mComponent.plus(new PreviewModule(activity));
        }
        mPreviewComponent.inject(activity);
    }

    public void removePreviewComponent() {
        mPreviewComponent = null;
    }
}