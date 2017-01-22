package com.vk.vkphoto.module;

import com.google.gson.GsonBuilder;
import com.vk.vkphoto.activity.PreviewActivity;
import com.vk.vkphoto.component.ActivityScope;
import com.vk.vkphoto.http.VkClient;
import com.vk.vkphoto.listener.PreviewListener;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class PreviewModule extends ActivityModule<PreviewActivity> {

    public PreviewModule(PreviewActivity activity) {
        super(activity);
    }

    @Provides
    @ActivityScope
    public PreviewListener providePreviewListener() {
        return mActivity;
    }

    @Provides
    @ActivityScope
    public VkClient provideVkClient() {
        return new Retrofit.Builder().baseUrl("https://vk.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(VkClient.class);
    }
}