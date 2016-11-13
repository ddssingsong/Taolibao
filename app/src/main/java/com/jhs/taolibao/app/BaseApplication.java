package com.jhs.taolibao.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.socialize.PlatformConfig;
import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * Created by dds on 2016/6/7.
 *
 * @TODO
 */


public class BaseApplication extends Application {
    public static Context context;
    public static SharedPreferences spUserInfo;
    public static SharedPreferences.Editor spUserInfoEditor;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;


        //talkinngdata
        TCAgent.init(this.getApplicationContext(), "69F84E7E7572860CDBA53CD76D355B3F", "default");
        TCAgent.setReportUncaughtExceptions(true);
        //imageloader
        initImageLoader(this);

        PlatformConfig.setWeixin("wx9c1a6d64f25bbe2a", "1884d166e326e6b788156fe7669c1f75");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("3382083906", "e58c2ffde922d26ea3279074eb00a49b");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("1105512013", "xand8Je0M7x73QMt");
        // QQ和Qzone appid appkey

        spUserInfo = getSharedPreferences("UserInfo", MODE_PRIVATE);
        spUserInfoEditor = spUserInfo.edit();
        UserInfoSingleton.setSPUserInfo();
        UserInfoSingleton.setSPUserInfoEditor();
        AutoLayoutConifg.getInstance().useDeviceSize();
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPoolSize(4);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(10 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        // config.writeDebugLogs();
        L.disableLogging();
        ImageLoader.getInstance().init(config.build());
    }

    public static Context getApplication() {
        return context;
    }

    /**
     * @return版本信息号
     */
    public static final String getVersion() {
        PackageManager packageManager = BaseApplication.context.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(BaseApplication.context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "1.0.0";
        }
    }

    /**
     * @return版本号
     */
    public static final int getVersionCode() {
        PackageManager packageManager = BaseApplication.context.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(BaseApplication.context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

}
