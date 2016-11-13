package com.jhs.taolibao.code.news.widget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jhs.taolibao.R;
import com.jhs.taolibao.adapter.NewsFirstAdapter;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.guess.GuessActivity;
import com.jhs.taolibao.code.my.GoldActivity;
import com.jhs.taolibao.code.my.widget.BaobiActivity;
import com.jhs.taolibao.code.news.presenter.NewsPresenterImpl;
import com.jhs.taolibao.code.news.view.NewsFirstView;
import com.jhs.taolibao.code.simtrade.activity.KeepActivity;
import com.jhs.taolibao.code.user.LoginAndRegitActivity;
import com.jhs.taolibao.entity.Dynamic;
import com.jhs.taolibao.entity.News;
import com.jhs.taolibao.entity.UserInfo;
import com.jhs.taolibao.utils.DialogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by dds on 2016/6/13
 *
 * @TODO 新闻界面 轮播图+文字新闻列表
 */
public class NewsFirstFragment extends BaseFragment implements NewsFirstView, BaseSliderView.OnSliderClickListener {
    private XRecyclerView recyclerView;
    private NewsFirstAdapter adapter;
    private SliderLayout mSlider;//图片轮播
    private RelativeLayout layout_faild;//错误界面
    private TextView tv_faild_msg;

    private NewsPresenterImpl newsPresenter;

    private boolean flag;//进入每日金股的标志
    private int page = 1;
    private int type = 1;//type=1表示获取类型1：新闻

    @Override
    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsPresenter = new NewsPresenterImpl(this);
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_first, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        recyclerView = (XRecyclerView) rootView.findViewById(R.id.recycle_view);
        layout_faild = (RelativeLayout) rootView.findViewById(R.id.layout_faild);
        tv_faild_msg = (TextView) rootView.findViewById(R.id.tv_faild_msg);
        mSlider = (SliderLayout) rootView.findViewById(R.id.slider);
        //下拉刷新+初始化recyclerView
        recyclerView.setLoadingListener(listener);
        adapter = new NewsFirstAdapter(getActivity(), R.layout.item_news_first);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallTrianglePath);
        recyclerView.setAdapter(adapter);

        //点击错误图标重新请求数据
        layout_faild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsPresenter.loadDynamic();//广告
                newsPresenter.loadFirstNews(type, page, null, false);
            }
        });
    }

    @Override
    public void onInitloadData() {
        newsPresenter.loadDynamic();//广告
        newsPresenter.loadFirstNews(type, page, null, true);//新闻
        //获取用户信息
        if (UserInfoSingleton.getUserInfo() != null) {
            newsPresenter.getuserInfo(UserInfoSingleton.getUserId());
        }


    }

    XRecyclerView.LoadingListener listener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    page = 1;
                    newsPresenter.loadFirstNews(type, page, null, false);
                    // newsPresenter.loadDynamic();//广告
                }
            }, 500);
        }

        @Override
        public void onLoadMore() {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    page++;
                    newsPresenter.loadFirstNews(type, page, null, false);
                }
            }, 500);
        }
    };


    //获取新闻数据成功
    @Override
    public void addNews(List<News> newsList) {
        if (page == 0 || page == 1) {
            adapter.addDataToAdapater(newsList, true);
            recyclerView.refreshComplete();

        } else {
            adapter.addDataToAdapater(newsList);
            recyclerView.loadMoreComplete();
        }

    }

    @Override
    public void showProgress() {
        layout_faild.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        layout_faild.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

    }

    @Override
    public void showErrorMsg() {
        layout_faild.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tv_faild_msg.setText(R.string.faild_network);
        recyclerView.refreshComplete();
    }

    //获取动态图数据
    @Override
    public void addDynamic(List<Dynamic> dynamicList) {
        for (int i = 0; i < dynamicList.size(); i++) {
            Dynamic dynamic = dynamicList.get(i);

            DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
            String picurl = dynamic.getPicurl().replace("\\", "/");
            try {
                String str = WebBiz.UPLOAD_SLIDER + URLEncoder.encode(picurl, "utf-8");
                textSliderView
                        .description(dynamic.getContent())
                        .image(WebBiz.UPLOAD_SLIDER + URLEncoder.encode(picurl, "utf-8"))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //add your extra information
            textSliderView.bundle(new Bundle());
            Bundle bundle = textSliderView.getBundle();
            bundle.putInt("action", dynamic.getAction());
            bundle.putString("actionUrl", dynamic.getActionUrl());
            mSlider.addSlider(textSliderView);
            mSlider.setPresetTransformer(SliderLayout.Transformer.Default);
            mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
            mSlider.setCustomAnimation(new DescriptionAnimation());
            mSlider.setDuration(5000);

        }

    }

    //获取用户信息成功
    @Override
    public void getuserInfoSuccess(UserInfo userInfo) {
        if (userInfo.isPayuser()) {
            flag = true;
        } else {
            flag = false;
        }
    }

    @Override
    public void onDestroyView() {
        mSlider.stopAutoCycle();//停止轮播图
        super.onDestroyView();
    }

    //轮播图点击
    @Override
    public void onSliderClick(BaseSliderView slider) {

        int action = slider.getBundle().getInt("action");
        if (action == 1) {
            //外部
            startActivity(new Intent(getActivity(), AdActivity.class).putExtra("url", slider.getBundle().getString("actionUrl")));
        } else {
            //内部
            String actionurl = slider.getBundle().getString("actionUrl");
            if (actionurl.equals("1")) {  //跳到模拟交易
                if (UserInfoSingleton.getUserInfo() != null) {
                    startActivity(new Intent(getActivity(), KeepActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "login"));
                }
            }
            if (actionurl.equals("2")) {//跳到每日金股
                if (UserInfoSingleton.getUserInfo() != null) {
                    if (flag) {
                        startActivity(new Intent(getActivity(), GoldActivity.class));
                    } else {
                        DialogUtil.alertDialogTipsNoDraw(getActivity(), "温馨提示", "你需要到我的信息界面查看每日金股");
                    }
                } else {
                    startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "login"));
                }


            }
            if (actionurl.equals("3")) {//跳到猜涨跌界面
                if (UserInfoSingleton.getUserInfo() != null) {
                    startActivity(new Intent(getActivity(), GuessActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "login"));
                }
            }
            if (actionurl.equals("4")) {//跳到我的宝币界面
                if (UserInfoSingleton.getUserInfo() != null) {
                    startActivity(new Intent(getActivity(), BaobiActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "login"));
                }
            }
        }

    }

}
