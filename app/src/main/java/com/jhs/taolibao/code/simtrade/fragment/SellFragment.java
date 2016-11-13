package com.jhs.taolibao.code.simtrade.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.simtrade.SimtradeCenter;
import com.jhs.taolibao.code.simtrade.adapter.BuySellInfoAdapter;
import com.jhs.taolibao.code.simtrade.adapter.KeepAdapter2;
import com.jhs.taolibao.code.simtrade.adapter.StockWizardAdapter;
import com.jhs.taolibao.code.simtrade.base.BaseFragment2;
import com.jhs.taolibao.code.simtrade.entity.KeepBean;
import com.jhs.taolibao.code.simtrade.entity.Stock;
import com.jhs.taolibao.code.simtrade.utils.HscloudUtils;
import com.jhs.taolibao.code.simtrade.view.CustomAutoCompleteTextView;
import com.jhs.taolibao.code.simtrade.view.EntrustAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 卖出界面
 * Created by xujingbo on 2016/7/7.
 */
public class SellFragment extends BaseFragment2{
    private static final String TAG = "SellFragment";

    //要查询的股票名字或代码
    private AutoCompleteTextView etStockNameCode;
    //股票名称
    private TextView tvStockName;
    //股票买入价
    private EditText etStockPrice;
    //股票买入数目
    private EditText etStockCount;
    //可买入最大数目
    private TextView tvStockTotalCount;
    //涨停值
    private TextView tvDownPx,tvUpPx;

    private ListView lvSellInfo,lvBuyInfo;
    //股票信息
    private ListView lvKeepInfo;

    private BuySellInfoAdapter buyAdapter;
    private BuySellInfoAdapter sellAdapter;
    private KeepAdapter2 keepAdapter;

    private List<Stock.Grp> bidGrp = new ArrayList<>();
    private List<Stock.Grp> offerGrp = new ArrayList<>();
    //当前操作的股票
    private Stock curStock;
    //上一次操作的股票
    private Stock lastStock;

