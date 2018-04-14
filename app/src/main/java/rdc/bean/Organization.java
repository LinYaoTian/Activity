package rdc.bean;

/**
 * Created by asus on 18-4-14.
 */

public class Organization {
    private String mImage;
    private String mName;
    private String mTime;
    private String mMessage;

    public String getImage() {
        return mImage == null ? "" : mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getName() {
        return mName == null ? "" : mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTime() {
        return mTime == null ? "" : mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getMessage() {
        return mMessage == null ? "" : mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
