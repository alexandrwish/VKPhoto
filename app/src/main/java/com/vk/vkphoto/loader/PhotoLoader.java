package com.vk.vkphoto.loader;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.vkphoto.VkApplication;
import com.vk.vkphoto.common.Constants;
import com.vk.vkphoto.http.VkClient;
import com.vk.vkphoto.presenter.PreviewPresenter;
import com.vk.vkphoto.record.ItemRecord;
import com.vk.vkphoto.record.PhotoInfoRecord;
import com.vk.vkphoto.record.PhotoRecord;
import com.vk.vkphoto.runnable.DownloadTask;
import com.vk.vkphoto.thread.VkThreadPool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class PhotoLoader {

    private final VkClient mClient;
    private final VkThreadPool mExecutor;
    private final SharedPreferences mPreferences;
    private PreviewPresenter mPresenter;

    @Inject
    public PhotoLoader(VkClient client, SharedPreferences preferences, VkThreadPool executor) {
        mClient = client;
        mExecutor = executor;
        mPreferences = preferences;
    }

    public void setPresenter(PreviewPresenter presenter) {
        mPresenter = presenter;
    }

    public void loadInfo() {
        VKRequest req = new VKRequest("photos.get", VKParameters.from(VKApiConst.OWNER_ID, mPreferences.getString(Constants.USER_ID, "0"), VKApiConst.ALBUM_ID, "wall"));
        req.executeWithListener(new VKRequest.VKRequestListener() {

            public void onComplete(VKResponse response) {
                mPresenter.updateInfo(Observable.just(new Gson().fromJson(response.responseString, PhotoInfoRecord.class).getResponse().getItems())
                        .subscribeOn(Schedulers.io())
                        .map(convertRecords())
                        .doOnNext(setImg()));
            }

            public void onError(VKError error) {
                Log.e(PhotoLoader.class.getName(), error.errorMessage, error.httpError);
            }
        });
    }

    private Func1<List<ItemRecord>, List<PhotoRecord>> convertRecords() {
        return new Func1<List<ItemRecord>, List<PhotoRecord>>() {
            public List<PhotoRecord> call(List<ItemRecord> items) {
                List<PhotoRecord> records = new ArrayList<>(items.size());
                for (ItemRecord itemRecord : items) {
                    records.add(new PhotoRecord(itemRecord.getId(),
                            itemRecord.getPhoto75(),
                            itemRecord.getPhoto807(),
                            itemRecord.getText(),
                            itemRecord.getDate(),
                            itemRecord.getHeight(),
                            itemRecord.getWidth(),
                            null,
                            null));
                }
                return records;
            }
        };
    }

    private Action1<? super List<PhotoRecord>> setImg() {
        return new Action1<List<PhotoRecord>>() {
            public void call(List<PhotoRecord> records) {
                for (PhotoRecord record : records) {
                    String filePath = VkApplication.getInstance().getCacheDir() + "/files/" + record.getId() + "_small";
                    if (new File(filePath).exists()) {
                        record.setLocationSmall(filePath);
                        record.setLocationLarge(VkApplication.getInstance().getCacheDir() + "/files/" + record.getId() + "_large");
                    } else {
                        mExecutor.post(new DownloadTask(record, mPresenter, mClient));
                    }
                }
            }
        };
    }
}