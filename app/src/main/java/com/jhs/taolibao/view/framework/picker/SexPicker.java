package com.jhs.taolibao.view.framework.picker;

import android.app.Activity;

/**
 * 性别
 */
public class SexPicker extends OptionPicker {

    /**
     * Instantiates a new Sex picker.
     *
     * @param activity the activity
     */
    public SexPicker(Activity activity) {
        super(activity, new String[]{
                "男",
                "女"
        });
    }

    /**
     * 仅仅提供男和女来选择
     */
    public void onlyMaleAndFemale() {
        options.remove(options.size() - 1);
    }

}
