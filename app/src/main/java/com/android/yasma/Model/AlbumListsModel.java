package com.android.yasma.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class AlbumListsModel implements Parcelable {
    private Integer userId;
    private Integer id;
    private String title;

    protected AlbumListsModel(Parcel in) {
        if (in.readByte() == 0) {
            userId = null;
        } else {
            userId = in.readInt();
        }
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
    }

    public static final Creator<AlbumListsModel> CREATOR = new Creator<AlbumListsModel>() {
        @Override
        public AlbumListsModel createFromParcel(Parcel in) {
            return new AlbumListsModel(in);
        }

        @Override
        public AlbumListsModel[] newArray(int size) {
            return new AlbumListsModel[size];
        }
    };

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (userId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(userId);
        }
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(title);
    }
}
