package com.jhs.taolibao.view.charting.interfaces.dataprovider;

import com.jhs.taolibao.view.charting.components.YAxis.AxisDependency;
import com.jhs.taolibao.view.charting.data.BarLineScatterCandleBubbleData;
import com.jhs.taolibao.view.charting.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    int getMaxVisibleCount();
    boolean isInverted(AxisDependency axis);
    
    int getLowestVisibleXIndex();
    int getHighestVisibleXIndex();

    BarLineScatterCandleBubbleData getData();
}
