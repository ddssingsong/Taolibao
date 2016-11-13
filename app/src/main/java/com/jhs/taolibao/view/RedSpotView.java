package com.jhs.taolibao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhs.taolibao.R;

/**
 * Created by dds on 2016/9/1.
 *
 * @TODO
 */
public class RedSpotView extends RelativeLayout {

    private static final int LEFT = 8;
    private static final int TOP = 4;
    private static final int RIGHT = 2;
    private static final int BOTTOM = 1;
    private static final int LEFT_TOP = 12;
    private static final int LEFT_BOTTOM = 9;
    private static final int RIGHT_TOP = 7;
    private static final int RIGHT_BOTTOM = 3;

    private TextView mSpotView;

    public RedSpotView(Context context) {
        this(context, null);
    }

    public RedSpotView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedSpotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.RedSpotView, 0, 0);
        int marginLeft, marginTop, marginRight, marginBottom, spotDiameter;
        String text;
        int gravity, textColor, spotColor;
        float textSize;
        boolean visibility;
        try {
            marginLeft = (int)array.getDimension(R.styleable.RedSpotView_spot_marginLeft, 0);
            marginTop = (int)array.getDimension(R.styleable.RedSpotView_spot_marginTop, 0);
            marginRight = (int)array.getDimension(R.styleable.RedSpotView_spot_marginRight, 0);
            marginBottom = (int)array.getDimension(R.styleable.RedSpotView_spot_marginBottom, 0);
            text = array.getString(R.styleable.RedSpotView_spotText);
            spotDiameter = (int)array.getDimension(R.styleable.RedSpotView_spotDiameter, -1);
            gravity = array.getInt(R.styleable.RedSpotView_spot_gravity, 0);
            visibility = array.getBoolean(R.styleable.RedSpotView_spot_visibility, true);
            textColor = array.getColor(R.styleable.RedSpotView_spotTextColor, Color.WHITE);
            spotColor = array.getColor(R.styleable.RedSpotView_spotColor, -1);
            textSize = array.getDimension(R.styleable.RedSpotView_spotTextSize, -1);
        } finally {
            array.recycle();
        }
        mSpotView = new TextView(context);
        if (spotDiameter == -1) {
            spotDiameter = dipsToPixels(16);
        }
        mSpotView.setText(text);
        mSpotView.setBackgroundResource(R.drawable.bg_red_circle);
        if (spotColor != -1) {
            Drawable background = mSpotView.getBackground();
            if (background instanceof ShapeDrawable) {
                ((ShapeDrawable)background).getPaint().setColor(spotColor);
            } else if (background instanceof GradientDrawable) {
                ((GradientDrawable)background).setColor(spotColor);
            } else if (background instanceof ColorDrawable) {
                ((ColorDrawable)background).setColor(spotColor);
            }
        }
        if (textSize != -1) {
            mSpotView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        } else {
            mSpotView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        }
        mSpotView.setTextColor(textColor);
        mSpotView.setGravity(Gravity.CENTER);
        mSpotView.setVisibility(visibility ? VISIBLE : GONE);
        LayoutParams params = new LayoutParams(spotDiameter, spotDiameter);
        params.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        switch (gravity) {
            case LEFT:
            case LEFT_TOP:
                params.addRule(ALIGN_PARENT_LEFT);
                params.addRule(ALIGN_PARENT_TOP);
                break;
            case LEFT_BOTTOM:
                params.addRule(ALIGN_PARENT_LEFT);
                params.addRule(ALIGN_PARENT_BOTTOM);
                break;
            case BOTTOM:
            case RIGHT_BOTTOM:
                params.addRule(ALIGN_PARENT_RIGHT);
                params.addRule(ALIGN_PARENT_BOTTOM);
                break;
            default:
                params.addRule(ALIGN_PARENT_RIGHT);
                params.addRule(ALIGN_PARENT_TOP);
                break;
        }
        mSpotView.setLayoutParams(params);
    }


    public void setSpotText(CharSequence text) {
        if (mSpotView != null) {
            mSpotView.setText(text);
        }
    }

    public void setSpotText(@StringRes int resId) {
        if (mSpotView != null) {
            mSpotView.setText(resId);
        }
    }

    public void showSpot() {
        if (mSpotView != null) {
            mSpotView.setVisibility(VISIBLE);
        }
    }

    public void hideSpot() {
        if (mSpotView != null) {
            mSpotView.setVisibility(GONE);
        }
    }

    public boolean isVisible() {
        return mSpotView != null && mSpotView.getVisibility() == VISIBLE;
    }

    private int dipsToPixels(int dips) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dips * scale + 0.5f);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mSpotView.getParent() == null) {
            addView(mSpotView);
        }
        super.onLayout(changed, l, t, r, b);
    }
}
