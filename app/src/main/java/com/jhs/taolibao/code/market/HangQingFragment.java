package com.jhs.taolibao.code.market;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.view.RainbowTextView;

/**
 * Created by dds on 2016/7/11.
 *
 * @TODO
 */
public class HangQingFragment extends BaseFragment implements View.OnClickListener {
    private BaseFragment currentFragment;
    private MarketFragment marketFragment;
    private FenjiFragment fenjiFragment;

    private TextView tv_hushen;
    private RainbowTextView tv_fenji;

    @Override
    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hangqing, container, false);

        return view;
    }

    @Override
    protected void initView(View rootView) {
        tv_hushen = (TextView) rootView.findViewById(R.id.tv_hushen);
        tv_fenji = (RainbowTextView) rootView.findViewById(R.id.tv_fenji);
        marketFragment = new MarketFragment();
        fenjiFragment = new FenjiFragment();

        tv_hushen.setClickable(true);
        tv_fenji.setClickable(true);
        tv_hushen.setOnClickListener(this);
        tv_fenji.setOnClickListener(this);


    }

    @Override
    public void onInitloadData() {
        showFragment(R.id.content_hangqing, marketFragment);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_hushen:
                tv_hushen.setTextColor(getResources().getColor(R.color.Red));
                //  tv_fenji.setTextColor(getResources().getColor(R.color.Gunpowder));
                showFragment(R.id.content_hangqing, marketFragment);
                tv_hushen.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.rectangle_line_red);
                tv_fenji.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.rectangle_line_white);
                break;
            case R.id.tv_fenji:
                // tv_fenji.setTextColor(getResources().getColor(R.color.Red));
                tv_hushen.setTextColor(getResources().getColor(R.color.Gunpowder));
                showFragment(R.id.content_hangqing, fenjiFragment);
                tv_fenji.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.rectangle_line_red);
                tv_hushen.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, R.drawable.rectangle_line_white);
                break;
        }
    }


    public void showFragment(int contentID, BaseFragment toFragment) {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (currentFragment != null) {
            currentFragment.onPause();
            ft.hide(currentFragment);
        }
        String tag = toFragment.getClass().getSimpleName();
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fragment == null) {
            ft.add(contentID, toFragment, tag);
        } else {
            toFragment.onResume();
            ft.show(toFragment);
        }
        ft.commit();
        currentFragment = toFragment;
    }
}
