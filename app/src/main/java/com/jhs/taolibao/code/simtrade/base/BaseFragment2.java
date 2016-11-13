package com.jhs.taolibao.code.simtrade.base;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.challenge.ChallengeCenter;
import com.jhs.taolibao.code.challenge.utils.ChallengeTask;
import com.jhs.taolibao.code.challenge.view.CostAlertDialog;
import com.jhs.taolibao.code.simtrade.entity.UserInfo;
import com.jhs.taolibao.view.WaitDialog;

import java.io.IOException;


/**
 * fragment基类
 *
 * @author jiao 2016.7.7
 */
public abstract class BaseFragment2 extends Fragment implements OnClickListener {
    //分享

    private boolean titleLoaded = false; // 标题是否加载成功
    private View titleLeftView;// 左控件
    private View titleView;// 整体控件
    private TextView tv_title;// 标题
    private ImageView ivTitle;//图片标题
    private TextView tvTitleRight;// 右标题
    private ImageView ivTitleRight;// 右图片
    private ImageView ivTitleRight1;// 右图片
    private WaitDialog progressDialog;//加载中进度条

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null != savedInstanceState) {
            return;
        }
        loadTitle();
        try {
            OnActCreate(savedInstanceState);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hideLeft();
    }

    /************************* 重写方法区 *****************************/

    /**
     * 子类OnCreate方法
     *
     * @param savedInstanceState
     */
    public abstract void OnActCreate(Bundle savedInstanceState) throws IOException;

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
        titleLeftView = getView().findViewById(R.id.ll_title_left_view);
        titleView = getView().findViewById(R.id.titleview);
        tv_title = (TextView) getView().findViewById(R.id.title_name_text);
        tvTitleRight = (TextView) getView().findViewById(R.id.title_right_text);
        ivTitleRight = (ImageView) getView().findViewById(R.id.title_right_img);
        ivTitle = (ImageView) getView().findViewById(R.id.title_name_img);

        if (titleView != null) {
            titleLeftView.setOnClickListener(this);
            titleLoaded = true;
        }
    }

    /**
     * 隐藏输入法
     */
    public void hideInputMethod() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getActivity().getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public final void onClick(View v) {
        // 过滤要处理的控件
        switch (v.getId()) {
            case R.id.ll_title_left_view:
                // 返回按钮
                getActivity().finish();
                break;

            default:
                break;
        }
        OnViewClick(v);
    }

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

    /**
     * 隐藏文字标题
     */
    public void hideTextTitle() {
        if (titleLoaded) {
            tv_title.setVisibility(View.GONE);
        }
    }

    /**
     * 设置图片标题，同时隐藏文字标题
     *
     * @param resId 图片资源
     */
    public void setImageTitle(int resId) {
        if (titleLoaded) {
            hideTextTitle();
            ivTitle.setVisibility(View.VISIBLE);
            ivTitle.setImageResource(resId);
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

            ivTitleRight.setImageResource(resId);
            ivTitleRight.setVisibility(View.VISIBLE);

        }
    }

    /**
     * 隐藏右文字
     */
    public void hideRight() {
        if (titleLoaded) {
            tvTitleRight.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右文字监听
     *
     * @param listener
     */
    public void setRightViewListener(OnClickListener listener) {
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

    public void setRightTextColor() {

        tvTitleRight.setTextColor(Color.parseColor("#ff7400"));
    }

    public void setRightImageListener(OnClickListener listener) {
        if (titleLoaded) {
            RelativeLayout rlRightImg = (RelativeLayout) getView().findViewById(R.id.title_rl_rightimg);
            rlRightImg.setOnClickListener(listener);
        }
    }

    /**
     * 设置另一张右图片（只有第一张右图片存在的情况下，才会存在）
     *
     * @param resId
     */
    public void setRightOtherImage(int resId) {
        if (titleLoaded) {
            ivTitleRight1.setBackgroundResource(resId);
        }
    }


    public String getRightTextString() {
        return tvTitleRight.getText().toString();
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
     * 显示左控件
     */
    public void showLeft() {
        if (titleLoaded) {
            titleLeftView.setVisibility(View.VISIBLE);
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

    /**
     * 给控件设置监听
     *
     * @param resId
     * @param listener
     */
    public View setViewClick(int resId, OnClickListener listener) {
        View view = getView().findViewById(resId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
        return view;
    }

    /**
     * 给控件设置监听
     *
     * @param resId
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
        intent.setClass(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * 是否全屏和显示标题，true为全屏和无标题，false为无标题，请在setContentView()方法前调用
     *
     * @param fullScreen
     */
    public void setFullScreen(boolean fullScreen) {
        if (fullScreen) {
            getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
            getActivity().getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

    }

    /**
     * Toast
     */
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
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
        if (getActivity() == null) {
            return;
        }
        progressDialog = new WaitDialog(getActivity());
        progressDialog.setMessage(msg);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        try {
            progressDialog.show();
        } catch (Exception e) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 将EditText光标置于末尾
     *
     * @param editText
     */
    public void setSelectionEnd(EditText editText) {
        CharSequence charSequence = editText.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    /**
     * 扣费确认框
     *
     * @param msg
     * @param listener
     */
    public void showCostDialog(final String msg, final int costPoint, final OnClickListener listener) {
        ChallengeCenter.getInstance().getTotalPoint(new ChallengeTask.OnChallengeListener() {
            @Override
            public void onSuccess(Object response) {
                UserInfo userInfo = (UserInfo) response;
                final int totalPoint = userInfo.getUser().getUserTotalPoint();
                new CostAlertDialog(getContext()).builder()
                        .setTotalCoin(String.valueOf(totalPoint))
                        .setMsg(msg)
                        .setPositiveButton(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //challengeOrWatch(type);
                                if (totalPoint < costPoint) {
                                    showToast("宝币总数不够，");
                                } else {
                                    listener.onClick(v);
                                }
                            }
                        }).show();
            }

            @Override
            public void onFailure(String msg, Exception e) {

            }
        });

    }

}
