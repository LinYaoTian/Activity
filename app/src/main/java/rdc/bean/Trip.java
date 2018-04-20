package rdc.bean;

import java.io.Serializable;

/**
 * Created by asus on 18-4-14.
 */

public class Trip implements Serializable {
    private String objectId;
    private String coverImageUrl;
    private String title;
    private String location;
    private String time;
    private int sawNum;


    private int type;

    @Override
    public boolean equals(Object obj) {
        return this.equals(((Trip)obj).getObjectId());
    }

    public String getObjectId() {
        return objectId == null ? "" : objectId;
    }

    public void setObjectId(String obiectId) {
        this.objectId = obiectId;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSawNum() {
        return sawNum;
    }

    public void setSawNum(int sawNum) {
        this.sawNum = sawNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
