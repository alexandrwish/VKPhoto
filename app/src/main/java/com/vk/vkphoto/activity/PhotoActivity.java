package com.vk.vkphoto.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.vk.vkphoto.R;
import com.vk.vkphoto.adapter.PhotoAdapter;
import com.vk.vkphoto.common.Constants;
import com.vk.vkphoto.record.PhotoRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoActivity extends FragmentActivity {

    @BindView(R.id.pager)
    protected ViewPager mPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        Parcelable[] parcelables = getIntent().getParcelableArrayExtra(Constants.OTHER);
        PhotoRecord record = getIntent().getParcelableExtra(Constants.CURRENT);
        List<PhotoRecord> records = new ArrayList<>(parcelables.length);
        int j = 0, p = 0;
        for (Parcelable parcelable : parcelables) {
            records.add((PhotoRecord) parcelable);
            if (!Objects.equals(record.getId(), ((PhotoRecord) parcelable).getId())) {
                j++;
            } else {
                p = j;
            }
        }
        PagerAdapter adapter = new PhotoAdapter(getSupportFragmentManager(), records);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(p, true);
    }
}