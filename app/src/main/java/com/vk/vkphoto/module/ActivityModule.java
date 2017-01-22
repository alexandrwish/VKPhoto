package com.vk.vkphoto.module;

import android.app.Activity;

public abstract class ActivityModule<A extends Activity> {

    protected final A mActivity;

    public ActivityModule(A activity) {
        mActivity = activity;
    }
}