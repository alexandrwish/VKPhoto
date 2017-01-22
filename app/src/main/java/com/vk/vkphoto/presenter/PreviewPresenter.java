package com.vk.vkphoto.presenter;

import android.util.Log;

import com.vk.vkphoto.listener.PreviewListener;
import com.vk.vkphoto.loader.PhotoLoader;
import com.vk.vkphoto.record.PhotoRecord;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class PreviewPresenter {

    private final PhotoLoader mLoader;
    private final PreviewListener mListener;

    @Inject
    public PreviewPresenter(PhotoLoader loader, PreviewListener listener) {
        mLoader = loader;
        mListener = listener;
        mLoader.setPresenter(this);
        mLoader.loadInfo();
    }

    public void updateInfo(Observable<List<PhotoRecord>> records) {
        records.observeOn(Schedulers.io())
                .subscribe(new Subscriber<List<PhotoRecord>>() {
                    public void onCompleted() {

                    }

                    public void onError(Throwable e) {
                        Log.e(PreviewPresenter.class.getName(), e.getMessage(), e);
                    }

                    public void onNext(List<PhotoRecord> items) {
                        mListener.showPhotos(items);
                    }
                });
    }

    public void updatePhoto(PhotoRecord mRecord) {
        mListener.showPhoto(mRecord);
    }
}