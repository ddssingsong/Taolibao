package com.jhs.taolibao.code.my.widget;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jhs.taolibao.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoActivity extends FragmentActivity {


    PhotoView ivPhoto;
    PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ivPhoto= (PhotoView) findViewById(R.id.iv_photo);
        String url = getIntent().getStringExtra("url");
        ImageLoader.getInstance().displayImage(url, ivPhoto);
        mAttacher = new PhotoViewAttacher(ivPhoto);
    }
}
