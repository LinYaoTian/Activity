package rdc.util;

import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;


public class CompressImageUtil {
    private static final String TAG = "CompressImageUtil";

    private static File mFile = null;

    public static void compressImage(String filePath, FileCallback callback) {
          mFile = new File(filePath);
           Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
           Tiny.getInstance().source(new File(filePath))
                   .asFile()
                   .withOptions(options)
                   .compress(callback);


    }


}