package com.jhs.taolibao.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jhs.taolibao.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by Administrator on 2016/3/4.
 */
public class BaseActivity extends AutoLayoutActivity implements BaseFragment.BackHandleInterface {
    // activity中初始化图片属性,这个一般写到baseActivity中
    public static DisplayImageOptions options;
    public static DisplayImageOptions options1;

    static {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.img_default_bg)
                .showImageOnFail(R.drawable.img_default_bg)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer()).build();
        options1 = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .showImageForEmptyUri(R.drawable.img_default_avatar)
                .showImageOnFail(R.drawable.img_default_avatar)
                .cacheOnDisk(false).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer()).build();


    }


    /**
     * 打开一个Activity不返回任何值
     */
    protected void openActivity(Class<?> pClass) {
        Intent _Intent = new Intent();
        _Intent.setClass(this, pClass);
        startActivity(_Intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        TCAgent.onPageStart(this, this.getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, this.getClass().getSimpleName());

    }

    // --Fragment跳转-----------------------------------------------------------------
    public BaseFragment mBackHandedFragment;
    private static BaseFragment currentFragment;
    private static BaseFragment currentFragment1;

    /**
     * 替换fragment,执行destroy方法---replace
     */
    public void replaceFragment(int contentID, BaseFragment toFragment) {
        replaceFragment(contentID, toFragment, false);
    }

    public void replaceFragment(int contentID, BaseFragment toFragment, boolean isAdd2Back) {
        String tag = toFragment.getClass().getSimpleName();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(contentID, toFragment);
        if (isAdd2Back) {
            ft.addToBackStack(tag);
        }
        ft.commit();
        currentFragment1 = toFragment;
    }

    /**
     * 显示fragment，隐藏其他fragment----hide-show
     */
    public void showFragment(int contentID, BaseFragment toFragment) {
        showFragment(contentID, toFragment, false);
    }

    public void showFragment(int contentID, BaseFragment toFragment, boolean isAdd2Back) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (currentFragment != null) {
            currentFragment.onPause();
            ft.hide(currentFragment);
        }
        String tag = toFragment.getClass().getSimpleName();
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fragment == null) {
            ft.add(contentID, toFragment, tag);
        } else {
            toFragment.onResume();
            ft.show(toFragment);
        }
        if (isAdd2Back) {
            ft.addToBackStack(tag);
        }
        ft.commit();
        currentFragment = toFragment;
    }


    @Override
    public void setSelectedFragment(BaseFragment selectFragment) {
        this.mBackHandedFragment = selectFragment;

    }

    /**
     * 重写返回键
     */
    @Override
    public void onBackPressed() {
        if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }

    }
}
