package cn.android.fengyi.commons.utils;

import android.content.Context;

/**
 * Create By:FengYi.Lee
 * Create Time:2022/1/17
 * DESC:
 */
public class AppUtils {

    public static int getAppVersionCode(Context context) {
        try {
            String pkName = context.getPackageName();
//            String versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
            int versionCode = context.getPackageManager().getPackageInfo(pkName, 0).versionCode;
            return versionCode;
        } catch (Exception e) {
        }
        return -1;
    }

}
