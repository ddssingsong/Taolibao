package com.jhs.taolibao.code.market.widget;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.adapter.ALeftAdapter;
import com.jhs.taolibao.adapter.BRightAdapter;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.market.StockDetailActivity;
import com.jhs.taolibao.code.market.prestener.FundPresenterImpl;
import com.jhs.taolibao.code.market.view.FundBView;
import com.jhs.taolibao.entity.Fund;
import com.jhs.taolibao.view.MyListView;
import com.jhs.taolibao.view.leftandrightscroll.SyncHorizontalScrollView;
import com.jhs.taolibao.view.leftandrightscroll.UtilTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dds on 2016/7/11.
 *
 * @TODO
 */
public class FenjiBFragment extends BaseFragment implements FundBView, AdapterView.OnItemClickListener {
    private LinearLayout leftContainerView;
    private MyListView leftListView;
    private List<String> leftlList;
    private LinearLayout rightContainerView;
    private MyListView rightListView;
    private List<Fund> models;
    private SyncHorizontalScrollView titleHorsv;
    private SyncHorizontalScrollView contentHorsv;

    private FundPresenterImpl fundPresenter;

    private String stockId = "";
    private String scn = "";
    private String doa = "";
    private ALeftAdapter adapter;


    private RelativeLayout layout_faild;//错误界面
    private TextView tv_faild_msg;
    private ScrollView scrollView;
    @Override
    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fundPresenter = new FundPresenterImpl(this);
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fenji_b, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        leftContainerView = (LinearLayout) rootView.findViewById(R.id.left_container);
        leftListView = (MyListView) rootView.findViewById(R.id.left_container_listview);
        rightContainerView = (LinearLayout) rootView.findViewById(R.id.right_container);
        rightListView = (MyListView) rootView.findViewById(R.id.right_container_listview);
        titleHorsv = (SyncHorizontalScrollView) rootView.findViewById(R.id.title_horsv);
        contentHorsv = (SyncHorizontalScrollView) rootView.findViewById(R.id.content_horsv);
        layout_faild = (RelativeLayout) rootView.findViewById(R.id.layout_faild);
        tv_faild_msg = (TextView) rootView.findViewById(R.id.tv_faild_msg);
        scrollView= (ScrollView) rootView.findViewById(R.id.scrollview);
        // 设置两个水平控件的联动v
        titleHorsv.setScrollView(contentHorsv);
        contentHorsv.setScrollView(titleHorsv);
        rightListView.setOnItemClickListener(this);

        adapter = new ALeftAdapter(getActivity());
        leftListView.setAdapter(adapter);
    }

    @Override
    public void onInitloadData() {
        // 添加左边内容数据
        leftlList = new ArrayList<>();
        fundPresenter.getFundBList(stockId, scn, doa,true);
    }

    @Override
    public void getFundListSuccess(List<Fund> list) {
        for (int i = 0; i < list.size(); i++) {
            leftlList.add(list.get(i).getFundBName());
        }
        adapter.AddDataToAdapter(leftlList);
       // UtilTools.setListViewHeightBasedOnChildren(leftListView);

        models = list;
        // 添加右边内容数据
        BRightAdapter myRightAdapter = new BRightAdapter(getActivity(), models);
        rightListView.setAdapter(myRightAdapter);

        UtilTools.setListViewHeightBasedOnChildren(leftListView);
        UtilTools.setListViewHeightBasedOnChildren(rightListView);
        //UtilTools.setListViewHeightBasedOnChildren(rightListView);
    }

    @Override
    public void showProgress() {
        layout_faild.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        layout_faild.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fund fund = (Fund) parent.getAdapter().getItem(position);
        startActivity(new Intent(getActivity(), StockDetailActivity.class).putExtra("stockid", fund.getFundBCode()));
    }

    public void searchStock(String keyword){
        leftlList = new ArrayList<>();
        fundPresenter.getFundBList(keyword, scn, doa,true);

    }
}
