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
    private BmobRelation conncerned;
    private BmobRelation trip;

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", userImg=" + userImg +
                ", university='" + university + '\'' +
                ", introduction='" + introduction + '\'' +
                ", conncerned=" + conncerned +
                ", trip=" + trip +
                '}';
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
        return userPhoto;
    }

    public void setUserPhoto(BmobFile userPhoto) {
        this.userPhoto = userPhoto;
    }
}
