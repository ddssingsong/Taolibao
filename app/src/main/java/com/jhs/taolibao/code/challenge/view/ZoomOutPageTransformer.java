package com.jhs.taolibao.code.challenge.view;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by jiao on 16/7/11.
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {


    private static final float MIN_SCALE = 0.9f;
    private static final float MIN_ALPHA = 0.5f;

    private static float defaultScale = 0.9f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // <-1 前一个页面
            view.setAlpha(0);
            view.setScaleX(defaultScale);
            view.setScaleY(defaultScale);
        } else if (position <= 1) { // [-1,1]当前页面
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }

            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            view.setAlpha(MIN_ALPHA +
                    (scaleFactor - MIN_SCALE) /
                            (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        } else { // >1  后一个页面
            view.setAlpha(0);
            view.setScaleX(defaultScale);
            view.setScaleY(defaultScale);
        }
    }

}
