package com.vk.vkphoto.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.vk.vkphoto.R;
import com.vk.vkphoto.record.PhotoRecord;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreviewAdapter extends ArrayAdapter<PhotoRecord> {

    public PreviewAdapter(Context context) {
        super(context, R.layout.item_grid, new LinkedList<PhotoRecord>());
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_grid, parent, false);
            v.setTag(new ViewHolder(v));
        } else {
            v = convertView;
        }
        final PhotoRecord record = getItem(position);
        if (record != null) {
            ViewHolder holder = (ViewHolder) v.getTag();
            if (record.getLocationSmall() == null) {
                holder.img.setImageDrawable(getContext().getDrawable(R.mipmap.ic_launcher));
            } else {
                holder.img.setImageDrawable(Drawable.createFromPath(record.getLocationSmall()));
            }
        }
        return v;
    }

    protected class ViewHolder {

        @BindView(R.id.img)
        protected ImageView img;

        ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }
}