    private List<Stock> dataWizard = new ArrayList<>();

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            update();
            handler.postDelayed(this,6000 );//6秒刷新一次
        }
        /**
         * 只刷新买5 卖5
         */
        void update(){
            if (null == curStock){
                return;
            }
            getReal(curStock.getCode());
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy,null);
    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {

//        curStock = new Stock();
//        curStock.setCode("600601.SS");
        initView();
        refreshKeenInfo();
        setAdapter();
        handler.postDelayed(runnable,6000); //开始刷新
    }

    /**
     * 初始化view
     */
    private void initView(){
        CustomAutoCompleteTextView TvCustomAutoComplete =
                (CustomAutoCompleteTextView)getView().findViewById(R.id.simtrade_et_stock_name_code);
        etStockNameCode = TvCustomAutoComplete.getAutoCompleteTextView();
        tvStockName = TvCustomAutoComplete.getTextView();
        //设置AutoCompleteTextView的一些属性
        etStockNameCode.setTextSize(15);
        etStockNameCode.setThreshold(1);
        etStockNameCode.setDropDownVerticalOffset(4);
        etStockNameCode.setDropDownHeight(300);
        etStockNameCode.setDropDownBackgroundResource(R.color.White);
        etStockNameCode.setHint(getContext().getResources().getString(R.string.stok_name_code));
        etStockNameCode.setHintTextColor(getContext().getResources().getColor(R.color.Aluminum));
        etStockNameCode.setTextColor(getContext().getResources().getColor(R.color.Aluminum));
        etStockNameCode.setInputType(InputType.TYPE_CLASS_NUMBER);
        //股票名称
        tvStockName.setTextColor(getContext().getResources().getColor(R.color.Aluminum));
        etStockPrice = (EditText) getView().findViewById(R.id.simtrade_et_stock_price);
        etStockCount = (EditText) getView().findViewById(R.id.simtrade_et_stock_count);
        tvStockTotalCount = (TextView) getView().findViewById(R.id.tv_total_count_value);
        tvDownPx = (TextView) getView().findViewById(R.id.tv_limit_dowm_value);
        tvUpPx = (TextView) getView().findViewById(R.id.tv_limit_up_value);

        //设置背景图片
        etStockNameCode.setBackgroundResource(R.drawable.rectangle_blue_bg);
        ImageButton btnPlus = (ImageButton) getView().findViewById(R.id.simtrade_btn_plus);
        btnPlus.setImageResource(R.drawable.btn_sellplus);
        ImageButton btnReduce = (ImageButton) getView().findViewById(R.id.simtrade_btn_reduce);
        btnReduce.setImageResource(R.drawable.btn_sellreduce);
        final Button btnSell = (Button)getView().findViewById(R.id.btn_buy);
        btnSell.setBackgroundResource(R.drawable.rectangle_blue_solid_bg);
        btnSell.setText("卖出");

        TextView tvEnableAmount = (TextView)getView().findViewById(R.id.tv_total_count);
        tvEnableAmount.setText("可卖量");

        lvBuyInfo = (ListView) getView().findViewById(R.id.simtrade_lv_buy);
        lvSellInfo = (ListView) getView().findViewById(R.id.simtrade_lv_sell);
        lvKeepInfo = (ListView) getView().findViewById(R.id.simtrade_lv_stocks_info);
        lvKeepInfo.setEmptyView(getView().findViewById(R.id.ll_data_null));

        etStockNameCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: s = " + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: s = " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: s = " + s);
                if (null == s.toString() || s.toString().length() == 0) {
                    curStock = null;
                    tvStockName.setText("");
                }
                if (s.toString().length() > 0) {
                    getWizard(s.toString());
                }
                if (s.toString().length() >= 6) {
                    if (null != curStock) {
                        getReal(curStock.getCode());
                    }
                }
            }
        });
        if ( null != curStock && null != curStock.getCode() ){
            etStockNameCode.setText(curStock.getCodeWithoutMic());
        }

        etStockNameCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curStock = ((StockWizardAdapter) etStockNameCode.getAdapter()).getItem(position);
                etStockNameCode.setText(curStock.getCodeWithoutMic());
                tvStockName.setText(curStock.getName());

            }
        });
        etStockPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getEnableAmount();
            }
        });

        setViewClick(R.id.simtrade_btn_plus);
        setViewClick(R.id.simtrade_btn_reduce);
        setViewClick(R.id.btn_buy);
    }
    private void setAdapter(){
        buyAdapter = new BuySellInfoAdapter(getContext(),bidGrp, Stock.Type.BUY);
        lvBuyInfo.setAdapter(buyAdapter);
        sellAdapter = new BuySellInfoAdapter(getContext(),offerGrp,Stock.Type.SELL);
        lvSellInfo.setAdapter(sellAdapter);
        //持仓
        keepAdapter = new KeepAdapter2();
        lvKeepInfo.setAdapter(keepAdapter);
        lvKeepInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KeepBean.KeepItem item = ((KeepAdapter2) lvKeepInfo.getAdapter()).getItem(position);
                clearCurStock();
                curStock.setCodeWithoutMic(item.getStock_code());
                curStock.setName(item.getStock_name());
                curStock.setCode(item.getStock_code1());
                etStockNameCode.setText(item.getStock_code());
                tvStockName.setText(curStock.getName());
            }
        });
    }

    /**
     * 获取持仓信息
     */
    private void refreshKeenInfo(){
        showProgressDialog();

        try {
            SimtradeCenter.getInstance().getKeepInfo(SimtradeCenter.getInstance().getUserId(), null,null,
                    new HscloudUtils.onGetBeanListener() {
                        @Override
                        public void onSuccess(KeepBean response) {
                            disMissDialog();
                            if (null != response) {
                                keepAdapter.setData(response.getData());
                            }
                        }

                        @Override
                        public void onFailure(String msg, Exception e) {
                            disMissDialog();
                        }
                    });
        } catch (Exception e) {
            disMissDialog();
            e.printStackTrace();
        }
    }
    /**
     * 刷新买卖档位信息
     */
    private void refreshBuySellInfo(){
        if (null == curStock){
            bidGrp.clear();
            offerGrp.clear();
            //return;
        }else {
            bidGrp = curStock.getBidGrp();
            offerGrp = curStock.getOfferGrp();
        }
        bidGrp = curStock.getBidGrp();
        offerGrp = curStock.getOfferGrp();
        buyAdapter.setDatas(bidGrp);
        buyAdapter.notifyDataSetChanged();
        sellAdapter.setDatas(offerGrp);
        sellAdapter.notifyDataSetChanged();

    }
    @Override
    public void OnViewClick(View v) {
        switch (v.getId()){
            //买入
            case R.id.btn_buy:
                showEntrustAlertDialog();
                break;
            case R.id.simtrade_btn_plus:
                onPricePlus();
                break;
            case R.id.simtrade_btn_reduce:
                onPriceReduce();
                break;
        }
    }

    /**
     * 卖出确认框
     */
    private void showEntrustAlertDialog(){
        if (null == curStock || null == etStockNameCode.getText()){
            showToast("请输入股票代码");
            return;
        }
        String price = etStockPrice.getText().toString();

        String count = etStockCount.getText().toString();
        if (null == price){
            showToast("请输入委托价格");
            return;
        }else if (null == count || count.equals("")){
            showToast("请输入委托数量");
            return;
        } else if (Long.parseLong(count) % 100 > 0){
            showToast("卖出入数量应为100的整数倍");
            return;
        }else {
            String totalCount = tvStockTotalCount.getText().toString();
            if (totalCount.contains("--")){
                return;
            }else if (Long.parseLong(count) > Long.parseLong(tvStockTotalCount.getText().toString())){
                showToast("委托数量不可超过最大可卖量");
                return;
            }

        }
        new EntrustAlertDialog(getContext()).builder()
                .setTitle("委托卖出")
                .setMsgContent1("证券名称",curStock.getName())
                .setMsgContent2("证券代码", curStock.getCodeWithoutMic())
                .setMsgContent3("委托价格", price)
                .setMsgContent4("委托数量",count)
                .hideContent(5)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sell();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do nothing
            }
        }).show();

    }
    private void clearCurStock(){
        curStock = null;
        curStock = new Stock();
    }
    /**
     * 降低价格，每次降0.01
     */
    private void onPriceReduce(){
        String stockPrice = etStockPrice.getText().toString();
        if (null != stockPrice && !stockPrice.equals("")){
            DecimalFormat decimalFormat = new DecimalFormat(".00");
            stockPrice = decimalFormat.format(Float.valueOf(stockPrice) - 0.01);
            etStockPrice.setText(stockPrice);
            //将EditText光标置于末尾
            setSelectionEnd(etStockPrice);
        }
    }

    /**
     * 增加价格，每次加0.01
     */
    private void onPricePlus(){
        String stockPrice = etStockPrice.getText().toString();
        if (null != stockPrice && !stockPrice.equals("")){
            DecimalFormat decimalFormat = new DecimalFormat(".00");
            stockPrice = decimalFormat.format(Float.valueOf(stockPrice) + 0.01);
            etStockPrice.setText(stockPrice);
            //将EditText光标置于末尾
            setSelectionEnd(etStockPrice);
        }
    }
    private void refreshPrice(){
        Log.d(TAG, "refreshPrice: ");
        if (curStock != null){
            if (null == lastStock || !lastStock.getCode().equals(curStock.getCode())) {
                lastStock = curStock;
                tvUpPx.setText(curStock.getUpPx());
                tvDownPx.setText(curStock.getDownPx());
                //卖出价默认显示当前买1
                etStockPrice.setText(curStock.getBidGrp().get(0).getPrice());
            }
        }else {
            tvUpPx.setText("--");
            tvDownPx.setText("--");
        }
    }

    private void refreshCount(String count){
        Log.d(TAG, "refreshCount: " + count);
        //String strCount = TextUtils.substring(count, 0, count.length() - 3);
        tvStockTotalCount.setText(count);

    }

    /**
     * 获取指定代码股票的行情
     * @param codeOrName
     */
    private void getReal(final String codeOrName){
        Log.d(TAG, "getReal: ");
        try {
            SimtradeCenter.getInstance().getReal(codeOrName, new HscloudUtils.onGetListListener() {
                @Override
                public void onSuccess(String response) {
                    Log.d(TAG, "onSuccess: response" + response);
                    try {
                        JSONObject json = new JSONObject(response);
                        if (json.has("data")) {
                            JSONObject snapshpt = json.getJSONObject("data").getJSONObject("snapshot");

                            JSONArray responseArray = snapshpt.getJSONArray(codeOrName);
                            if (null == curStock) {
                                curStock = new Stock();
                            }

                            curStock.setHighPx(responseArray.getString(2));
                            curStock.setLowPx(responseArray.getString(3));
                            curStock.setUpPx(responseArray.getString(6));
                            curStock.setDownPx(responseArray.getString(7));
                            curStock.setBidGrp(responseArray.getString(4));
                            curStock.setOfferGrp(responseArray.getString(5));
                            curStock.setName(responseArray.getString(8));

                            refreshPrice();
                            refreshBuySellInfo();
                        }
                    } catch (JSONException e) {
                        Log.i(TAG, "onSuccess: " + e.toString());
                    }
                }

                @Override
                public void onFailure(String msg, Exception e) {
                    Log.d(TAG, "onFailure: msg :" + msg);
                }
            });
        }catch (Exception e){

        }
    }

    /**
     * 获取按键精灵
     * @param code
     */
    private void getWizard(final String code){
        try {
            SimtradeCenter.getInstance().getWizard(code, new HscloudUtils.onGetListListener() {
                @Override
                public void onSuccess(String response) {
                    Log.d(TAG, "getWizard onSuccess: " + response);
                    try {
                        JSONObject json = new JSONObject(response);
                        if (json.has("data")) {
                            JSONArray data = json.getJSONArray("data");
                            dataWizard.clear();
                            for (int i = 0; i < data.length(); i++) {
                                Stock stock = new Stock();
                                JSONObject object = data.getJSONObject(i);
                                String code = object.getString("prod_code");
                                stock.setCode(object.getString("prod_code"));
                                stock.setName((object.getString("prod_name")));
                                stock.setCodeWithoutMic(TextUtils.substring(code, 0, 6));
                                dataWizard.add(stock);
                            }

                            etStockNameCode.setAdapter(new StockWizardAdapter(getContext(), dataWizard,code));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(String msg, Exception e) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取持仓可用数量
     */
    private void getEnableAmount(){
        Log.d(TAG, "getEnableAmount: ");
        if (null == curStock){
            return;
        }
        try {
            SimtradeCenter.getInstance().getKeepInfo(
                    SimtradeCenter.getInstance().getUserId(),null, curStock.getCodeWithoutMic(),
                    new HscloudUtils.onGetBeanListener() {
                        @Override
                        public void onSuccess(KeepBean response) {
                            if (null != response
                                    && null != response.getData()
                                    && response.getData().size() > 0) {
                                KeepBean.KeepItem keepItem = response.getData().get(0);
                                String enableAmount = keepItem.getEnable_amount();
                                refreshCount(enableAmount);
                            }
                        }

                        @Override
                        public void onFailure(String msg, Exception e) {

                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 委托卖出
     */
    private void sell(){
        if (null == curStock || null == etStockNameCode.getText()){
            showToast("请输入股票代码");
            return;
        }
        String code = curStock.getCode();
        String price = etStockPrice.getText().toString();
        String count = etStockCount.getText().toString();

        if (null == price){
            showToast("请输入委托价格");
            return;
        }else if (null == count || count.equals("")){
            showToast("请输入委托数量");
            return;
        } else if (Long.parseLong(count) % 100 > 0){
            showToast("卖出入数量应为100的整数倍");
            return;
        }else {
            String totalCount = tvStockTotalCount.getText().toString();
            if (totalCount.contains("--")){
                return;
            }else if (Long.parseLong(count) > Long.parseLong(tvStockTotalCount.getText().toString())){
                showToast("委托数量不可超过最大可卖量");
                return;
            }

        }
        try {
            SimtradeCenter.getInstance().entrustEnter(code, price, count, "2", new HscloudUtils.onGetListListener() {
                @Override
                public void onSuccess(String response) {
                    Log.d(TAG, "sell onSuccess: " + response);
                    try {
                        JSONObject json = new JSONObject(response);
                        if (!json.isNull("data")) {
                            resetView();
                            refreshKeenInfo();
                            showToast("交易成功");
                        }else{

                            if (json.has("error_code")){

                                int errorCode = json.getInt("error_code");
                                if (errorCode == 10220){
                                    showToast(json.getString("error_info"));
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String msg, Exception e) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetView(){
        curStock = null;
        etStockPrice.setText("");
        etStockNameCode.setText("");
        etStockCount.setText("");
        tvDownPx.setText("--");
        tvUpPx.setText("--");
        tvStockTotalCount.setText("--");
        bidGrp.clear();
        offerGrp.clear();

        buyAdapter.setDatas(bidGrp);
        buyAdapter.notifyDataSetChanged();
        sellAdapter.setDatas(offerGrp);
        sellAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable); //停止刷新
        super.onDestroy();
    }
}
