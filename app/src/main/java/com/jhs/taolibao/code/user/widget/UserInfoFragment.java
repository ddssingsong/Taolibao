package com.jhs.taolibao.code.user.widget;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jhs.taolibao.R;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.base.BaseActivity;
import com.jhs.taolibao.base.BaseFragment;
import com.jhs.taolibao.code.user.prestenter.UserPrestenerImpl;
import com.jhs.taolibao.code.user.view.UserInfoView;
import com.jhs.taolibao.entity.UserInfo;
import com.jhs.taolibao.utils.AssetsUtils;
import com.jhs.taolibao.utils.BitmapUtil;
import com.jhs.taolibao.utils.CalendarUtil;
import com.jhs.taolibao.utils.DateUtil;
import com.jhs.taolibao.utils.JsonUtils;
import com.jhs.taolibao.utils.ToastUtil;
import com.jhs.taolibao.view.SelectPicPopupWindow;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;

import com.jhs.taolibao.view.framework.picker.AddressPicker;
import com.jhs.taolibao.view.framework.picker.DatePicker;
import com.jhs.taolibao.view.framework.picker.OptionPicker;
import com.jhs.taolibao.view.framework.picker.SexPicker;

/**
 * Created by dds on 2016/6/27.
 *
 * @TODO
 */
public class UserInfoFragment extends BaseFragment implements View.OnClickListener, UserInfoView {

    private com.jhs.taolibao.view.TitleBar titlebar;//标题栏
    private RelativeLayout layout_edit_icon;//头像
    private com.jhs.taolibao.view.CircleImageView iv_edit_icon;//头像图标
    private RelativeLayout layout_edit_alias;//昵称
    private TextView tv_edit_alias;
    private RelativeLayout layout_edit_sign;//签名
    private TextView tv_edit_sign;
    private RelativeLayout layout_edit_gender;//性别
    private TextView tv_edit_gender;
    private RelativeLayout layout_edit_birth;//生日
    private TextView tv_edit_birth;
    private RelativeLayout layout_edit_city;//城市
    private TextView tv_edit_city;

    SelectPicPopupWindow menuWindow;
    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo_taolibao.jpg";
    private File tempFile;
    private Bitmap bitmap;
    private UserPrestenerImpl userPrestener;
    private static final int MY_PERMISSIONS_REQUEST_TAKE_PHOTO = 15;

