package com.jhs.taolibao.code.my.widget;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.my.prestenter.SoftUpdatePresenterImpl;
import com.jhs.taolibao.code.my.view.UpdateView;
import com.jhs.taolibao.entity.VersionInfo;
import com.jhs.taolibao.service.UpdateService;
import com.jhs.taolibao.utils.DataCleanManager;
import com.jhs.taolibao.utils.DialogUtil;
import com.jhs.taolibao.utils.ToastUtil;
import com.jhs.taolibao.view.TitleBar;

/**
 * Created by dds on 2016/6/28.
 *
 * @TODO
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener, UpdateView {
    private TitleBar titleBar;
    private TextView tv_settig_checkv;
    private TextView tv_settig_clear;

    private RelativeLayout layout_setting_modifypwd;
    private RelativeLayout layout_setting_clear;
    private RelativeLayout layout_setting_checkv;
    private SoftUpdatePresenterImpl softUpdatePresenter;
    private RelativeLayout layout_exit;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    onInitloadData();
                    ToastUtil.showToast(getActivity(), "清除完成");
                    layout_setting_clear.setClickable(true);
                    break;
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        softUpdatePresenter = new SoftUpdatePresenterImpl(this);
    }

    @Override
    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        layout_setting_modifypwd = (RelativeLayout) rootView.findViewById(R.id.layout_setting_modifypwd);
        layout_setting_clear = (RelativeLayout) rootView.findViewById(R.id.layout_setting_clear);
        layout_setting_checkv = (RelativeLayout) rootView.findViewById(R.id.layout_setting_checkv);
        titleBar = (TitleBar) rootView.findViewById(R.id.titlebar);
        layout_exit = (RelativeLayout) rootView.findViewById(R.id.layout_exit);
        tv_settig_checkv = (TextView) rootView.findViewById(R.id.tv_settig_checkv);
        tv_settig_clear = (TextView) rootView.findViewById(R.id.tv_settig_clear);
        layout_exit.setOnClickListener(this);
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackToActivity(1, null);
            }
        });

        layout_setting_modifypwd.setOnClickListener(this);
        layout_setting_clear.setOnClickListener(this);
        layout_setting_checkv.setOnClickListener(this);

    }

    @Override
    public void onInitloadData() {
        if (UserInfoSingleton.getUserInfo() == null) {
            layout_setting_modifypwd.setVisibility(View.GONE);
            layout_exit.setVisibility(View.GONE);
        } else {
            layout_setting_modifypwd.setVisibility(View.VISIBLE);
            layout_exit.setVisibility(View.VISIBLE);
        }
        //设置版本号
        tv_settig_checkv.setText("V" + BaseApplication.getVersion());
        //查询缓存大小
        try {
            String cache = DataCleanManager.getTotalCacheSize(BaseApplication.getApplication());
            tv_settig_clear.setText(cache);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_exit:
                //退出登录
                DialogUtil.alertDialogTips(getActivity(), "提示", "您确定要退出吗？", new DialogUtil.ICommonCallBack() {
                    @Override
                    public void excute(Object object) {
                        callbackToActivity(2, null);
                    }
                }, null);

                break;
            case R.id.layout_setting_modifypwd:
                //修改密码
                callbackToActivity(3, null);
                break;
            case R.id.layout_setting_clear:
                //清除缓存
                layout_setting_clear.setClickable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DataCleanManager.clearAllCache(BaseApplication.getApplication());
                        handler.sendEmptyMessage(1);
                    }
                }).start();

                break;
            case R.id.layout_setting_checkv:
                //检查版本
                handleCheckV();
                break;
        }

    }

    //检查最新版本
    private void handleCheckV() {
        softUpdatePresenter.getVersionCode();
    }

    @Override
    public void needUpdate(VersionInfo versionInfo) {
        String str = "当前版本：" + versionInfo.getAppname() + " Code:" + BaseApplication.getVersion() + " ,发现新版本：" + versionInfo.getAppname() +
                " Code:" + versionInfo.getVerName() + " ,是否更新？";
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("软件更新").setMessage(str)
                // 设置内容
                .setPositiveButton("更新",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //启动服务
                                getActivity().startService(new Intent(getActivity(), UpdateService.class));

                            }
                        })
                .setNegativeButton("暂不更新",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 点击"取消"按钮之后退出程序
                                dialog.dismiss();
                            }
                        }).create();// 创建
        // 显示对话框
        dialog.show();


    }

    @Override
    public void dontneedUpdate() {
        ToastUtil.showToast(getActivity(), "已经是最新版本");
    }


}
