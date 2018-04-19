package rdc.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Lin Yaotian on 2018/4/13.
 */

public class User extends BmobUser {

    private String nickname;
    private BmobFile userImg;
    private BmobFile userPhoto;
    private String university;
    private String introduction;
    private String newSendTime;
    private String newActivityTitle;
    private BmobRelation conncerned;
    private BmobRelation trip;
    private String tagsOrder;

    public String getNewActivityTitle() {
        return newActivityTitle;
    }

    public User setNewActivityTitle(String newActivityTitle) {
        this.newActivityTitle = newActivityTitle;
        return this;
    }

    public String getTagsOrder() {
        return tagsOrder;
    }

    public User setTagsOrder(String tagsOrder) {
        this.tagsOrder = tagsOrder;
        return this;
    }

    public BmobRelation getTrip() {
        return trip;
    }

    public void setTrip(BmobRelation trip) {
        this.trip = trip;
    }

    public String getIntroduction() {
        return introduction == null ? "" : introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public BmobFile getUserImg() {
        return userImg == null ? new BmobFile() : userImg;
    }

    public void setUserImg(BmobFile userImg) {
        this.userImg = userImg;
    }

    public BmobRelation getConncerned() {
        return conncerned;
    }

    public void setConncerned(BmobRelation conncerned) {
        this.conncerned = conncerned;
    }

    public BmobFile getUserPhoto() {
        return userPhoto == null ? new BmobFile():userPhoto;
    }

    public void setUserPhoto(BmobFile userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getNewSendTime() {
        return newSendTime == null ? "" : newSendTime;
    }

    public void setNewSendTime(String newSendTime) {
        this.newSendTime = newSendTime;
    }

    public String getNewsActivityTitle() {
        return newActivityTitle == null ? "" : newActivityTitle;
    }

    public void setNewsActivityTitle(String newsActivityTitle) {
        this.newActivityTitle = newsActivityTitle;
    }
}
