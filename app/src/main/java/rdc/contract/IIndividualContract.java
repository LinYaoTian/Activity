package rdc.contract;

import android.content.Intent;
import android.net.Uri;

import rdc.bean.User;

/**
 * Created by asus on 18-4-15.
 */

public interface IIndividualContract {
    interface View {

        void takePhoto(Uri uri);
        void openAlbum();
        void setPhotoImage();
        void setAlbumImage(String imagePath);
        String getImageFilePath();
        String getPhotoFilePath();
        String getName();
        String getIntroduction();
        String getUniversity();
        String getPhone();
        void back();
        void setOnError();
    }


    interface Model{
        void updateUserWithAllFile(String imageUrl,String Photo,String name,String introduction,String phone,String university,Presenter presenter);
        void updateUserWithPhoto(String Photo,String name,String introduction,String university,String phone,Presenter presenter);
        void updateUserWithImage(String imageUrl,String name,String introduction,String university,String phone,Presenter presenter);
        void updateUserWithNoneFile(String name,String introduction,String university,String phone,Presenter presenter);

    }

    interface Presenter{

        void updateUser();

        void takePhoto();
        void chooseAlbum();
        void setAlbumPhoto(Intent intent);

        void back();
        void onError();


    }
}
