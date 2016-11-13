package com.jhs.taolibao.code.market;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.market.widget.FenjiAFragment;
import com.jhs.taolibao.code.market.widget.FenjiBFragment;
import com.jhs.taolibao.code.market.widget.FenjiMFragment;
import com.jhs.taolibao.code.market.widget.FundTaoliFragment;

/**
 * Created by dds on 2016/7/8.
 *
 * @TODO
 */
public class FenjiFragment extends BaseFragment {
    private FenjiAFragment fenjiAFragment;
    private FenjiBFragment fenjiBFragment;
    private FenjiMFragment fenjiMFragment;
    private FundTaoliFragment fundTaoliFragment;
    private BaseFragment currentFragment;
    private TabLayout tabLayout;

    private EditText et_fund_search;
    private int position;

    @Override
    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fenji, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);

        et_fund_search = (EditText) rootView.findViewById(R.id.et_fund_search);
        fenjiAFragment = new FenjiAFragment();
        fenjiBFragment = new FenjiBFragment();
        fenjiMFragment = new FenjiMFragment();
        fundTaoliFragment = new FundTaoliFragment();
        tabLayout.addTab(tabLayout.newTab().setText(R.string.taolibao));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.fenjiA));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.fenjiB));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.fenjiM));


        showFragment(R.id.content_fenji, fundTaoliFragment);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();

                if (position == 0) {
                    showFragment(R.id.content_fenji, fundTaoliFragment);
                }
                if (position == 1) {
                    showFragment(R.id.content_fenji, fenjiAFragment);
                }
                if (position == 2) {
                    showFragment(R.id.content_fenji, fenjiBFragment);
                }
                if (position == 3) {
                    showFragment(R.id.content_fenji, fenjiMFragment);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onInitloadData() {
        et_fund_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    String keyword = et_fund_search.getText().toString();
                    switch (position) {
                        case 0:

                            fundTaoliFragment.searchStock(keyword);
                            break;
                        case 1:
                            fenjiAFragment.searchStock(keyword);

                            break;
                        case 2:
                            fenjiBFragment.searchStock(keyword);

                            break;
                        case 3:
                            fenjiMFragment.searchStock(keyword);
                            break;
                    }


                    return true;
                }
                return false;
            }
        });

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
