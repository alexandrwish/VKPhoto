package com.vk.vkphoto.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vk.vkphoto.fragment.PhotoFragment;
import com.vk.vkphoto.record.PhotoRecord;

import java.util.List;

public class PhotoAdapter extends FragmentPagerAdapter {

    private final List<PhotoRecord> mRecords;

    public PhotoAdapter(FragmentManager fm, List<PhotoRecord> records) {
        super(fm);
        mRecords = records;
    }

    public Fragment getItem(int position) {
        return PhotoFragment.newInstance(mRecords.get(position));
    }

    public int getCount() {
        return mRecords.size();
    }

    public CharSequence getPageTitle(int position) {
        return "Фото " + position;
    }
}
