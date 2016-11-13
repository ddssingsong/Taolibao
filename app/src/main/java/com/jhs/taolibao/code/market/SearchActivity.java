package com.jhs.taolibao.code.market;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.base.recyclerview.CommonAdapter;
import com.jhs.taolibao.base.recyclerview.ViewHolder;
import com.jhs.taolibao.code.market.prestener.SearchStockPresenterImpl;
import com.jhs.taolibao.code.market.view.SearchView;
import com.jhs.taolibao.entity.SearchStock;
import com.jhs.taolibao.utils.StatusBarUtil;
import com.jhs.taolibao.utils.ToastUtil;
import com.jhs.taolibao.view.TitleBar;
import com.jhs.taolibao.view.topsnaker.TSnackbar;

import java.util.List;

/**
 * 搜索股票界面
 *
 * @Author dds
 */
public class SearchActivity extends BaseActivity implements SearchView {
    private TitleBar titleBar;
    private EditText et_search_stock;
    private RelativeLayout layout_search_history;
    private TextView tv_clear_search_history;

    private RecyclerView recycle_search_history;
    private RecyclerView recycle_search_info;
    private CommonAdapter adapter;
    private SearchStockPresenterImpl searchPresenter;
    private RelativeLayout layout_main_search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(SearchActivity.this);
        setContentView(R.layout.activity_search);


        searchPresenter = new SearchStockPresenterImpl(this);
        initView();
        initData();
    }


    private void initView() {
        layout_main_search = (RelativeLayout) findViewById(R.id.layout_main_search);
        et_search_stock = (EditText) findViewById(R.id.et_search_stock);
        layout_search_history = (RelativeLayout) findViewById(R.id.layout_search_history);
        tv_clear_search_history = (TextView) findViewById(R.id.tv_clear_search_history);
        recycle_search_history = (android.support.v7.widget.RecyclerView) findViewById(R.id.recycle_search_history);
        recycle_search_info = (android.support.v7.widget.RecyclerView) findViewById(R.id.recycle_search_info);
        titleBar = (TitleBar) findViewById(R.id.titlebar);
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initData() {
        adapter = new CommonAdapter<SearchStock>(this, R.layout.item_stock_search_list) {

            @Override
            public void convert(ViewHolder holder, final SearchStock searchStock) {

                holder.setText(R.id.tv_stock_name, searchStock.getName());
                holder.setText(R.id.tv_stock_code, searchStock.getStockCode());
                holder.setOnClickListener(R.id.btn_add_option, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String code = searchStock.getCode();
                        if (UserInfoSingleton.getUserInfo() != null) {
                            searchPresenter.InsetOptional(UserInfoSingleton.getUserId(), code);
                        } else {
                            ToastUtil.showToast(BaseApplication.getApplication(), "请登录");
                        }


                    }
                });

            }
        };

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_search_info.setLayoutManager(linearLayoutManager);
        recycle_search_info.setAdapter(adapter);

        et_search_stock.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {

                    //这里调用搜索方法
                    String keyword = et_search_stock.getText().toString().trim();
                    searchPresenter.GetBinefStock(keyword);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void GetBinefStockSuccess(List<SearchStock> list) {
        adapter.addDataToAdapater(list, true);
    }

    @Override
    public void InsetOptionalSuccess() {
        TSnackbar snackbar = TSnackbar
                .make(layout_main_search, "自选股已添加成功", TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.addIcon(R.drawable.ic_check_white, 200);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor("#883C8BE1"));
        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
