package com.jhs.taolibao.code.challenge.view;

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
 * 挑战部分普通花费金币提示框
 * Created by xujingbo on 2016/7/22.
 */
public class CostAlertDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView tvTotalCoin;
    private TextView tvMsg;
    private Button btn_neg;
    private Button btn_pos;

    private Display display;

    public CostAlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public CostAlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_challenge_dialog, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        tvTotalCoin = (TextView) view.findViewById(R.id.costdialog_tv_totalCoin);

        tvMsg = (TextView) view.findViewById(R.id.costdialog_tv_costMsg);

        btn_neg = (Button) view.findViewById(R.id.btn_neg);

        btn_pos = (Button) view.findViewById(R.id.btn_pos);



        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 设置总宝币数
     * @param title
     * @return
     */
    public CostAlertDialog setTotalCoin(String title) {

        if ("".equals(title)) {
            tvTotalCoin.setText("0");
        } else {
            tvTotalCoin.setText(title);
        }
        return this;
    }

    public CostAlertDialog setMsg(String msg) {

        if (null == msg) {
            tvMsg.setText("");
        } else {
            tvMsg.setText(msg);
        }
        return this;
    }


    public CostAlertDialog setPositiveButton(
                                         final View.OnClickListener listener) {
        btn_pos.setOnClickListener(new View.OnClickListener() {
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

    public CostAlertDialog setNegativeButton(
                                         final View.OnClickListener listener) {

        btn_neg.setOnClickListener(new View.OnClickListener() {
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

    public void show() {
        dialog.show();
    }
}
