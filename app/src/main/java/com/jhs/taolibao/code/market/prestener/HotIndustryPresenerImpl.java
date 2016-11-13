package com.jhs.taolibao.code.market.prestener;

import com.jhs.taolibao.code.market.model.HotIndutryModelImpl;
import com.jhs.taolibao.code.market.view.HotIndutryView;
import com.jhs.taolibao.entity.HotIndustry;

import java.util.List;

/**
 * Created by dds on 2016/7/4.
 *
 * @TODO
 */
public class HotIndustryPresenerImpl implements HotIndustryPresener {
    private HotIndutryView hotIndutryView;
    private HotIndutryModelImpl hotIndutryModel;


    public HotIndustryPresenerImpl(HotIndutryView hotIndutryView) {
        this.hotIndutryView = hotIndutryView;
        hotIndutryModel = new HotIndutryModelImpl();
    }

    @Override
    public void getHotIndustry(int page, int size) {
        hotIndutryModel.getHotIndutry(page, size, new HotIndutryModelImpl.onGetHotIndustryListener() {
            @Override
            public void onSuccess(List<HotIndustry> hotIndustries) {
                hotIndutryView.GetHotIndustrySuccess(hotIndustries);
            }

            @Override
            public void onFailure(String msg, Exception e) {
              //  ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }
}
