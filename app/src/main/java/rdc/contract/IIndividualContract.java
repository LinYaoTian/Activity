package rdc.contract;

import android.content.Intent;
import android.net.Uri;

import rdc.bean.User;

/**
 * Created by asus on 18-4-15.
 */

public interface IIndividualContract {
    interface View {

        void  setUserInfo(User userInfo);
        void takePhoto(Uri uri);
        void openAlbum();
        void setPhotoImage();
        void setAlbumImage(String imagePath);
        String getImageFilePath();
        String getPhotoFilePath();
        String getName();
        String getIntroduction();
        String getUniversity();
        void back();


    }


    interface Model{
        void getUserInfo(Presenter presenter);
        void updateUserWithAllFile(String imageUrl,String Photo,String name,String introduction,String university,Presenter presenter);
        void updateUserWithPhoto(String Photo,String name,String introduction,String university,Presenter presenter);
        void updateUserWithImage(String imageUrl,String name,String introduction,String university,Presenter presenter);
        void updateUserWithNoneFile(String name,String introduction,String university,Presenter presenter);

    }

    interface Presenter{

        void updateUser();

        void takePhoto();
        void chooseAlbum();
        void setAlbumPhoto(Intent intent);

        void  setUserInfo(User userInfo);
        void getUserInfo();
        void back();


    }
}
