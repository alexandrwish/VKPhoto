package com.vk.vkphoto.runnable;

import android.util.Log;

import com.vk.vkphoto.VkApplication;
import com.vk.vkphoto.http.VkClient;
import com.vk.vkphoto.presenter.PreviewPresenter;
import com.vk.vkphoto.record.PhotoRecord;

import java.io.File;
import java.io.IOException;

import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DownloadTask implements Runnable {

    private final VkClient mClient;
    private final PhotoRecord mRecord;
    private final PreviewPresenter mPresenter;

    public DownloadTask(PhotoRecord record, PreviewPresenter presenter, VkClient client) {
        mRecord = record;
        mClient = client;
        mPresenter = presenter;
    }

    public void run() {
        mClient.loadPhoto(mRecord.getUrlSmall())
                .flatMap(processResponse(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleResult());

        mClient.loadPhoto(mRecord.getUrlLarge())
                .flatMap(processResponse(false))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleResult());
    }

    private Func1<Response<ResponseBody>, Observable<PhotoRecord>> processResponse(final boolean isSmall) {
        return new Func1<Response<ResponseBody>, Observable<PhotoRecord>>() {
            public Observable<PhotoRecord> call(Response<ResponseBody> responseBodyResponse) {
                return saveToDiskRx(responseBodyResponse, isSmall);
            }
        };
    }

    private Observable<PhotoRecord> saveToDiskRx(final Response<ResponseBody> response, final boolean isSmall) {
        return Observable.create(new Observable.OnSubscribe<PhotoRecord>() {
            public void call(Subscriber<? super PhotoRecord> subscriber) {
                try {
                    String filePath = VkApplication.getInstance().getCacheDir() + "/files/";
                    if (new File(filePath).mkdirs()) {
                        Log.d(DownloadTask.class.getName(), "Создали директорию");
                    }
                    File destinationFile = new File(filePath + mRecord.getId() + (isSmall ? "_small" : "_large"));
                    if (isSmall) {
                        mRecord.setLocationSmall(destinationFile.getAbsolutePath());
                    } else {
                        mRecord.setLocationLarge(destinationFile.getAbsolutePath());
                    }
                    BufferedSink bufferedSink = Okio.buffer(Okio.sink(destinationFile));
                    bufferedSink.writeAll(response.body().source());
                    bufferedSink.close();
                    subscriber.onNext(mRecord);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    Log.e(DownloadTask.class.getName(), e.getMessage(), e);
                    subscriber.onError(e);
                }
            }
        });
    }

    private Observer<PhotoRecord> handleResult() {
        return new Observer<PhotoRecord>() {
            public void onCompleted() {
                Log.d(DownloadTask.class.getName(), "onCompleted");
            }

            public void onError(Throwable e) {
                Log.e(DownloadTask.class.getName(), e.getMessage(), e);
            }

            public void onNext(PhotoRecord record) {
                mPresenter.updatePhoto(record);
            }
        };
    }
}