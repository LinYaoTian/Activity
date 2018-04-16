package rdc.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import com.zxy.tiny.callback.FileCallback;

import java.io.File;

import rdc.base.BasePresenter;
import rdc.bean.User;
import rdc.contract.IIndividualContract;
import rdc.model.IndividualModel;
import rdc.util.CompressImageUtil;

/**
 * Created by asus on 18-4-15.
 */

public class IndividualPresenter extends BasePresenter<IIndividualContract.View> implements IIndividualContract.Presenter{
  private IndividualModel mModel;
  private boolean isNext;
  private String imagefile;

    public IndividualPresenter() {
        mModel = new IndividualModel();
    }

    @Override
    public void setUserInfo(User userInfo) {
        getMvpView().setUserInfo(userInfo);
    }

    @Override
    public void getUserInfo() {
       mModel.getUserInfo(this);
    }

    @Override
    public void takePhoto() {
        getMvpView().takePhoto(mModel.getmImageUrl());

    }

    @Override
    public void chooseAlbum() {
        getMvpView().openAlbum();

    }

    @Override
    public void updateUser() {
        if (!TextUtils.isEmpty(getMvpView().getImageFilePath())){
            CompressImageUtil.compressImage(getMvpView().getImageFilePath(), new FileCallback() {
                @Override
                public void callback(boolean isSuccess, String outfile, Throwable t) {
                    imagefile = outfile;
                    isNext = isSuccess;
                }
            });
        }
        if (!TextUtils.isEmpty(getMvpView().getPhotoFilePath())&&isNext){
            CompressImageUtil.compressImage(getMvpView().getPhotoFilePath(), new FileCallback() {
                @Override
                public void callback(boolean isSuccess, String outfile, Throwable t) {
                    if (isNext&&isSuccess){
                        mModel.updateUser(imagefile,outfile,getMvpView().getName(),getMvpView().getIntroduction(),getMvpView().getIntroduction(),IndividualPresenter.this);
                    }
                }
            });
        }else {



        }




    }



    @Override
    public void setAlbumPhoto(Intent intent) {
        if (Build.VERSION.SDK_INT >= 19) {
            getMvpView().setAlbumImage(mModel.handleImageOnKitKat(intent));
        } else {
            Uri uri = intent.getData();
            getMvpView().setAlbumImage(mModel.getmImageFilePath(uri,null));

        }
    }
}
