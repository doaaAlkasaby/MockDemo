package com.mock.mockdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by 007 on 10/07/2017.
 */

public class Image  implements Parcelable, Serializable {
    int width, height;
    String url;
Image parcable;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //-----------------------------


    @Override
    public String toString() {
        return getUrl();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeInt(width);
        out.writeInt(height);
        out.writeString(url);


        out.writeParcelable(parcable, flags);
    }

    private Image(Parcel in) {
        width = in.readInt();
        height = in.readInt();

        url = in.readString();


        parcable = in.readParcelable(Product.class.getClassLoader());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
