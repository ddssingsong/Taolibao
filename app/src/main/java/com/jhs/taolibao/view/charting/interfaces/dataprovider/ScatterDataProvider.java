package com.jhs.taolibao.view.charting.interfaces.dataprovider;

import com.jhs.taolibao.view.charting.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
