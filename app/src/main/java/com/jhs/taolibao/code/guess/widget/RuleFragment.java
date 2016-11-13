package com.jhs.taolibao.code.guess.widget;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.utils.OkHttpUtil;
import com.squareup.okhttp.Request;

/**
 * Created by dds on 2016/7/15.
 *
 * @TODO
 */
public class RuleFragment extends DialogFragment {
    private TextView submit;
    private TextView tv_title;
    private TextView tv_content;
    private WebView web_tip;


    private String title;
    private String content;
    private int drawableid;
    private Drawable drawable;

    private int type;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_rule, container);
        initView(view);
        initData();
        return view;
    }


    private void initView(View view) {
        submit = (TextView) view.findViewById(R.id.tv_checkin_submit);
        tv_title = (TextView) view.findViewById(R.id.tv_checkin_day);
        tv_content = (TextView) view.findViewById(R.id.tv_checkin_tip);
        web_tip = (WebView) view.findViewById(R.id.web_tip);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RuleFragment.this.dismiss();
            }
        });
        drawable = getResources().getDrawable(drawableid);
        if (title != null) {
            tv_title.setText(title);
            //tv_title.setCompoundDrawables(drawable, null, null, null);
        }
        if (content != null) {
            tv_content.setText(content);
        }

        if (type != 0) {
            if (type == 1) {
                //挑战规则
                web_tip.setVisibility(View.VISIBLE);
                OkHttpUtil.getAsyn(WebBiz.GetConfigByKey + "?key=challange_rule", new OkHttpUtil.ResultCallback<String>() {

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {


                        web_tip.loadDataWithBaseURL("", response, null, "UTF-8", WebBiz.GetConfigByKey + "?key=challange_rule");

                    }
                });

            }

            if (type == 2) {
                //猜涨跌规则
                web_tip.setVisibility(View.VISIBLE);
                OkHttpUtil.getAsyn(WebBiz.GetConfigByKey + "?key=guess_rule", new OkHttpUtil.ResultCallback<String>() {

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {


                        web_tip.loadDataWithBaseURL("", response, null, "UTF-8", WebBiz.GetConfigByKey + "?key=guess_rule");

                    }
                });


            }
        }

    }

    private void initData() {


    }

    public void setTitle(String title, int drawableid) {
        this.title = title;
        this.drawableid = drawableid;
    }


    public void setType(int type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
