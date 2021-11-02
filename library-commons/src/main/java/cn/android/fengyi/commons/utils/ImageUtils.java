package cn.android.fengyi.commons.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ImageUtils {

    /**
     * base64转图片bitmap
     * @param base64Str
     * @return
     */
    public static Bitmap stringToBitmap(String base64Str) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(base64Str, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
