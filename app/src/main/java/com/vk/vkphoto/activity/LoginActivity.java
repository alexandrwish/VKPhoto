package com.vk.vkphoto.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.vk.sdk.VKSdk;
import com.vk.vkphoto.R;
import com.vk.vkphoto.VkApplication;
import com.vk.vkphoto.listener.LoginListener;
import com.vk.vkphoto.presenter.LoginPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity implements LoginListener {

    @Inject
    protected LoginPresenter mPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        VkApplication.getInstance().getHolder().plusLoginComponent(this);
    }

    protected void onDestroy() {
        VkApplication.getInstance().getHolder().removeLoginComponent();
        super.onDestroy();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, mPresenter)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void showPreview() {
        startActivity(new Intent(LoginActivity.this, PreviewActivity.class));
    }

    @OnClick(R.id.login_btn)
    public void onClick() {
        mPresenter.login(this);
    }
}