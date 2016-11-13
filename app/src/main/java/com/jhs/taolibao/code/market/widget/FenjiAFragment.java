package com.jhs.taolibao.code.market.widget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.adapter.ALeftAdapter;
import com.jhs.taolibao.adapter.ARightAdapter;
import com.jhs.taolibao.app.WebData;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.market.StockDetailActivity;
import com.jhs.taolibao.code.market.prestener.FundPresenterImpl;
import com.jhs.taolibao.code.market.view.FundAView;
import com.jhs.taolibao.entity.Fund;
import com.jhs.taolibao.view.leftandrightscroll.SyncHorizontalScrollView;
import com.jhs.taolibao.view.leftandrightscroll.UtilTools;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dds on 2016/7/9.
 */
public class FenjiAFragment extends BaseFragment implements FundAView, AdapterView.OnItemClickListener {
    private ListView leftListView;
    private List<String> leftlList;
    private ListView rightListView;
    private List<Fund> models;
    private SyncHorizontalScrollView titleHorsv;
    private SyncHorizontalScrollView contentHorsv;

    private FundPresenterImpl fundPresenter;

    private String stockId = "";
    private String scn = "";
    private String doa = "";
    private ALeftAdapter adapter;
    private ARightAdapter rightAdapter;
    //private SwipeRefreshLayout swiperefresh;


    private RelativeLayout layout_faild;//错误界面
    private TextView tv_faild_msg;
    private ScrollView scrollView;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.AddDataToAdapter(leftlList);
            rightAdapter.addDataToAdapter(models);
            UtilTools.setListViewHeightBasedOnChildren(leftListView);
            UtilTools.setListViewHeightBasedOnChildren(rightListView);
            hideProgress();
        }
    };

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
        View view = inflater.inflate(R.layout.fragment_fenji_a, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        leftListView = (ListView) rootView.findViewById(R.id.left_container_listview);
        rightListView = (ListView) rootView.findViewById(R.id.right_container_listview);
        titleHorsv = (SyncHorizontalScrollView) rootView.findViewById(R.id.title_horsv);
        contentHorsv = (SyncHorizontalScrollView) rootView.findViewById(R.id.content_horsv);
        layout_faild = (RelativeLayout) rootView.findViewById(R.id.layout_faild);
        tv_faild_msg = (TextView) rootView.findViewById(R.id.tv_faild_msg);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollview);
        // 设置两个水平控件的联动
        titleHorsv.setScrollView(contentHorsv);
        contentHorsv.setScrollView(titleHorsv);
        rightListView.setOnItemClickListener(this);
        adapter = new ALeftAdapter(getActivity());
        rightAdapter=new ARightAdapter(getContext());
        leftListView.setAdapter(adapter);
        rightListView.setAdapter(rightAdapter);


    }

    @Override
    public void onInitloadData() {
        leftlList = new ArrayList<>();

        //获取数据
        //fundPresenter.getFundAList(stockId, scn, doa, true);

        getAsynHttp();
    }


    @Override
    public void getFundListSuccess(final List<Fund> list) {
        for (int i = 0; i < list.size(); i++) {
            leftlList.add(list.get(i).getFundAName());
        }
        adapter.AddDataToAdapter(leftlList);
        models = list;
        // 添加右边内容数据
        ARightAdapter myRightAdapter = new ARightAdapter(getActivity(), models);
        rightListView.setAdapter(myRightAdapter);
        //swiperefresh.setRefreshing(false);

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
        startActivity(new Intent(getActivity(), StockDetailActivity.class).putExtra("stockid", fund.getFundACode()));

    }


    public void searchStock(String keyword) {
        leftlList = new ArrayList<>();
        fundPresenter.getFundAList(keyword, scn, doa, true);

    }

    //获取数据
    private void getAsynHttp() {
        showProgress();
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("StockID", stockId)
                .add("scn", scn)
                .add("doa", doa)
                .add("op", "fja")
                .build();
        Request request = new Request.Builder()
                .url(WebData.Fuzaiurl)
                .post(builder.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String str = response.body().string();
                try {
                    JSONObject object = new JSONObject(str);
                    int code = object.getInt("Code");
                    if (code == 0) {
                        models = new ArrayList<Fund>();
                        leftlList = new ArrayList<String>();
                        JSONArray array = object.getJSONArray("Data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object1 = (JSONObject) array.get(i);
                            Fund fund = new Fund();
                            fund.setJsonObject(object1);
                            leftlList.add(fund.getFundAName());
                            models.add(fund);
                        }
                        handler.sendEmptyMessage(1);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
