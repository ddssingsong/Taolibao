package com.jhs.taolibao.view.charting.data.realm.implementation;

import com.jhs.taolibao.view.charting.data.CandleData;
import com.jhs.taolibao.view.charting.data.realm.base.RealmUtils;
import com.jhs.taolibao.view.charting.interfaces.datasets.ICandleDataSet;

import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Philipp Jahoda on 19/12/15.
 */
public class RealmCandleData extends CandleData {

    public RealmCandleData(RealmResults<? extends RealmObject> result, String xValuesField, List<ICandleDataSet> dataSets) {
        super(RealmUtils.toXVals(result, xValuesField), dataSets);
    }
}
