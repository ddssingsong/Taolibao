package com.jhs.taolibao.code.news.widget;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhs.taolibao.R;
import com.jhs.taolibao.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dds on 2016/6/12.
 *
 * @TODO
 */
public class NewsFragment extends BaseFragment {
    public static final int NEWS_TYPE_BAGUA = 2;
    public static final int NEWS_TYPE_NEICAN = 3;
   // public static final int NEWS_TYPE_ZHIBO = 5;
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private NewsFirstFragment newsFirstFragment;
    private NewsLiveFragment newsLiveFragment;

    @Override
    protected boolean onBackPressed() {
        return false;
    }
    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        return view;
    }
    @Override
    protected void initView(View view) {
        mTablayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
    }

    @Override
    public void onInitloadData() {
        newsFirstFragment = new NewsFirstFragment();
        newsLiveFragment=new NewsLiveFragment();
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(newsFirstFragment, getString(R.string.toutiao));
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_BAGUA), getString(R.string.bagua));
        adapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_NEICAN), getString(R.string.neican));
        adapter.addFragment(newsLiveFragment, getString(R.string.zhibo));
        mViewPager.setAdapter(adapter);
        mTablayout.addTab(mTablayout.newTab().setText(R.string.toutiao));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.bagua));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.neican));
        mTablayout.addTab(mTablayout.newTab().setText(R.string.zhibo));
        mTablayout.setupWithViewPager(mViewPager);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
