package rdc.bean;

import java.io.Serializable;

/**
 * Created by asus on 18-4-18.
 */

public class OrganizationActivity implements Serializable {
    private String coverImageUrl;
    private String title;
    private String location;
    private String time;
    private int sawNum;
    private int type;
    private String id;
    private String image;
    private String photo;
    private String name;
    private String introduction;

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoverImageUrl() {
        return coverImageUrl == null ? "" : coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location == null ? "" : location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time == null ? "" : time;
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

    public String getImage() {
        return image == null ? "" : image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhoto() {
        return photo == null ? "" : photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction == null ? "" : introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
