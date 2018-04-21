package rdc.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;
import java.io.File;
import java.io.IOException;

import static rdc.activity.ReleaseActivity.CUT_GALLERY_PICTURE;
import static rdc.activity.ReleaseActivity.CUT_CAMERA_PICTURE;


public class ImageUtil {
    private static final String TAG = "ImageUtil";

    private static File mFile = null;

    /**
     * 图片的压缩
     * @param filePath
     * @param callback
     */
    public static void compressImage(String filePath, FileCallback callback) {
           mFile = new File(filePath);
           Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
           Tiny.getInstance().source(new File(filePath))
                   .asFile()
                   .withOptions(options)
                   .compress(callback);
    }

    /**
     * 从相册选择照片
     */
    public static void gallery(AppCompatActivity activity, Uri imageUri) {
        File outputImage = new File(Environment.getExternalStorageDirectory().getPath(),"cccc.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent(Intent.ACTION_PICK,null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, CUT_GALLERY_PICTURE);
    }

    /**
     * 拍照
     */
    public static void camera(AppCompatActivity activity, Uri imageUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, CUT_CAMERA_PICTURE);
    }

    /**
     * 裁剪图片
     */
    public static void cropImageUri(AppCompatActivity activity, Uri uri, Uri imageUri, int outputX, int outputY, int requestCode){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("aspectX", 20);
        intent.putExtra("aspectY", 13);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, requestCode);
    }
}