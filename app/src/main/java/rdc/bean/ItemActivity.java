package rdc.bean;

/**
 * Created by Lin Yaotian on 2018/4/13.
 */

public class ItemActivity {
    private String coverImageUrl;
    private String title;
    private String location;
    private String time;
    private int sawNum;

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
}
