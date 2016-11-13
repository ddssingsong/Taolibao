package com.jhs.taolibao.code.market.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhs.taolibao.view.charting.components.Legend;
import com.jhs.taolibao.view.charting.components.LimitLine;
import com.jhs.taolibao.view.charting.components.XAxis;
import com.jhs.taolibao.view.charting.components.YAxis;
import com.jhs.taolibao.view.charting.data.BarData;
import com.jhs.taolibao.view.charting.data.BarDataSet;
import com.jhs.taolibao.view.charting.data.BarEntry;
import com.jhs.taolibao.view.charting.data.Entry;
import com.jhs.taolibao.view.charting.data.LineData;
import com.jhs.taolibao.view.charting.data.LineDataSet;
import com.jhs.taolibao.view.charting.formatter.YAxisValueFormatter;
import com.jhs.taolibao.view.charting.highlight.Highlight;
import com.jhs.taolibao.view.charting.interfaces.datasets.ILineDataSet;
import com.jhs.taolibao.view.charting.listener.OnChartValueSelectedListener;
import com.jhs.taolibao.view.charting.utils.Utils;
import com.jhs.taolibao.R;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.market.prestener.TuPresenterImpl;
import com.jhs.taolibao.code.market.view.MinuteHqView;
import com.jhs.taolibao.code.simtrade.entity.MinutesBean;
import com.jhs.taolibao.code.simtrade.utils.ChartUtils;
import com.jhs.taolibao.code.simtrade.utils.VolFormatter;
import com.jhs.taolibao.entity.Level5;
import com.jhs.taolibao.view.charts.DataParse;
import com.jhs.taolibao.view.charts.MyBarChart;
import com.jhs.taolibao.view.charts.MyLeftMarkerView;
import com.jhs.taolibao.view.charts.MyLineChart;
import com.jhs.taolibao.view.charts.MyRightMarkerView;
import com.jhs.taolibao.view.charts.MyXAxis;
import com.jhs.taolibao.view.charts.MyYAxis;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dds on 2016/7/8.
 *
 * @TODO
 */
public class TimeFragment extends BaseFragment implements MinuteHqView {
    private MyLineChart lineChart;
    private MyBarChart barChart;

    private LineDataSet d1, d2;
    MyXAxis xAxisLine;
    MyYAxis axisRightLine;
    MyYAxis axisLeftLine;
    BarDataSet barDataSet;

    MyXAxis xAxisBar;
    MyYAxis axisLeftBar;
    MyYAxis axisRightBar;
    SparseArray<String> stringSparseArray;
    Integer sum = 0;
    List<Integer> listA, listB;

    private List<Level5> buylist;
    private List<Level5> salelist;
    private TuPresenterImpl tuPresenter;


    private LinearLayout layout_wudang;
    private TextView tv_sale_price5;
    private TextView tv_sale_price4;
    private TextView tv_sale_price3;
    private TextView tv_sale_price2;
    private TextView tv_sale_price1;
    private TextView tv_sale_q5;
    private TextView tv_sale_q4;
    private TextView tv_sale_q3;
    private TextView tv_sale_q2;
    private TextView tv_sale_q1;
    private TextView tv_buy_price1;
    private TextView tv_buy_price2;
    private TextView tv_buy_price3;
    private TextView tv_buy_price4;
    private TextView tv_buy_price5;
    private TextView tv_buy_q1;
    private TextView tv_buy_q2;
    private TextView tv_buy_q3;
    private TextView tv_buy_q4;
    private TextView tv_buy_q5;

