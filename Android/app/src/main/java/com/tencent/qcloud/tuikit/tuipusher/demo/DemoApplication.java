package com.tencent.qcloud.tuikit.tuipusher.demo;

import android.os.Build;
import android.os.StrictMode;

import androidx.multidex.MultiDexApplication;

import com.tencent.rtmp.TXLiveBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import com.tencent.qcloud.tuikit.tuipusher.demo.debug.GenerateTestUserSig;

import static com.tencent.qcloud.tuikit.tuipusher.demo.debug.GenerateTestUserSig.LICENSEURL;
import static com.tencent.qcloud.tuikit.tuipusher.demo.debug.GenerateTestUserSig.LICENSEURLKEY;

/**
 * 调试注意事项：
 * 第一步：配置正确的应用信息 替换该文件中{@link GenerateTestUserSig}的PLACEHOLDER字符串
 * 第二部：修改当 app模块 的 build.gradle 文件中的 applicationId 为 License 对应的包名
 */
public class DemoApplication extends MultiDexApplication {
    private static String TAG = "DemoApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        TXLiveBase.getInstance().setLicence(this, LICENSEURL, LICENSEURLKEY);
        closeAndroidPDialog();
    }

    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}