package com.vk.vkphoto.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.GridView;

import com.vk.vkphoto.R;
import com.vk.vkphoto.VkApplication;
import com.vk.vkphoto.adapter.PreviewAdapter;
import com.vk.vkphoto.common.Constants;
import com.vk.vkphoto.listener.PreviewListener;
import com.vk.vkphoto.presenter.PreviewPresenter;
import com.vk.vkphoto.record.PhotoRecord;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class PreviewActivity extends Activity implements PreviewListener {

    @BindView(R.id.grid_preview)
    protected GridView gridView;

    @Inject
    protected PreviewPresenter mPresenter;

    private PreviewAdapter mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);
        mAdapter = new PreviewAdapter(this);
        gridView.setAdapter(mAdapter);
        VkApplication.getInstance().getHolder().plusPreviewComponent(this);
    }

    protected void onDestroy() {
        VkApplication.getInstance().getHolder().removePreviewComponent();
        super.onDestroy();
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void showPhotos(final List<PhotoRecord> records) {
        runOnUiThread(new Runnable() {
            public void run() {
                mAdapter.clear();
                mAdapter.addAll(records);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public void showPhoto(final PhotoRecord record) {
        runOnUiThread(new Runnable() {
            public void run() {
                for (int i = 0; i < mAdapter.getCount(); i++) {
                    PhotoRecord r = mAdapter.getItem(i);
                    if (r != null && r.getId().equals(record.getId())) {
                        r.setLocationSmall(record.getLocationSmall());
                        break;
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnItemClick(R.id.grid_preview)
    public void onItemClick(int position) {
        Parcelable[] parcelables = new Parcelable[mAdapter.getCount()];
        for (int i = 0; i < mAdapter.getCount(); i++) {
            parcelables[i] = mAdapter.getItem(i);
        }
        startActivity(new Intent(PreviewActivity.this, PhotoActivity.class)
                .putExtra(Constants.CURRENT, mAdapter.getItem(position))
                .putExtra(Constants.OTHER, parcelables));
    }
}