package com.jhs.taolibao.view.charting.interfaces.dataprovider;

import com.jhs.taolibao.view.charting.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
