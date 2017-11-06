package com.just.valley.reciter.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Valley on 2017/10/27.
 */

public class ContentFormat implements Parcelable{

    private String mTitle;
    private String mText;
    private int mCharacterNumber;
    private String mTextOrigin;

    public ContentFormat(String Title,String Text,String TextOrigin,int CharacterNumber){
        mText = Text;
        mTitle = Title;
        mCharacterNumber = CharacterNumber;
        mTextOrigin = TextOrigin;
    }

    protected ContentFormat(Parcel in) {
        mTitle = in.readString();
        mText = in.readString();
        mCharacterNumber = in.readInt();
        mTextOrigin = in.readString();
    }

    public static final Creator<ContentFormat> CREATOR = new Creator<ContentFormat>() {
        @Override
        public ContentFormat createFromParcel(Parcel in) {
            return new ContentFormat(in);
        }

        @Override
        public ContentFormat[] newArray(int size) {
            return new ContentFormat[size];
        }
    };

    public String getmTitle() {
        return mTitle;
    }

    public String getmText() {
        return mText;
    }

    public int getmCharacterNumber() {
        return mCharacterNumber;
    }

    public String getmTextOrigin() {
        return mTextOrigin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mText);
        parcel.writeInt(mCharacterNumber);
        parcel.writeString(mTextOrigin);
    }
}
