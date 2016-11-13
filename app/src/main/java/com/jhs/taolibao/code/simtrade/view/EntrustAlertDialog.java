package com.jhs.taolibao.code.simtrade.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhs.taolibao.R;

/**
 * 委托确认框
 * Created by xujingbo on 2016/7/14.
 */
public class EntrustAlertDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView tvTitle;
    private Button btnNeg;
    private Button btnPos;
    private Display display;

    private LinearLayout llContents[];
    private TextView tvMsgTitles[];
    private TextView tvMsgContents[];

    public EntrustAlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public EntrustAlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_alert_dialog, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        tvTitle = (TextView) view.findViewById(R.id.alerdialog_tv_title);
        btnNeg = (Button) view.findViewById(R.id.btn_neg);
        btnPos = (Button) view.findViewById(R.id.btn_pos);

        llContents = new LinearLayout[5];
        tvMsgContents = new TextView[5];
        tvMsgTitles = new TextView[5];

        llContents[0] = (LinearLayout)view.findViewById(R.id.alerdialog_ll_content1);
        llContents[1] = (LinearLayout)view.findViewById(R.id.alerdialog_ll_content2);
        llContents[2] = (LinearLayout)view.findViewById(R.id.alerdialog_ll_content3);
        llContents[3] = (LinearLayout)view.findViewById(R.id.alerdialog_ll_content4);
        llContents[4] = (LinearLayout)view.findViewById(R.id.alerdialog_ll_content5);

        tvMsgTitles[0] = (TextView)view.findViewById(R.id.alerdialog_tv_msg_title1);
        tvMsgTitles[1] = (TextView)view.findViewById(R.id.alerdialog_tv_msg_title2);
        tvMsgTitles[2] = (TextView)view.findViewById(R.id.alerdialog_tv_msg_title3);
        tvMsgTitles[3] = (TextView)view.findViewById(R.id.alerdialog_tv_msg_title4);
        tvMsgTitles[4] = (TextView)view.findViewById(R.id.alerdialog_tv_msg_title5);

        tvMsgContents[0] = (TextView) view.findViewById(R.id.alerdialog_tv_msg_content1);
        tvMsgContents[1] = (TextView) view.findViewById(R.id.alerdialog_tv_msg_content2);
        tvMsgContents[2] = (TextView) view.findViewById(R.id.alerdialog_tv_msg_content3);
        tvMsgContents[3] = (TextView) view.findViewById(R.id.alerdialog_tv_msg_content4);
        tvMsgContents[4] = (TextView) view.findViewById(R.id.alerdialog_tv_msg_content5);


        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(
                new FrameLayout.LayoutParams(
                        (int) (display.getWidth() * 0.65), LinearLayout.LayoutParams.WRAP_CONTENT));

        return this;
    }
    public EntrustAlertDialog setTitle(String title) {

        if ("".equals(title)) {
            tvTitle.setText("标题");
        } else {
            tvTitle.setText(title);
        }
        return this;
    }
    public EntrustAlertDialog setMsgContent1(String title, String content){
        llContents[0].setVisibility(View.VISIBLE);
        tvMsgTitles[0].setText(title);
        tvMsgContents[0].setText(content);
        return this;
    }
    public EntrustAlertDialog setMsgContent2(String title, String content){
        llContents[1].setVisibility(View.VISIBLE);
        tvMsgTitles[1].setText(title);
        tvMsgContents[1].setText(content);
        return this;
    }
    public EntrustAlertDialog setMsgContent3(String title, String content){
        llContents[2].setVisibility(View.VISIBLE);
        tvMsgTitles[2].setText(title);
        tvMsgContents[2].setText(content);
        return this;
    }
    public EntrustAlertDialog setMsgContent4(String title, String content){
        llContents[3].setVisibility(View.VISIBLE);
        tvMsgTitles[3].setText(title);
        tvMsgContents[3].setText(content);
        return this;
    }
    public EntrustAlertDialog setMsgContent5(String title, String content){
        llContents[4].setVisibility(View.VISIBLE);
        tvMsgTitles[4].setText(title);
        tvMsgContents[4].setText(content);
        return this;
    }

    public EntrustAlertDialog hideContent(int index){
        if (index <= 0){
            index = 1;
        }
        llContents[index - 1].setVisibility(View.GONE);
        return this;
    }


    public EntrustAlertDialog setPositiveButton(String text,
                                                final View.OnClickListener listener) {

        if ("".equals(text)) {
            btnPos.setText("确定");
        } else {
            btnPos.setText(text);
        }
        btnPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    public EntrustAlertDialog setNegativeButton(String text,
                                                final View.OnClickListener listener) {

        if ("".equals(text)) {
            btnNeg.setText("取消");
        } else {
            btnNeg.setText(text);
        }
        btnNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null ) {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    public void show() {
        dialog.show();
    }
}
