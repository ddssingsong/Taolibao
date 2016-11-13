package com.jhs.taolibao.view.framework.picker;

import android.app.Activity;

/**
 * 星座选择器
 *

 */
public class ConstellationPicker extends OptionPicker {

    /**
     * Instantiates a new Constellation picker.
     *
     * @param activity the activity
     */
    public ConstellationPicker(Activity activity) {
        super(activity, new String[]{
                "水瓶",
                "双鱼",
                "白羊",
                "金牛",
                "双子",
                "巨蟹",
                "狮子",
                "处女",
                "天秤",
                "天蝎",
                "射手",
                "摩羯",
        });
        setLabel("座");
    }

}
