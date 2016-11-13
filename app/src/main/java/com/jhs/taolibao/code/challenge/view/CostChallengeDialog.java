package com.jhs.taolibao.code.challenge.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhs.taolibao.R;

/**
 * 挑战部分参加挑战花费金币提示框
 * Created by xujingbo on 2016/8/2.
 */
public class CostChallengeDialog {

    private static final String TAG = "CostChallengeDialog";

    private static final int MIN_COST = 1000;
    private static final int MAX_COST = 20000;
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView tvTotalCoin;
    private TextView tvMsg;
    private Button btn_neg;
    private Button btn_pos;
    private ImageButton btnReduce;
    private ImageButton btnPlus;
    private TextView tvCost;
    //宝币数量不够扣除的提示
    private TextView tvAlert;
    private Display display;
    private int cost = 1000;

    private int totalPoint = 0;
    public CostChallengeDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public CostChallengeDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_challenge_cost_dialog, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        tvTotalCoin = (TextView) view.findViewById(R.id.costdialog_tv_totalCoin);

        tvMsg = (TextView) view.findViewById(R.id.costdialog_tv_costMsg);

        btn_neg = (Button) view.findViewById(R.id.btn_neg);

        btn_pos = (Button) view.findViewById(R.id.btn_pos);

        btnReduce = (ImageButton) view.findViewById(R.id.btn_reduce);
        btnPlus = (ImageButton) view.findViewById(R.id.btn_plus);
        tvCost = (TextView) view.findViewById(R.id.et_cost);
        tvAlert = (TextView) view.findViewById(R.id.tv_alert);
        tvAlert.setVisibility(View.GONE);
        tvCost.setText(String.valueOf(cost));

        btnReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cost <= MIN_COST){
                    //do nothing
                }else {
                    cost = cost - 1000;
                    tvCost.setText(String.valueOf(cost));
                }
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cost >= MAX_COST){
                    //do nothing
                }else {
                    cost = cost + 1000;
                    tvCost.setText(String.valueOf(cost));
                }
            }
        });

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
     * @param totalCoin
     * @return
     */
    public CostChallengeDialog setTotalCoin(int totalCoin) {

        if (totalCoin == 0) {
            tvTotalCoin.setText("0");
        } else {
            tvTotalCoin.setText(String.valueOf(totalCoin));
        }
        totalPoint = totalCoin;
        return this;
    }

    public CostChallengeDialog setMsg(String msg) {

        if (null == msg) {
            tvMsg.setText("");
        } else {
            tvMsg.setText(msg);
        }
        return this;
    }


    public CostChallengeDialog setPositiveButton(
            final View.OnClickListener listener) {
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (tvCost.getText().toString().length() > 0) {
                        if (totalPoint < cost) {
                            tvAlert.setVisibility(View.VISIBLE);
                        } else {
                            tvAlert.setVisibility(View.GONE);
                            listener.onClick(v);
                        }
                    }
                }
                dialog.dismiss();
            }
        });
        return this;
    }
    public int getCost(){
        return cost;
    }
    public CostChallengeDialog setNegativeButton(
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