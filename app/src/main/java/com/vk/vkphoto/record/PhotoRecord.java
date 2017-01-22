package com.vk.vkphoto.record;

import android.os.Parcel;
import android.os.Parcelable;

public class PhotoRecord implements Parcelable {

    public static final Creator<PhotoRecord> CREATOR = new Creator<PhotoRecord>() {

        public PhotoRecord createFromParcel(Parcel in) {
            return new PhotoRecord(in);
        }

        public PhotoRecord[] newArray(int size) {
            return new PhotoRecord[size];
        }
    };

    private Integer id;
    private Integer date;
    private String text;
    private String urlSmall;
    private String urlLarge;
    private String locationSmall;
    private String locationLarge;
    private Integer width;
    private Integer height;

    public PhotoRecord() {
    }

    public PhotoRecord(Integer id, String urlSmall, String photo807, String text, Integer date, Integer height, Integer width, String locationSmall, String locationLarge) {
        this.id = id;
        this.urlSmall = urlSmall;
        this.urlLarge = photo807;
        this.text = text;
        this.date = date;
        this.height = height;
        this.width = width;
        this.locationSmall = locationSmall;
        this.locationLarge = locationLarge;
    }

    public PhotoRecord(Parcel in) {
        urlSmall = in.readString();
        urlLarge = in.readString();
        locationSmall = in.readString();
        locationLarge = in.readString();
        text = in.readString();
        height = in.readInt();
        width = in.readInt();
        date = in.readInt();
        id = in.readInt();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrlSmall() {
        return urlSmall;
    }

    public void setUrlSmall(String urlSmall) {
        this.urlSmall = urlSmall;
    }

    public String getUrlLarge() {
        return urlLarge;
    }

    public void setUrlLarge(String urlLarge) {
        this.urlLarge = urlLarge;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getLocationSmall() {
        return locationSmall;
    }

    public void setLocationSmall(String locationSmall) {
        this.locationSmall = locationSmall;
    }

    public String getLocationLarge() {
        return locationLarge;
    }

    public void setLocationLarge(String locationLarge) {
        this.locationLarge = locationLarge;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(urlSmall);
        dest.writeString(urlLarge);
        dest.writeString(locationSmall);
        dest.writeString(locationLarge);
        dest.writeString(text);
        dest.writeInt(height);
        dest.writeInt(width);
        dest.writeInt(date);
        dest.writeInt(id);
    }
}