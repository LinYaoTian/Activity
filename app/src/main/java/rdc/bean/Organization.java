package rdc.bean;

import java.io.Serializable;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by asus on 18-4-14.
 */

public class Organization implements Serializable{
    private BmobFile mImage;
    private String mName;
    private String mNewSendTime;
    private String mMessage;
    private int mType  ;

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public BmobFile getImage() {
        return mImage;
    }

    public void setImage(BmobFile image) {
        mImage = image;
    }

    public String getName() {
        return mName == null ? "" : mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTime() {
        return mNewSendTime == null ? "" : mNewSendTime;
    }

    public void setTime(String time) {
        mNewSendTime = time;
    }

    public String getMessage() {
        return mMessage == null ? "" : mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
