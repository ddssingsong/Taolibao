package com.jhs.taolibao.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Window;


/**
 * Created by dds on 2016/7/12.
 *
 * @TODO
 */
public class DialogUtil {

    private static AlertDialog alertDialog;
    private static ProgressDialog progressdialog;

    /**
     * 弹出确定和取消两个按钮的dialog
     *
     * @param context 上下文
     * @param title   标题文字
     * @param content 提示信息
     */
    public static void alertDialogTips(Context context, String title, String content,
                                       final ICommonCallBack okCallback, final ICommonCallBack cancelCallback) {
        alertDialog = new AlertDialog.Builder(context).setTitle(title).setIcon(android.R.drawable.ic_menu_info_details)
                .setMessage(content).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        if (null != okCallback) {
                            okCallback.excute(null);
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (null != cancelCallback) {
                            cancelCallback.excute(null);
                        }
                    }
                }).show();
    }

    /**
     * 弹出只有一个确定按钮的Dialog
     *
     * @param context
     * @param title
     * @param content
     */
    public static void alertDialogTipsNoDraw(Context context, String title, String content) {
        alertDialog = new AlertDialog.Builder(context).setTitle(title).setIcon(android.R.drawable.ic_menu_info_details)
                .setMessage(content).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                }).show();
    }

    /**
     * 弹出ProgressDialog
     *
     * @param context 上下文
     * @param msg     提示信息
     */
    public static void showProgress(Context context, String msg) {
        progressdialog = new ProgressDialog(context);
        progressdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialog.setMessage(msg);
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(true);
        progressdialog.show();
    }

    /**
     * 弹出横向的ProgressDialog
     */
    public static void showProgress(Context context) {
        progressdialog = new ProgressDialog(context);
        progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressdialog.setTitle("正在下载");
        progressdialog.setMessage("请稍候...");
        progressdialog.setIndeterminate(false);
        progressdialog.show();
    }

    /**
     * 关闭ProgressDialog
     */
    public static void closeProgressDialog() {
        try {
            if (progressdialog != null) {
                if (progressdialog.isShowing()) {
                    progressdialog.dismiss();
                }
            }
        } catch (Exception e) {
        }
    }


    public interface ICommonCallBack {
        void excute(Object object);
    }

}
