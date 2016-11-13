package com.jhs.taolibao.code.challenge.model;

import com.jhs.taolibao.code.challenge.constant.ChallengeConstant;

/**
 * @author jiao on 2016/7/18 12:35
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:用户擂台角色bean
 */
public class UserRole {
    //0成功 1失败 -1异常
    private int code;
    //int数值 0 无角色   1，擂主2，挑战者3，观看者
    private int role;
    //异常错误信息
    private String Errinfo;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ChallengeConstant.ArenaRole getRole() {
        return ChallengeConstant.ArenaRole.values()[role];
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getErrinfo() {
        return Errinfo;
    }

    public void setErrinfo(String errinfo) {
        Errinfo = errinfo;
    }
}
