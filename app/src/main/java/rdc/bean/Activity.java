package rdc.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by asus on 18-4-14.
 */

public class Activity extends BmobObject {
    private String title;
    private String time;
    private String sendtime;
    private Integer sawnum;
    private String place;
    private String content;
    private User manager;
    private BmobFile image;
    private BmobRelation attcipator;
    private String university;
    private String tag;

    @Override
    public String toString() {
        return "Activity{" +"createdAt:"+getCreatedAt()+
                '}';
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSendtime() {
        return sendtime == null ? "" : sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public Integer getSawnum() {
        return sawnum;
    }

    public void setSawnum(Integer sawnum) {
        this.sawnum = sawnum;
    }

    public String getPlace() {
        return place == null ? "" : place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public BmobFile getImage() {
        return image == null ? new BmobFile():image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public BmobRelation getAttcipator() {
        return attcipator;
    }

    public void setAttcipator(BmobRelation attcipator) {
        this.attcipator = attcipator;
    }
}
