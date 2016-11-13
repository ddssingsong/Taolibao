package com.jhs.taolibao.code;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.code.simtrade.SimtradeCenter;

public class LogoActivity extends BaseActivity {
    private ImageView iv_logo;
    private TextView tv_slogan;

    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //无title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
        setContentView(R.layout.activity_logo);
        initView();
        starAnim();
        if (UserInfoSingleton.getUserInfo() != null) {
           // Log.d("msg","开始获取令牌");
            SimtradeCenter.getInstance().init(UserInfoSingleton.getUserInfo().getHsAccount(), UserInfoSingleton.getUserInfo().getHsPwd());
           // Log.d("msg","获取令牌结束");
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        }, 2500);

    }

    private void initView() {
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        tv_slogan = (TextView) findViewById(R.id.tv_slogan);


    }

    private void starAnim() {
        iv_logo.setImageResource(R.drawable.anim_logo);
        animationDrawable = (AnimationDrawable) iv_logo.getDrawable();
        animationDrawable.start();

    }


}
