package com.dhimandasgupta.savednews.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dhimandasgupta on 01/06/17.
 */

public class News implements Parcelable {
    private String name;
    private String source;

    public News(final String name, final String source) {
        this.name = name;
        this.source = source;
    }

    private News(Parcel in) {
        name = in.readString();
        source = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(source);
    }

    @Override
    public String toString() {
        return "News{" +
                "name='" + name + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
