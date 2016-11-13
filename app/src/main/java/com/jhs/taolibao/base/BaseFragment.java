package com.jhs.taolibao.base;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tendcloud.tenddata.TCAgent;

/**
 * Created by dds on 2016/3/4.
 */
public abstract class BaseFragment extends Fragment {
    private BaseFragmentCallback callback;//activity实现这个接口
    protected BackHandleInterface mBackHandledInterface;
    private String defTag;


    public BaseFragment() {
        defTag = getClass().getSimpleName();
    }

    protected abstract boolean onBackPressed();

    /**
     * 将当前Fragment中的事件及数据反馈到其所依附的Activity
     */
    public void callbackToActivity(int eventID, Object data) {
        if (callback != null) {
            callback.onCallback(defTag, eventID, data);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // 判断activity是否实现了这个接口
        if (activity instanceof BaseFragmentCallback) {
            callback = (BaseFragmentCallback) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        TCAgent.onPageStart(getContext(), this.getClass().getSimpleName());
        if (!(getActivity() instanceof BackHandleInterface)) {
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        } else {
            mBackHandledInterface = (BackHandleInterface) getActivity();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        //告诉FragmentActivity，当前Fragment在栈顶
        mBackHandledInterface.setSelectedFragment(this);
    }

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = onInitloadView(inflater, container, savedInstanceState);
            initView(rootView);
            onInitloadData();
        }
        ResetData();
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (rootView != null) {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(getContext(), this.getClass().getSimpleName());
    }

    public abstract View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 当前Fragment的数据加载, 只有在第一次createView时才会触发
     */
    protected abstract void initView(View rootView);

    public abstract void onInitloadData();


    /**
     * 隐藏之后再显示时重新加载数据....
     */
    public void ResetData() {

    }

    public interface BaseFragmentCallback {
        /**
         * 当前fragment有数据要回调回去
         */
        void onCallback(String tag, int eventID, Object data);
    }

    public interface BackHandleInterface {
        void setSelectedFragment(BaseFragment selectFragment);
    }
}