    @Override
    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPrestener = new UserPrestenerImpl(this);
    }

    @Override
    public View onInitloadView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userifo, container, false);
        return view;
    }

    @Override
    protected void initView(View rootView) {
        layout_edit_icon = (RelativeLayout) rootView.findViewById(R.id.layout_edit_icon);
        iv_edit_icon = (com.jhs.taolibao.view.CircleImageView) rootView.findViewById(R.id.iv_edit_icon);
        layout_edit_alias = (RelativeLayout) rootView.findViewById(R.id.layout_edit_alias);
        tv_edit_alias = (TextView) rootView.findViewById(R.id.tv_edit_alias);
        layout_edit_sign = (RelativeLayout) rootView.findViewById(R.id.layout_edit_sign);
        tv_edit_sign = (TextView) rootView.findViewById(R.id.tv_edit_sign);
        layout_edit_gender = (RelativeLayout) rootView.findViewById(R.id.layout_edit_gender);
        tv_edit_gender = (TextView) rootView.findViewById(R.id.tv_edit_gender);
        layout_edit_birth = (RelativeLayout) rootView.findViewById(R.id.layout_edit_birth);
        tv_edit_birth = (TextView) rootView.findViewById(R.id.tv_edit_birth);
        layout_edit_city = (RelativeLayout) rootView.findViewById(R.id.layout_edit_city);
        tv_edit_city = (TextView) rootView.findViewById(R.id.tv_edit_city);
        layout_edit_icon.setOnClickListener(this);
        layout_edit_alias.setOnClickListener(this);
        layout_edit_sign.setOnClickListener(this);
        layout_edit_gender.setOnClickListener(this);
        layout_edit_birth.setOnClickListener(this);
        layout_edit_city.setOnClickListener(this);
        titlebar = (com.jhs.taolibao.view.TitleBar) rootView.findViewById(R.id.titlebar);
        titlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackToActivity(1, null);
            }
        });
    }

    @Override
    public void onInitloadData() {
        //初始化数据
        UserInfo userInfo = UserInfoSingleton.getUserInfo();
        if (userInfo != null) {
            tv_edit_alias.setText((userInfo.getAlias() == null || userInfo.getAlias().equals("")) ? "添加" : userInfo.getAlias());
            tv_edit_sign.setText((userInfo.getSign().equals("") || userInfo.getSign() == null) ? "这个人什么都没留下" : userInfo.getSign());
            tv_edit_gender.setText((userInfo.getSexStr().equals("") || userInfo.getSexStr() == null) ? "未知" : userInfo.getSexStr());
            tv_edit_birth.setText(userInfo.getBirthday() == null || (userInfo.getBirthday().equals("")) ? "添加" : DateUtil.timeStamp2Date(userInfo.getBirthday(), "yyyy-MM-dd"));
            tv_edit_city.setText((userInfo.getCity().equals("") || userInfo.getCity() == null) ? "添加" : userInfo.getCity());
            ImageLoader.getInstance().displayImage(WebBiz.UPLOAD_PREFIX + userInfo.getIcon(), iv_edit_icon, BaseActivity.options1);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_edit_icon:
                //修改头像
                handleModifyPicture(v);
                break;
            case R.id.layout_edit_alias:
                //修改昵称
                callbackToActivity(2, null);
                break;
            case R.id.layout_edit_sign:
                //修改签名
                callbackToActivity(3, null);
                break;
            case R.id.layout_edit_gender:
                //修改性别
                handlerModifyGende();
                break;
            case R.id.layout_edit_birth:
                ///修改生日
                handleModifyBirth();
                break;
            case R.id.layout_edit_city:
                //修改城市
                handleModifyCity();
                break;
        }
    }

    //修改城市
    private void handleModifyCity() {
        new AddressInitTask(getActivity(), true).execute("北京", "北京");


    }

    //生日
    private void handleModifyBirth() {
        DatePicker picker = new DatePicker(getActivity());
        picker.setRange(1900, CalendarUtil.getYear());
        final UserInfo userInfo1 = UserInfoSingleton.getUserInfo();
        if (null != userInfo1) {
            String birth = userInfo1.getBirthday();
            if (birth == null || birth.equals("")) {
                picker.setSelectedItem(CalendarUtil.getYear(), 1, 1);
            } else {
                picker.setSelectedItem(CalendarUtil.getYear(), 1, 1);
            }

            picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    if (null != year && null != month && null != day) {
                        String birth = year + "-" + month + "-" + day;
                        userInfo1.setBirthday(birth);
                        String userJson = JsonUtils.serialize(userInfo1);
                        userPrestener.updateUserInfo(userJson);
                    }
                }
            });
            picker.show();
        }
    }


    //修改性别
    private void handlerModifyGende() {
        SexPicker picker = new SexPicker(getActivity());
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option) {
                String gender = option;
                UserInfo userInfo = UserInfoSingleton.getUserInfo();
                if (gender.equals("女")) {
                    userInfo.setSex(0);
                    if (userInfo.getBirthday() != null && !userInfo.getBirthday().equals("")) {
                        String str = DateUtil.timeStamp2Date(userInfo.getBirthday(), "yyyy-MM-dd");
                        userInfo.setBirthday(DateUtil.timeStamp2Date(userInfo.getBirthday(), "yyyy-MM-dd"));
                    }
                } else {
                    userInfo.setSex(1);
                    if (userInfo.getBirthday() != null && !userInfo.getBirthday().equals("")) {
                        userInfo.setBirthday(DateUtil.timeStamp2Date(userInfo.getBirthday(), "yyyy-MM-dd"));
                    }
                }
                String userJson = JsonUtils.serialize(userInfo);
                userPrestener.updateUserInfo(userJson);
            }
        });
        picker.show();

    }

    //修改头像
    private void handleModifyPicture(View v) {
        menuWindow = new SelectPicPopupWindow(getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuWindow.dismiss();
                switch (v.getId()) {
                    case R.id.btn_take_photo:
                        //拍照

                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_TAKE_PHOTO);
                        } else {
                            takephoto();
                        }

                        break;
                    case R.id.btn_pick_photo:
                        //从相册中选择
                        //打开相册
                        // 激活系统图库，选择一张图片
                        Intent intent1 = new Intent(Intent.ACTION_PICK);
                        intent1.setType("image/*");
                        startActivityForResult(intent1, 2);
                        break;
                    default:
                        break;
                }
            }
        });
        //显示窗口
        menuWindow.showAtLocation(v.getRootView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    //拍照
    private void takephoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (BitmapUtil.isCanUseSD()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), PHOTO_FILE_NAME)));
        }
        startActivityForResult(intent, 1);
    }


    //获取到图片之后进行操作
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (BitmapUtil.isCanUseSD()) {
                if (resultCode == -1) {
                    tempFile = new File(Environment.getExternalStorageDirectory(),
                            PHOTO_FILE_NAME);
                    crop(Uri.fromFile(tempFile));
                }


            } else {
                ToastUtil.showToast(getActivity(), "未找到存储卡，无法存储照片！");
            }

        }
        if (requestCode == 2) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }
        }
        if (requestCode == 3) {
            try {
                bitmap = data.getParcelableExtra("data");
                iv_edit_icon.setImageBitmap(bitmap);
                //这里将剪切好的图片保存到本地并上传到服务器
                saveBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    //权限开启回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_TAKE_PHOTO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takephoto();
            } else {
                // Permission Denied
                Toast.makeText(getActivity(), "您未开启拍照权限", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //剪切图片
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, 3);
    }

    //上传图片
    public void saveBitmap(Bitmap image) {
        byte[] buffer = BitmapUtil.bitmap2Bytes(image);
        byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
        String photo = new String(encode);
        Log.i("msg", photo);
        //存进本地之后在上传到网络
        userPrestener.uploadImage(photo);

    }

    //上传图片成功
    @Override
    public void uploadImageSuccess() {
        ToastUtil.showToast(getActivity(), "上传成功");
        getUserInfo();
    }


    //显示进度条
    @Override
    public void showProcess() {

    }

    //隐藏进度条
    @Override
    public void hideProcess() {

    }

    //获取用户信息
    public void getUserInfo() {
        userPrestener.GetuserInfo();
    }

    //获取用户信息成功
    @Override
    public void getUserInforSucess(UserInfo userInfo) {
        if (userInfo != null) {
            tv_edit_alias.setText((userInfo.getAlias() == null || userInfo.getAlias().equals("")) ? "添加" : userInfo.getAlias());
            tv_edit_sign.setText((userInfo.getSign().equals("") || userInfo.getSign() == null) ? "这个人什么都没留下" : userInfo.getSign());
            tv_edit_gender.setText((userInfo.getSexStr().equals("") || userInfo.getSexStr() == null) ? "未知" : userInfo.getSexStr());
            tv_edit_birth.setText(userInfo.getBirthday() == null || (userInfo.getBirthday().equals("")) ? "添加" : DateUtil.timeStamp2Date(userInfo.getBirthday(), "yyyy-MM-dd"));
            tv_edit_city.setText((userInfo.getCity().equals("") || userInfo.getCity() == null) ? "添加" : userInfo.getCity());
            ImageLoader.getInstance().clearDiskCache();
            ImageLoader.getInstance().clearMemoryCache();
            String url = WebBiz.UPLOAD_PREFIX + userInfo.getIcon();
            ImageLoader.getInstance().displayImage(url, iv_edit_icon, BaseActivity.options1);
        }
        UserInfoSingleton.setUserInfo(userInfo);


    }

    //修改信息成功
    @Override
    public void modifyUserInfoSucess() {
        getUserInfo();
    }


    // 获取地址数据并显示地址选择器

    public class AddressInitTask extends AsyncTask<String, Void, ArrayList<AddressPicker.Province>> {
        private Activity activity;
        private ProgressDialog dialog;
        private String selectedProvince = "", selectedCity = "", selectedCounty = "";
        private boolean hideCounty = false;

        /**
         * 初始化为不显示区县的模式
         *
         * @param activity
         * @param hideCounty is hide County
         */
        public AddressInitTask(Activity activity, boolean hideCounty) {
            this.activity = activity;
            this.hideCounty = hideCounty;
            dialog = ProgressDialog.show(activity, null, "正在初始化数据...", true, true);
        }

        public AddressInitTask(Activity activity) {
            this.activity = activity;
            dialog = ProgressDialog.show(activity, null, "正在初始化数据...", true, true);
        }

        @Override
        protected ArrayList<AddressPicker.Province> doInBackground(String... params) {
            if (params != null) {
                switch (params.length) {
                    case 1:
                        selectedProvince = params[0];
                        break;
                    case 2:
                        selectedProvince = params[0];
                        selectedCity = params[1];
                        break;
                    case 3:
                        selectedProvince = params[0];
                        selectedCity = params[1];
                        selectedCounty = params[2];
                        break;
                    default:
                        break;
                }
            }
            ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
            try {
                String json = AssetsUtils.readText(activity, "city.json");
                data.addAll(JSON.parseArray(json, AddressPicker.Province.class));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<AddressPicker.Province> result) {
            dialog.dismiss();
            if (result.size() > 0) {
                AddressPicker picker = new AddressPicker(activity, result);
                picker.setHideCounty(hideCounty);
                picker.setSelectedItem(selectedProvince, selectedCity, selectedCounty);
                picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                    @Override
                    public void onAddressPicked(String province, String city, String county) {
                        if (county == null) {
                            UserInfo userInfo = UserInfoSingleton.getUserInfo();
                            if (userInfo != null) {
                                userInfo.setCity(city);
                                if (userInfo.getBirthday() != null && !userInfo.getBirthday().equals("")) {
                                    userInfo.setBirthday(DateUtil.timeStamp2Date(userInfo.getBirthday(), "yyyy-MM-dd"));
                                }
                            }
                            String userJson = JsonUtils.serialize(userInfo);
                            userPrestener.updateUserInfo(userJson);

                        } else {
                            Toast.makeText(activity, province + city + county, Toast.LENGTH_LONG).show();
                        }
                    }
                });
                picker.show();
            } else {
                Toast.makeText(activity, "数据初始化失败", Toast.LENGTH_SHORT).show();
            }
        }

    }


}

