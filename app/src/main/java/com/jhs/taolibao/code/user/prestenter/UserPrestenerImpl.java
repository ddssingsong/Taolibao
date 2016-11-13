package com.jhs.taolibao.code.user.prestenter;

import com.jhs.taolibao.app.BaseApplication;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.code.simtrade.SimtradeCenter;
import com.jhs.taolibao.code.user.model.PicModel;
import com.jhs.taolibao.code.user.model.PicModelImpl;
import com.jhs.taolibao.code.user.model.UserModelImpl;
import com.jhs.taolibao.code.user.view.FindPwdView;
import com.jhs.taolibao.code.user.view.LoginView;
import com.jhs.taolibao.code.user.view.ModifyPwdView;
import com.jhs.taolibao.code.user.view.RegiterView;
import com.jhs.taolibao.code.user.view.UserInfoView;
import com.jhs.taolibao.entity.UserInfo;
import com.jhs.taolibao.utils.ToastUtil;

/**
 * Created by dds on 2016/6/3.
 *
 * @TODO
 */
public class UserPrestenerImpl implements UserPrestener {
    private UserModelImpl userModel;
    private PicModel picModel;
    private LoginView loginView;
    private RegiterView regiterView;
    private FindPwdView findPwdView;
    private ModifyPwdView modifyPwdView;
    private UserInfoView userInfoView;

    public UserPrestenerImpl(LoginView loginView) {
        this.loginView = loginView;
        userModel = new UserModelImpl();
    }

    public UserPrestenerImpl(RegiterView regiterView) {
        this.regiterView = regiterView;
        userModel = new UserModelImpl();
    }

    public UserPrestenerImpl(ModifyPwdView modifyPwdView) {
        this.modifyPwdView = modifyPwdView;
        userModel = new UserModelImpl();
    }

    public UserPrestenerImpl(FindPwdView findPwdView) {
        this.findPwdView = findPwdView;
        userModel = new UserModelImpl();
    }

    public UserPrestenerImpl(UserInfoView userInfoView) {
        this.userInfoView = userInfoView;
        picModel = new PicModelImpl();
        userModel = new UserModelImpl();
    }


    //登陆
    @Override
    public void Login(String username, String password) {
        if (!isMobileNO(username) || username.length() != 11) {
            loginView.inputUsernameError();
            return;
        }
        if (password == null || password.equals("")) {
            loginView.inputPwdNull();
            return;
        }
        loginView.showProgress();
        userModel.Login(username, password, new UserModelImpl.onLoginListener() {
            @Override
            public void onSuccess(UserInfo userinfo) {

                loginView.loginSuccess();
                //保存用户信息
                UserInfoSingleton.setUserId(Integer.toString(userinfo.getId()));
                UserInfoSingleton.setUserInfo(userinfo);

                if (UserInfoSingleton.getUserInfo() != null) {
                    //Log.d("msg","开始获取令牌");
                    SimtradeCenter.getInstance().init(UserInfoSingleton.getUserInfo().getHsAccount(), UserInfoSingleton.getUserInfo().getHsPwd());
                    // Log.d("msg","获取令牌结束");
                }

                loginView.hideProgress();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                loginView.hideProgress();
                ToastUtil.showToast(BaseApplication.getApplication(), msg);

            }
        });


    }

    @Override
    public void Register(String username, String password, String code) {
        if (!isMobileNO(username) || username.length() != 11) {
            regiterView.inputUsernameError();
            return;
        }
        if (password == null || password.equals("") || password.length() < 6) {
            regiterView.inputPwdNull();
            return;
        }
        if (code == null || code.equals("")) {
            regiterView.inputCodeNull();
            return;
        }
        regiterView.showProcess();
        userModel.Register(username, password, code, new UserModelImpl.onRegisterListener() {
            @Override
            public void onSuccess() {
                regiterView.hideProcess();
                regiterView.registerSuccess();
            }

            @Override
            public void onHaveRegister(String msg) {
                regiterView.hideProcess();
                regiterView.inputUsernameHavingRegister();
            }

            @Override
            public void onCodeError() {
                regiterView.hideProcess();
                regiterView.errorCode();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                regiterView.hideProcess();
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });

    }

    @Override
    public void getCode(boolean isRegist, String mobile) {
        if (!isMobileNO(mobile) || mobile.length() != 11) {
            regiterView.inputUsernameError();
            return;
        }
        regiterView.sendMsgToServer();
        userModel.getCode(isRegist, mobile, new UserModelImpl.onGetCodeListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast(BaseApplication.getApplication(), "验证码已发送！");
                regiterView.getCodeSuccess();
            }

            @Override
            public void onHaveRegister(String msg) {
                regiterView.inputUsernameHavingRegister();
                regiterView.clearTimer();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });

    }