    private boolean flag;
    private String stockId;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {

                lineChart.invalidate();//刷新图
                barChart.invalidate();
                setOffset();
            }
        }
    };

    public void setStockid(String stockid) {
        if (flag) {
            this.stockId = stockid;
        } else {
//            if (stockid.contains("sh") || stockid.contains("sz")) {
//                stockid = stockid.substring(2);
//            }
            this.stockId = stockid;
        }


    }

    //如果是从指数进去的就把五档数据隐藏
    public void IsIndex() {
        flag = true;
    }

    //设置买卖五档数据
    public void setBuyAndSale(List<Level5> buylist, List<Level5> salelist) {
        this.buylist = buylist;
        this.salelist = salelist;

        tv_sale_price5.setText(salelist.get(4).getPrice());
        tv_sale_price4.setText(salelist.get(3).getPrice());
        tv_sale_price3.setText(salelist.get(2).getPrice());
        tv_sale_price2.setText(salelist.get(1).getPrice());
        tv_sale_price1.setText(salelist.get(0).getPrice());
        tv_sale_q5.setText(salelist.get(4).getQuantity());
        tv_sale_q4.setText(salelist.get(3).getQuantity());
        tv_sale_q3.setText(salelist.get(2).getQuantity());
        tv_sale_q2.setText(salelist.get(1).getQuantity());
        tv_sale_q1.setText(salelist.get(0).getQuantity());

        tv_buy_price1.setText(buylist.get(4).getPrice());
        tv_buy_price2.setText(buylist.get(3).getPrice());
        tv_buy_price3.setText(buylist.get(2).getPrice());
        tv_buy_price4.setText(buylist.get(1).getPrice());
        tv_buy_price5.setText(buylist.get(0).getPrice());
        tv_buy_q1.setText(buylist.get(4).getQuantity());
        tv_buy_q2.setText(buylist.get(3).getQuantity());
        tv_buy_q3.setText(buylist.get(2).getQuantity());
        tv_buy_q4.setText(buylist.get(1).getQuantity());
        tv_buy_q5.setText(buylist.get(0).getQuantity());
    }

    @Override
    protected boolean onBackPressed() {
        return false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tuPresenter = new TuPresenterImpl(this);
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        lineChart = (MyLineChart) rootView.findViewById(R.id.line_chart);
        barChart = (MyBarChart) rootView.findViewById(R.id.bar_chart);
        layout_wudang = (LinearLayout) rootView.findViewById(R.id.layout_wudang);
        tv_sale_price5 = (TextView) rootView.findViewById(R.id.tv_sale_price5);
        tv_sale_price4 = (TextView) rootView.findViewById(R.id.tv_sale_price4);
        tv_sale_price3 = (TextView) rootView.findViewById(R.id.tv_sale_price3);
        tv_sale_price2 = (TextView) rootView.findViewById(R.id.tv_sale_price2);
        tv_sale_price1 = (TextView) rootView.findViewById(R.id.tv_sale_price1);
        tv_sale_q5 = (TextView) rootView.findViewById(R.id.tv_sale_q5);
        tv_sale_q4 = (TextView) rootView.findViewById(R.id.tv_sale_q4);
        tv_sale_q3 = (TextView) rootView.findViewById(R.id.tv_sale_q3);
        tv_sale_q2 = (TextView) rootView.findViewById(R.id.tv_sale_q2);
        tv_sale_q1 = (TextView) rootView.findViewById(R.id.tv_sale_q1);
        tv_buy_price1 = (TextView) rootView.findViewById(R.id.tv_buy_price1);
        tv_buy_price2 = (TextView) rootView.findViewById(R.id.tv_buy_price2);
        tv_buy_price3 = (TextView) rootView.findViewById(R.id.tv_buy_price3);
        tv_buy_price4 = (TextView) rootView.findViewById(R.id.tv_buy_price4);
        tv_buy_price5 = (TextView) rootView.findViewById(R.id.tv_buy_price5);
        tv_buy_q1 = (TextView) rootView.findViewById(R.id.tv_buy_q1);
        tv_buy_q2 = (TextView) rootView.findViewById(R.id.tv_buy_q2);
        tv_buy_q3 = (TextView) rootView.findViewById(R.id.tv_buy_q3);
        tv_buy_q4 = (TextView) rootView.findViewById(R.id.tv_buy_q4);
        tv_buy_q5 = (TextView) rootView.findViewById(R.id.tv_buy_q5);

        if (flag) {
            layout_wudang.setVisibility(View.GONE);
        }

    }


    //从网络获取分时数据
    @Override
    public void onInitloadData() {
        initChart();
        stringSparseArray = setXLabels();
        tuPresenter.getMinutehqData(stockId);

    }

    //获取分时数据成功
    @Override
    public void getMinutehqDataSuccess(DataParse mData) {
        //设置分时数据
        setData(mData);

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                barChart.highlightValues(new Highlight[]{h});
                lineChart.setHighlightValue(h);
            }

            @Override
            public void onNothingSelected() {
            }
        });
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                //  barChart.highlightValues(new Highlight[]{h});
                lineChart.setHighlightValue(new Highlight(h.getXIndex(), 0));//此函数已经返回highlightBValues的变量，并且刷新，故上面方法可以注释
                // barChart.setHighlightValue(h);
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }

    @Override
    public void getMinutrhpDataFaild() {
        lineChart.setNoDataText("暂无数据");
    }


    //初始化折线图和柱状图
    private void initChart() {
        lineChart.setScaleEnabled(false);
        lineChart.setDrawBorders(true);
        lineChart.setBorderWidth(1);
        lineChart.setBorderColor(getResources().getColor(R.color.minute_grayLine));
        lineChart.setDescription("");
        Legend lineChartLegend = lineChart.getLegend();
        lineChartLegend.setEnabled(false);

        barChart.setScaleEnabled(false);
        barChart.setDrawBorders(true);
        barChart.setBorderWidth(1);
        barChart.setBorderColor(getResources().getColor(R.color.minute_grayLine));
        barChart.setDescription("");


        Legend barChartLegend = barChart.getLegend();
        barChartLegend.setEnabled(false);
        //x轴
        xAxisLine = lineChart.getXAxis();
        xAxisLine.setDrawLabels(true);
        xAxisLine.setPosition(XAxis.XAxisPosition.BOTTOM);
        // xAxisLine.setLabelsToSkip(59);


        //左边y
        axisLeftLine = lineChart.getAxisLeft();
        /*折线图y轴左没有basevalue，调用系统的*/
        axisLeftLine.setLabelCount(5, true);
        axisLeftLine.setDrawLabels(true);
        axisLeftLine.setDrawGridLines(false);
        /*轴不显示 避免和border冲突*/
        axisLeftLine.setDrawAxisLine(false);


        //右边y
        axisRightLine = lineChart.getAxisRight();
        axisRightLine.setLabelCount(2, true);
        axisRightLine.setDrawLabels(true);
        axisRightLine.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                DecimalFormat mFormat = new DecimalFormat("#0.00%");
                return mFormat.format(value);
            }
        });

        axisRightLine.setStartAtZero(false);
        axisRightLine.setDrawGridLines(false);
        axisRightLine.setDrawAxisLine(false);
        //背景线
        xAxisLine.setGridColor(getResources().getColor(R.color.minute_grayLine));
        xAxisLine.setAxisLineColor(getResources().getColor(R.color.minute_grayLine));
        xAxisLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeftLine.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisRightLine.setAxisLineColor(getResources().getColor(R.color.minute_grayLine));
        axisRightLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));

        //bar x y轴
        xAxisBar = barChart.getXAxis();
        xAxisBar.setDrawLabels(false);
        xAxisBar.setDrawGridLines(true);
        xAxisBar.setDrawAxisLine(false);
        // xAxisBar.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisBar.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftBar = barChart.getAxisLeft();
        axisLeftBar.setAxisMinValue(0);
        axisLeftBar.setDrawGridLines(false);
        axisLeftBar.setDrawAxisLine(false);
        axisLeftBar.setTextColor(getResources().getColor(R.color.minute_zhoutv));


        axisRightBar = barChart.getAxisRight();
        axisRightBar.setDrawLabels(false);
        axisRightBar.setDrawGridLines(false);
        axisRightBar.setDrawAxisLine(false);
        //y轴样式
        this.axisLeftLine.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                DecimalFormat mFormat = new DecimalFormat("#0.00");
                return mFormat.format(value);
            }
        });

    }


    private void setData(DataParse mData) {
        setMarkerView(mData);
        setShowLabels(stringSparseArray);
        // Log.e("###", mData.getDatas().size() + "ee");
        if (mData.getDatas().size() == 0) {
            lineChart.setNoDataText("暂无数据");
            return;
        }
        //设置y左右两轴最大最小值
        axisLeftLine.setAxisMinValue(mData.getMin());
        axisLeftLine.setAxisMaxValue(mData.getMax());
        axisRightLine.setAxisMinValue(mData.getPercentMin());
        axisRightLine.setAxisMaxValue(mData.getPercentMax());


        axisLeftBar.setAxisMaxValue(mData.getVolmax());
        /*单位*/
        String unit = ChartUtils.getVolUnit(mData.getVolmax());
        int u = 1;
        if ("万手".equals(unit)) {
            u = 4;
        } else if ("亿手".equals(unit)) {
            u = 8;
        }
        /*次方*/
        axisLeftBar.setValueFormatter(new VolFormatter((int) Math.pow(10, u)));
        axisLeftBar.setShowMaxAndUnit(unit);
        axisLeftBar.setDrawLabels(true);
        //axisLeftBar.setAxisMinValue(0);//即使最小是不是0，也无碍
        axisLeftBar.setShowOnlyMinMax(true);
        axisRightBar.setAxisMaxValue(mData.getVolmax());
        //   axisRightBar.setAxisMinValue(mData.getVolmin);//即使最小是不是0，也无碍
        //axisRightBar.setShowOnlyMinMax(true);

        //基准线
        LimitLine ll = new LimitLine(0);
        ll.setLineWidth(1f);
        ll.setLineColor(getResources().getColor(R.color.minute_jizhun));
        ll.enableDashedLine(10f, 10f, 0f);
        ll.setLineWidth(1);
        axisRightLine.addLimitLine(ll);
        axisRightLine.setBaseValue(0);

        ArrayList<Entry> lineCJEntries = new ArrayList<>();
        ArrayList<Entry> lineJJEntries = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        // Log.e("##", Integer.toString(xVals.size()));
        for (int i = 0, j = 0; i < mData.getDatas().size(); i++, j++) {
           /* //避免数据重复，skip也能正常显示
            if (mData.getDatas().get(i).time.equals("13:30")) {
                continue;
            }*/
            MinutesBean t = mData.getDatas().get(j);

            if (t == null) {
                lineCJEntries.add(new Entry(Float.NaN, i));
                lineJJEntries.add(new Entry(Float.NaN, i));
                barEntries.add(new BarEntry(Float.NaN, i));
                continue;
            }
            if (!TextUtils.isEmpty(stringSparseArray.get(i)) &&
                    stringSparseArray.get(i).contains("/")) {
                i++;
            }
            lineCJEntries.add(new Entry(mData.getDatas().get(i).cjprice, i));
            lineJJEntries.add(new Entry(mData.getDatas().get(i).avprice, i));
            barEntries.add(new BarEntry(mData.getDatas().get(i).cjnum, i));
            // dateList.add(mData.getDatas().get(i).time);
        }
        d1 = new LineDataSet(lineCJEntries, "成交价");
        d2 = new LineDataSet(lineJJEntries, "均价");
        d1.setDrawValues(false);
        d2.setDrawValues(false);
        barDataSet = new BarDataSet(barEntries, "成交量");

        d1.setCircleRadius(0);
        d2.setCircleRadius(0);
        d1.setColor(getResources().getColor(R.color.minute_blue));
        d2.setColor(getResources().getColor(R.color.minute_yellow));
        d1.setHighLightColor(Color.GRAY);
        d2.setHighlightEnabled(false);
        d1.setDrawFilled(true);


        barDataSet.setBarSpacePercent(50); //bar空隙
        barDataSet.setHighLightColor(Color.GRAY);
        barDataSet.setHighLightAlpha(255);
        barDataSet.setDrawValues(false);
        barDataSet.setHighlightEnabled(true);
        barDataSet.setColor(getResources().getColor(R.color.Red));
        List<Integer> list = new ArrayList<>();
        list.add(getResources().getColor(R.color.Red));
        list.add(Color.parseColor("#19A75D"));//跌
        barDataSet.setColors(list);
        //谁为基准
        d1.setAxisDependency(YAxis.AxisDependency.LEFT);
        // d2.setAxisDependency(YAxis.AxisDependency.RIGHT);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        sets.add(d2);
        /*注老版本LineData参数可以为空，最新版本会报错，修改进入ChartData加入if判断*/
        LineData cd = new LineData(getMinutesCount(), sets);
        if (cd != null) {
            lineChart.setData(cd);
        }
        BarData barData = new BarData(getMinutesCount(), barDataSet);
        barChart.setData(barData);

        setOffset();
        barChart.invalidate();
        lineChart.invalidate();//刷新图
        handler.sendEmptyMessageDelayed(1, 200);


    }

    public String[] getMinutesCount() {
        return new String[242];
    }

    private SparseArray<String> setXLabels() {
        SparseArray<String> xLabels = new SparseArray<>();
        xLabels.put(0, "09:30");
        xLabels.put(60, "10:30");
        xLabels.put(121, "11:30/13:00");
        xLabels.put(182, "14:00");
        xLabels.put(241, "15:00");
        return xLabels;
    }

    public void setShowLabels(SparseArray<String> labels) {
        xAxisLine.setXLabels(labels);
        xAxisBar.setXLabels(labels);
    }

    private void setMarkerView(DataParse mData) {
        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(getActivity(), R.layout.mymarkerview);
        MyRightMarkerView rightMarkerView = new MyRightMarkerView(getActivity(), R.layout.mymarkerview);
        lineChart.setMarker(leftMarkerView, rightMarkerView, mData);
    }

    /*设置量表对齐*/
    private void setOffset() {
        float lineLeft = lineChart.getViewPortHandler().offsetLeft();
        float barLeft = barChart.getViewPortHandler().offsetLeft();
        float lineRight = lineChart.getViewPortHandler().offsetRight();
        float barRight = barChart.getViewPortHandler().offsetRight();
        float barBottom = barChart.getViewPortHandler().offsetBottom();
        float offsetLeft, offsetRight;
        float transLeft = 0, transRight = 0;
 /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
        if (barLeft < lineLeft) {
            //offsetLeft = Utils.convertPixelsToDp(lineLeft - barLeft);
            // barChart.setExtraLeftOffset(offsetLeft);
            transLeft = lineLeft;

        } else {
            offsetLeft = Utils.convertPixelsToDp(barLeft - lineLeft);
            lineChart.setExtraLeftOffset(offsetLeft);
            transLeft = barLeft;
        }

  /*注：setExtraRight...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
        if (barRight < lineRight) {
            //offsetRight = Utils.convertPixelsToDp(lineRight);
            //barChart.setExtraRightOffset(offsetRight);
            transRight = lineRight;
        } else {
            offsetRight = Utils.convertPixelsToDp(barRight);
            lineChart.setExtraRightOffset(offsetRight);
            transRight = barRight;
        }
        barChart.setViewPortOffsets(transLeft, 5, transRight, barBottom);
        // barChart.resetViewPortOffsets();
    }


}
