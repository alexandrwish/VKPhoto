package com.vk.vkphoto.presenter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.vkphoto.common.Constants;
import com.vk.vkphoto.listener.LoginListener;

import javax.inject.Inject;

public class LoginPresenter implements VKCallback<VKAccessToken> {

    private final SharedPreferences mPreferences;
    private final LoginListener mListener;

    @Inject
    public LoginPresenter(SharedPreferences preferences, LoginListener listener) {
        mPreferences = preferences;
        mListener = listener;
    }

    public void login(Activity activity) {
        VKSdk.login(activity);
    }

    public void onResult(VKAccessToken res) {
        // Пользователь успешно авторизовался
        mPreferences.edit()
                .putString(Constants.USER_ID, res.userId)
                .putString(Constants.ACCESS_TOKEN, res.accessToken)
                .apply();
        mListener.showPreview();
    }

    public void onError(VKError error) {
        // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
        Log.e(LoginPresenter.class.getName(), error.errorMessage, error.httpError);
    }
}