package rdc.model;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import rdc.app.App;
import rdc.bean.User;
import rdc.contract.IIndividualContract;

/**
 * Created by asus on 18-4-15.
 */

public class IndividualModel implements IIndividualContract.Model {

    private Uri mImageUrl;
    private String mImageFilePath;

    @Override
    public void updateUserWithAllFile(String imageUrl, String Photo, final String name, final String introduction, final String university, final IIndividualContract.Presenter presenter) {
        final User user = BmobUser.getCurrentUser(User.class);

        final User newUser = new User();
        final BmobFile imageBmobFile = new BmobFile(new File(imageUrl));
        final BmobFile photoBmobFile = new BmobFile(new File(Photo));
        imageBmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                    photoBmobFile.upload(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                newUser.setUserImg(imageBmobFile);
                                newUser.setUserPhoto(photoBmobFile);
                                newUser.setNickname(name);
                                newUser.setIntroduction(introduction);
                                newUser.setUniversity(university);
                                newUser.update(user.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                        if (e == null) {
                                            user.setUserPhoto(photoBmobFile);
                                            user.setUserImg(imageBmobFile);
                                            user.setUniversity(university);
                                            user.setNickname(name);
                                            user.setIntroduction(introduction);
                                            presenter.back();

                                        }else {
                                            presenter.onError();
                                        }
                                    }
                                });
                            }else {
                                presenter.onError();
                            }
                        }
                    });
                }else {
                    presenter.onError();
                }
            }

        });
    }

    @Override
    public void updateUserWithPhoto(String Photo, final String name, final String introduction, final String university, final IIndividualContract.Presenter presenter) {
        final User user = BmobUser.getCurrentUser(User.class);
        final User newUser = new User();
        final BmobFile photoBmobFile = new BmobFile(new File(Photo));
        photoBmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    newUser.setUserPhoto(photoBmobFile);
                    newUser.setNickname(name);
                    newUser.setIntroduction(introduction);
                    newUser.setUniversity(university);
                    newUser.update(user.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                            if (e == null) {
                                user.setUserPhoto(photoBmobFile);
                                user.setUniversity(university);
                                user.setNickname(name);
                                user.setIntroduction(introduction);
                                presenter.back();

                            }else {
                                presenter.onError();
                            }
                        }
                    });
                }else {
                    presenter.onError();
                }

            }
        });


    }

    @Override
    public void updateUserWithImage(String imageUrl, final String name, final String introduction, final String university, final IIndividualContract.Presenter presenter) {
        final User user = BmobUser.getCurrentUser(User.class);
        final User newUser = new User();
        final BmobFile imageBmobFile = new BmobFile(new File(imageUrl));
        imageBmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                    newUser.setUserImg(imageBmobFile);
                    newUser.setNickname(name);
                    newUser.setIntroduction(introduction);
                    newUser.setUniversity(university);
                    newUser.update(user.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                            if (e == null) {
                                user.setUserImg(imageBmobFile);
                                user.setUniversity(university);
                                user.setNickname(name);
                                user.setIntroduction(introduction);
                                presenter.back();

                            }else {
                                presenter.onError();
                            }
                        }
                    });
                }else {
                    presenter.onError();
                }
            }
        });

    }

    @Override
    public void updateUserWithNoneFile(final String name, final String introduction, final String university, final IIndividualContract.Presenter presenter) {
        final User user = BmobUser.getCurrentUser(User.class);
        final User newUser = new User();

        newUser.setNickname(name);
        newUser.setIntroduction(introduction);
        newUser.setUniversity(university);
        newUser.update(user.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {

                if (e == null) {
                    user.setUniversity(university);
                    user.setNickname(name);
                    user.setIntroduction(introduction);
                    presenter.back();

                }else {
                    presenter.onError();
                }
            }
        });


    }


    public Uri getmImageUrl() {
        File outputImage = new File(App.getsCacheDir(), "output_image.JPEG");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();

        }

        if (Build.VERSION.SDK_INT >= 24) {

            mImageUrl = FileProvider.getUriForFile(App.getmContext(), "com.rdc.activity.fileprovider", outputImage);
        } else {

            mImageUrl = Uri.fromFile(outputImage);
        }

        return mImageUrl;
    }

    @TargetApi(19)
    public String handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(App.getmContext(), uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                mImageFilePath = getmImageFilePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                mImageFilePath = getmImageFilePath(contentUri, null);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            mImageFilePath = getmImageFilePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            mImageFilePath = uri.getPath();
        }

        return mImageFilePath;


    }

    public String getmImageFilePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = App.getsContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


}
