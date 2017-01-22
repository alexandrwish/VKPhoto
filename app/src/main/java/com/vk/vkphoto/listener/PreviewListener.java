package com.vk.vkphoto.listener;

import com.vk.vkphoto.record.PhotoRecord;

import java.util.List;

public interface PreviewListener {

    void showPhotos(List<PhotoRecord> records);

    void showPhoto(PhotoRecord record);
}