    @Override
    public void getCode1(boolean isRegist, String mobile) {
        if (!isMobileNO(mobile) || mobile.length() != 11) {
            findPwdView.inputUsernameError();
            return;
        }
        findPwdView.sendMsgToServer();
        userModel.getCode(isRegist, mobile, new UserModelImpl.onGetCodeListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast(BaseApplication.getApplication(), "验证码已发送！");
                findPwdView.getCodeSuccess();
            }

            @Override
            public void onHaveRegister(String msg) {
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }

    @Override
    public void ModifyPwd(String oldpwd, String newpwd, String newpwd2) {

        if (oldpwd.isEmpty() || newpwd.isEmpty() || newpwd2.isEmpty()) {
            modifyPwdView.inputPwdError();
            return;
        }
        if (!newpwd.equals(newpwd2)) {
            modifyPwdView.inputPwdError();
            return;
        }
        modifyPwdView.showProcess();
        userModel.ModifyPwd(oldpwd, newpwd, new UserModelImpl.onModifyListener() {
            @Override
            public void onSuccess() {
                modifyPwdView.hideProcess();
                modifyPwdView.modifyPwdSuccess();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                modifyPwdView.hideProcess();
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });


    }

    @Override
    public void FindPwd(String username, String newpwd, String code) {
        if (!isMobileNO(username) || username.length() != 11) {
            findPwdView.inputUsernameError();
            return;
        }
        if (newpwd == null || newpwd.equals("")) {
            findPwdView.inputPwdNull();
            return;
        }
        if (code == null || code.equals("")) {
            findPwdView.inputCodeNull();
            return;
        }
        findPwdView.showProcess();
        userModel.FindPwd(username, newpwd, code, new UserModelImpl.onFindPwdListener() {
            @Override
            public void onSuccess() {
                findPwdView.hideProcess();
                ToastUtil.showToast(BaseApplication.getApplication(), "密码找回成功");
                findPwdView.FindPwdSuccess();
            }

            @Override
            public void onCodeError() {
                findPwdView.hideProcess();
                findPwdView.errorCode();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                findPwdView.hideProcess();
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }

    @Override
    public void GetuserInfo() {
        if (UserInfoSingleton.getUserInfo() != null) {
            String userid = UserInfoSingleton.getUserId();
            userModel.GetuserInfo(userid, new UserModelImpl.onGetUserInfoListener() {
                @Override
                public void onSuccess(UserInfo userinfo) {
                    userInfoView.getUserInforSucess(userinfo);
                }

                @Override
                public void onFailure(String msg, Exception e) {

                }
            });
        }

    }

    //上传头像
    @Override
    public void uploadImage(String photo) {
        picModel.uploadimage(photo, new PicModelImpl.onPicListener() {
            @Override
            public void onSuccess() {
                userInfoView.uploadImageSuccess();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);

            }
        });

    }

    //修改用户信息
    @Override
    public void updateUserInfo(String userJson) {
        userModel.updateUserInfo(userJson, new UserModelImpl.onUpdateUserListener() {
            @Override
            public void onSuccess() {
                userInfoView.modifyUserInfoSucess();
            }

            @Override
            public void onFailure(String msg, Exception e) {
                ToastUtil.showToast(BaseApplication.getApplication(), msg);
            }
        });
    }


    public boolean isMobileNO(String mobiles) {
        if (mobiles.startsWith("13")) {
            return true;
        }
        if (mobiles.startsWith("14")) {
            return true;
        }
        if (mobiles.startsWith("15")) {
            return true;
        }
        if (mobiles.startsWith("17")) {
            return true;
        }
        if (mobiles.startsWith("18")) {
            return true;
        }
        return false;
    }
}
