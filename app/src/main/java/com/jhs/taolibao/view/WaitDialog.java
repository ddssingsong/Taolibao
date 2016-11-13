
package com.jhs.taolibao.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

import com.jhs.taolibao.R;

public class WaitDialog extends ProgressDialog {

    public WaitDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.progress_tans);
        setProgressStyle(STYLE_SPINNER);
        setMessage("拼命加载中…");
    }

}