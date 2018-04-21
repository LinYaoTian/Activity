package rdc.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.zxy.tiny.callback.FileCallback;

import java.io.File;

import rdc.activity.IndividualActivity;
import rdc.base.BasePresenter;
import rdc.bean.User;
import rdc.contract.IIndividualContract;
import rdc.model.IndividualModel;
import rdc.util.ImageUtil;

/**
 * Created by asus on 18-4-15.
 */

public class IndividualPresenter extends BasePresenter<IIndividualContract.View> implements IIndividualContract.Presenter {
    private IndividualModel mModel;

    private String imagefile;
    private static final String TAG = "IndividualPresenter";

    public IndividualPresenter() {
        mModel = new IndividualModel();
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
    public void onError() {
        getMvpView().setOnError();
    }

    @Override
    public void updateUser() {
        final String imageFilePath = getMvpView().getImageFilePath();
        final String photoFilePath = getMvpView().getPhotoFilePath();
        //如果背景和头像头不为空，都需要提交
        // 如果一个有一个没有改变就之提交一部分，如果图片都没有更改就提交一部分数据
        if (!TextUtils.isEmpty(imageFilePath) && !TextUtils.isEmpty(photoFilePath)) {
            ImageUtil.compressImage(imageFilePath, new FileCallback() {
                @Override
                public void callback(boolean isSuccess, String outfile, Throwable t) {
                    imagefile = outfile;
                    if (isSuccess) {
                        ImageUtil.compressImage(photoFilePath, new FileCallback() {
                            @Override
                            public void callback(boolean isSuccess, String outfile, Throwable t) {
                                if (isSuccess) {
                                    mModel.updateUserWithAllFile(imagefile, outfile, getMvpView().getName(), getMvpView().getIntroduction(), getMvpView().getUniversity(), IndividualPresenter.this);
                                }
                            }
                        });
                    } else {


                    }

                }
            });
        } else if (!TextUtils.isEmpty(imageFilePath) && TextUtils.isEmpty(photoFilePath)) {
            ImageUtil.compressImage(imageFilePath, new FileCallback() {
                @Override
                public void callback(boolean isSuccess, String outfile, Throwable t) {
                    if (isSuccess) {
                        mModel.updateUserWithImage(outfile, getMvpView().getName(), getMvpView().getIntroduction(), getMvpView().getUniversity(), IndividualPresenter.this);
                    }
                }
            });
        } else if (TextUtils.isEmpty(imageFilePath) && !TextUtils.isEmpty(photoFilePath)) {
            ImageUtil.compressImage(photoFilePath, new FileCallback() {
                @Override
                public void callback(boolean isSuccess, String outfile, Throwable t) {
                    if (isSuccess) {
                        mModel.updateUserWithPhoto(outfile, getMvpView().getName(), getMvpView().getIntroduction(), getMvpView().getUniversity(), IndividualPresenter.this);

                    }
                }
            });
        } else {
            mModel.updateUserWithNoneFile(getMvpView().getName(), getMvpView().getIntroduction(), getMvpView().getUniversity(), IndividualPresenter.this);
        }


    }


    @Override
    public void setAlbumPhoto(Intent intent) {
        if (Build.VERSION.SDK_INT >= 19) {
            getMvpView().setAlbumImage(mModel.handleImageOnKitKat(intent));
        } else {
            Uri uri = intent.getData();
            getMvpView().setAlbumImage(mModel.getmImageFilePath(uri, null));

        }
    }

    @Override
    public void back() {

        getMvpView().back();
    }
}
