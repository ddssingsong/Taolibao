package com.jhs.taolibao.code.news.widget;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jhs.taolibao.R;
import com.jhs.taolibao.adapter.CommentAdapter;
import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.code.news.presenter.CommentPresenterImpl;
import com.jhs.taolibao.code.news.view.NewsDetailView;
import com.jhs.taolibao.code.user.LoginAndRegitActivity;
import com.jhs.taolibao.entity.Comment;
import com.jhs.taolibao.entity.News;
import com.jhs.taolibao.entity.UserInfo;
import com.jhs.taolibao.event.NormalComment;
import com.jhs.taolibao.utils.AppUtil;
import com.jhs.taolibao.utils.CalendarUtil;
import com.jhs.taolibao.utils.DateUtil;
import com.jhs.taolibao.utils.StatusBarUtil;
import com.jhs.taolibao.utils.ToastUtil;
import com.jhs.taolibao.view.TitleBar;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 新闻详情界面
 *
 * @author dds
 */
public class NewsDetailActivity extends BaseActivity implements NewsDetailView, View.OnClickListener {
    private TitleBar titlebar;
    private XRecyclerView recyclerView;
    private CommentAdapter adapter;
    private RelativeLayout layout_faild;//错误界面
    private TextView tv_faild_msg;
    //新闻题目和内容
    TextView txtTitle;//新闻题目
    TextView txtTime;//新闻时间
    TextView txtComment;//？评论
    TextView intComment;//？评论
    private WebView webView;//新闻详情
    //评论
    private RelativeLayout layout_comment;
    EditText tv_click_comment;
    private ImageView iv_send;


    private CommentPresenterImpl presenter;

    private int conut; //评论数
    private int page = 1;
    private News news;//从上一个界面传过来的信息
    private Comment insertcomment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.activity_news_detail);
        if (getIntent() != null) {
            news = (News) getIntent().getSerializableExtra("news");//获取到上一个界面传过来的新闻信息
            presenter = new CommentPresenterImpl(this);
            initView();
            initData();
        }

    }

    private void initView() {
        recyclerView = (XRecyclerView) findViewById(R.id.recycle_comment);
        titlebar = (TitleBar) findViewById(R.id.titlebar);
        layout_faild = (RelativeLayout) findViewById(R.id.layout_faild);
        tv_faild_msg = (TextView) findViewById(R.id.tv_faild_msg);
        tv_click_comment = (EditText) findViewById(R.id.tv_click_comment);
        layout_comment = (RelativeLayout) findViewById(R.id.layout_comment);
        iv_send = (ImageView) findViewById(R.id.iv_send);
        titlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //进行分享
        titlebar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleShare();
            }
        });
        iv_send.setOnClickListener(this);//评论
        layout_faild.setOnClickListener(this); //错误处理

        //新闻内容
        View header = LayoutInflater.from(this).inflate(R.layout.news_detail_header, (ViewGroup) findViewById(android.R.id.content), false);
        recyclerView.addHeaderView(header);
        txtTitle = (TextView) header.findViewById(R.id.txt_news_title);
        txtTime = (TextView) header.findViewById(R.id.txt_news_date);
        txtComment = (TextView) header.findViewById(R.id.txt_news_comment);
        webView = (WebView) header.findViewById(R.id.news_webview);
        intComment = (TextView) header.findViewById(R.id.tv_news_detail_comment);


        //评论内容
        adapter = new CommentAdapter(this, R.layout.item_comment_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadingListener(listener);
        initWebSetting();

    }


    private void initData() {
        //请求新闻内容
        if (news != null) {
            txtTitle.setText(news.getTitle());
            txtTime.setText(DateUtil.timeStamp2Date(news.getPublictime(), "yyyy-MM-dd HH:mm"));
            // webView.loadDataWithBaseURL("fake://not/needed", news.getInfoContent(), "text/html", "UTF-8", "");
            webView.loadUrl("http://218.245.0.52:8081/News/Content?infoId=" + news.getId());

        }
        //请求评论数据
        presenter.loadComment(news.getId(), page, true);
    }

    //下拉刷新
    XRecyclerView.LoadingListener listener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    page = 1;
                    presenter.loadComment(news.getId(), page, false);
                }
            }, 500);
        }

        @Override
        public void onLoadMore() {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    page++;
                    presenter.loadComment(news.getId(), page, false);
                }
            }, 500);
        }
    };


    //初始化网页显示的设置
    private void initWebSetting() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setSaveFormData(true);
        webView.setWebViewClient(new InsideWebViewClient());
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(false);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


    }

    class InsideWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (!webView.getSettings().getLoadsImagesAutomatically()) {
                webView.getSettings().setLoadsImagesAutomatically(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_send:
                //评论
                UserInfo userInfo = UserInfoSingleton.getUserInfo();
                if (userInfo != null) {
                    int userid = UserInfoSingleton.getUserInfo().getId();
                    int newsid = news.getId();
                    String comment = tv_click_comment.getText().toString();
                    insertcomment = new Comment();
                    insertcomment.setAlias(userInfo.getAlias());
                    insertcomment.setUserIcon(userInfo.getIcon());

                    insertcomment.setTimeStr(CalendarUtil.getCurrentDate());
                    insertcomment.setComment(comment);
                    presenter.insertComment(userid, newsid, comment);
                } else {
                    //跳到登陆界面
                    startActivity(new Intent(this, LoginAndRegitActivity.class).putExtra("type", "login"));
                }

                break;

            case R.id.layout_faild:
                presenter.loadComment(news.getId(), page, true);
                break;
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
        recyclerView.setVisibility(View.INVISIBLE);
        tv_faild_msg.setText(R.string.faild_network);
        recyclerView.refreshComplete();
    }

    @Override
    public void addComment(List<Comment> commentlist, int totalcount) {
        intComment.setText(totalcount + "");
        txtComment.setText(totalcount + "评论");
        conut = totalcount;
        if (page == 1) {
            adapter.addDataToAdapater(commentlist, true);
            recyclerView.refreshComplete();

        } else {
            adapter.addDataToAdapater(commentlist, false);
            recyclerView.loadMoreComplete();
        }


    }

    @Override
    public void commentSuccess() {
        ToastUtil.showToast(BaseApplication.getApplication(), "评论成功");
        AppUtil.closeSoftInput(this);
        tv_click_comment.setText("");//将评论框的内容置空
        adapter.addDataToAdapater(insertcomment);
        conut++;
        EventBus.getDefault().post(new NormalComment(conut));
        txtComment.setText(conut + "评论");
        intComment.setText(conut + "");

    }


    //第三放分享
    private void handleShare() {
        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                {
                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
                };
        UMImage umImage = new UMImage(this, R.mipmap.ic_launcher);
        new ShareAction(this).setDisplayList(displaylist)
                .withText(news.getDec())
                .withTitle(news.getTitle())
                .withTargetUrl("http://218.245.0.52:8081/news/Index?infoId=" + news.getId())
                .withMedia(umImage)
                .open();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
