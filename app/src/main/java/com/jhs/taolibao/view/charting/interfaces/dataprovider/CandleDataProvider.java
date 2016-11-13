package com.jhs.taolibao.view.charting.interfaces.dataprovider;

import com.jhs.taolibao.view.charting.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
