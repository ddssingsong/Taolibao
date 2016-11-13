package com.jhs.taolibao.code.simtrade.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.challenge.view.CostAlertDialog;
import com.jhs.taolibao.code.simtrade.app.AppManager;
import com.jhs.taolibao.code.simtrade.utils.DensityUtil;
import com.jhs.taolibao.utils.ToastUtil;
import com.jhs.taolibao.view.WaitDialog;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.autolayout.AutoLayoutActivity;


/**
 * Activity的基类
 *
 * @author jiao 2016.7.7
 */
public abstract class BaseActivity2 extends AutoLayoutActivity implements
        OnClickListener {

    private boolean titleLoaded = false; // 标题是否加载成功
    private View titleLeftView;// 左控件
    private View titleView;// 整体控件
    private TextView tv_title;// 标题
    private TextView tvTitleRight;// 右标题
    private ImageView ivTitleRight;// 右图片
    private WaitDialog progressDialog;//加载中进度条

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(this, this.getClass().getSimpleName());
        AppManager.getAppManager().addActivity(this);
        setFullScreen(false);
        //4.4版本以上
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window win = getWindow();
//            WindowManager.LayoutParams winParams = win.getAttributes();
//            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//            winParams.flags |= bits;
//            win.setAttributes(winParams);
//        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        DensityUtil.getInstance().setDpi(dm.density);
        if (getLayout() != 0) {
            setContentView(getLayout());
        }
        loadTitle();

        OnActCreate(savedInstanceState);

    }

    /************************* 重写方法区 *****************************/

    /**
     * 返回本界面的布局文件
     *
     * @return
     */
    public abstract int getLayout();

    /**
     * 子类OnCreate方法
     *
     * @param savedInstanceState
     */
    public abstract void OnActCreate(Bundle savedInstanceState);

    /**
     * 控件的点击事件
     *
     * @param v
     */
    public abstract void OnViewClick(View v);

    /*********************** 父类方法区 ******************************/

    /**
     * 加载标题
     */
    private void loadTitle() {
        titleLeftView = findViewById(R.id.ll_title_left_view);
        titleView = findViewById(R.id.titleview);
        tv_title = (TextView) findViewById(R.id.title_name_text);
        tvTitleRight = (TextView) findViewById(R.id.title_right_text);
        ivTitleRight = (ImageView) findViewById(R.id.title_right_img);
        if (titleView != null) {
            titleLeftView.setOnClickListener(this);
            titleLoaded = true;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                ViewGroup.LayoutParams params = titleView.getLayoutParams();
//                params.height = params.height + DensityUtil.getStatusBarHeight();
//                titleView.setLayoutParams(params);
//            }
        }
    }

    /**
     * 隐藏输入法
     */
    public void hideInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public final void onClick(View v) {
        // 过滤要处理的控件
        switch (v.getId()) {
            case R.id.ll_title_left_view:
                // 返回按钮
                finish();
                break;

            default:
                break;
        }
        OnViewClick(v);
    }

    /********************** 公共方法区 *******************************/

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (titleLoaded) {
            tv_title.setText(title);
        }
    }

    public void hideTitleText() {
        if (titleLoaded) {
            tv_title.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题背景
     *
     * @param colorId
     */
    public void setTitleBackground(int colorId) {
        if (titleLoaded) {
            titleView.setBackgroundColor(getResources().getColor(colorId));
        }
    }

    /**
     * 隐藏标题
     */
    public void hideTitle() {
        if (titleLoaded) {
            titleView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右图片
     *
     * @param resId
     */
    public void setRightImage(int resId) {
        if (titleLoaded) {
            ivTitleRight.setBackgroundResource(resId);
        }
    }

    /**
     * 隐藏右文字
     */
    public void hideRightText() {
        if (titleLoaded) {
            tvTitleRight.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右图片监听
     *
     * @param listener
     */
    public void setRightImageListener(OnClickListener listener) {
        if (titleLoaded) {
            ivTitleRight.setOnClickListener(listener);
        }
    }

    /**
     * 显示右文本
     */
    public void showRightText() {
        if (titleLoaded) {
            tvTitleRight.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置右文本
     *
     * @param text
     */
    public void setRightText(String text) {
        if (titleLoaded) {
            showRightText();
            tvTitleRight.setText(text);
        }
    }

    /**
     * 隐藏左控件
     */
    public void hideLeft() {
        if (titleLoaded) {
            titleLeftView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置左控件的点击事件
     */
    public void setLeftClickListener(OnClickListener listener) {
        if (titleLoaded) {
            titleLeftView.setOnClickListener(listener);
        }
    }

    public void hideLine() {
//        findViewById(R.id.title_line).setVisibility(View.GONE);
    }

    /**
     * 给控件设置监听
     *
     * @param resId
     * @param listener
     */
    public View setViewClick(int resId, OnClickListener listener) {
        View view = findViewById(resId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
        return view;
    }

    /**
     * 给控件设置监听
     *
     * @param resId
     * @param
     */
    public View setViewClick(int resId) {
        return setViewClick(resId, this);
    }

    /**
     * 跳转一个界面不传递数据
     *
     * @param clazz
     */
    public void startActivity(Class<? extends BaseActivity2> clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
    }

    /**
     * 是否全屏和显示标题，true为全屏和无标题，false为无标题，请在setContentView()方法前调用
     *
     * @param fullScreen
     */
    public void setFullScreen(boolean fullScreen) {
        if (fullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, this.getClass().getSimpleName());
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * Toast
     */
    public void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    /**
     * 显示正在加载的进度条
     */
    public void showProgressDialog() {
        showProgressDialog("拼命加载中...");
    }

    public void showProgressDialog(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new WaitDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        try{
            progressDialog.show();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 取消对话框显示
     */
    public void disMissDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 扣费确认框
     * @param msg
     * @param listener
     */
    public void showCostDialog(String msg, final OnClickListener listener){
        new CostAlertDialog(this).builder()
                .setTotalCoin("2345")
                .setMsg(msg)
                .setPositiveButton(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //challengeOrWatch(type);
                        listener.onClick(v);
                    }
                }).show();
    }
}
