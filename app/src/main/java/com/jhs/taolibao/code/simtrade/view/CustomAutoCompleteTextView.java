package com.jhs.taolibao.code.simtrade.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhs.taolibao.code.simtrade.adapter.StockWizardAdapter;

/**
 * 自定义AutoCompleteTextView
 * Created by xujingbo on 2016/7/13.
 */
public class CustomAutoCompleteTextView extends RelativeLayout{
    private Context context;
    private AutoCompleteTextView tv;
    private TextView textView;
    public CustomAutoCompleteTextView(Context context) {
        this(context, null);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }

    private void initViews() {
        LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        tv = new AutoCompleteTextView(context);
        tv.setLayoutParams(params);
        tv.setPadding(30, 0, 80, 0);
        tv.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
        tv.setSingleLine(true);

        LayoutParams p=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        p.rightMargin=10;
        textView = new TextView(context);
        textView.setText("");
        textView.setPadding(0, 0, 10, 0);
        textView.setLayoutParams(p);
        textView.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);

        this.addView(tv);
        this.addView(textView);
    }

    public void setAdapter(StockWizardAdapter adapter){
        tv.setAdapter(adapter);
    }

    public void setThreshold(int threshold){
        tv.setThreshold(threshold);
    }

    public AutoCompleteTextView getAutoCompleteTextView(){
        return tv;
    }
    public TextView getTextView(){
        return textView;
    }
}
