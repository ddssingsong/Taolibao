package com.jhs.taolibao.code.simtrade.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * @author jiao on 2016/7/15 16:57
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:动画工具类
 */
public class AnimationUtils {

    /**
     * "挑战"的上下滚动的广告条
     */
    public static void showAd(final Context context, final View ad, final TextView adName, final
    String adStr) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(ad, "translationY", 0,
                -DensityUtil.dip2px(context, 15));
        ObjectAnimator alpha = ObjectAnimator.ofFloat(ad, "alpha", 1, 0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(translationY, alpha);
        set.setDuration(500);
        set.start();
        translationY.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                adName.setText(adStr);
                ObjectAnimator translationY1 = ObjectAnimator.ofFloat(ad, "translationY",
                        (DensityUtil.dip2px(context, 15)), 0);
                ObjectAnimator alpha1 = ObjectAnimator.ofFloat(ad, "alpha", 0, 1);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(translationY1, alpha1);
                set.setDuration(500);
                set.start();
            }
        });
    }
}
