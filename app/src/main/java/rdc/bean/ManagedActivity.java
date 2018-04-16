package rdc.bean;

/**
 * Created by asus on 18-4-14.
 */

public class ManagedActivity {
     private String mImageUrl;
     private String mTitle;
     private String mSendTime;
     private int mType ;

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getImageUrl() {
        return mImageUrl == null ? "" : mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getTitle() {
        return mTitle == null ? "" : mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSendTime() {
        return mSendTime == null ? "" : mSendTime;
    }

    public void setSendTime(String sendTime) {
        mSendTime = sendTime;
    }
}
