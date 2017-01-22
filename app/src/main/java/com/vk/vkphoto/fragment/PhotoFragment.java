package com.vk.vkphoto.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vk.vkphoto.R;
import com.vk.vkphoto.common.Constants;
import com.vk.vkphoto.record.PhotoRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoFragment extends Fragment {

    @BindView(R.id.date)
    TextView mDate;
    @BindView(R.id.comment)
    TextView mComment;
    @BindView(R.id.img)
    ImageView mImg;
    private PhotoRecord mRecord;

    public static PhotoFragment newInstance(PhotoRecord record) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(Constants.CURRENT, record);
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecord = getArguments().getParcelable(Constants.CURRENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, null);
        ButterKnife.bind(this, view);
        mComment.setText(mRecord.getText());
        mImg.setImageDrawable(Drawable.createFromPath(mRecord.getLocationLarge()));
        mDate.setText(new SimpleDateFormat("d MMM HH:mm", Locale.UK).format(new Date(Long.valueOf(mRecord.getDate()))));
        return view;
    }
}
