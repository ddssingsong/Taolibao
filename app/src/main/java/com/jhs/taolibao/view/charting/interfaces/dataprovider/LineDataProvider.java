package com.jhs.taolibao.view.charting.interfaces.dataprovider;

import com.jhs.taolibao.view.charting.components.YAxis;
import com.jhs.taolibao.view.charting.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
