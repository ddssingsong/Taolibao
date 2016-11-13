package com.jhs.taolibao.view.framework.picker;

import android.app.Activity;

/**
 * 数字选择器
 *

 */
public class NumberPicker extends OptionPicker {

    /**
     * Instantiates a new Number picker.
     *
     * @param activity the activity
     */
    public NumberPicker(Activity activity) {
        super(activity, new String[]{});
    }

    /**
     * Sets range.
     *
     * @param startNumber the start number
     * @param endNumber   the end number
     */
    public void setRange(int startNumber, int endNumber) {
        setRange(startNumber, endNumber, 1);
    }

    /**
     * Sets range.
     *
     * @param startNumber the start number
     * @param endNumber   the end number
     * @param step        the step
     */
    public void setRange(int startNumber, int endNumber, int step) {
        for (int i = startNumber; i <= endNumber; i = i + step) {
            options.add(String.valueOf(i));
        }
    }

    /**
     * Sets selected item.
     *
     * @param number the number
     */
    public void setSelectedItem(int number) {
        setSelectedItem(String.valueOf(number));
    }

}

