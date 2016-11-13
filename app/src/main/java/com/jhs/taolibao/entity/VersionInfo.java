package com.jhs.taolibao.entity;

/**
 * Created by dds on 2016/7/18.
 *
 * @TODO  版本信息
 */
public class VersionInfo {


    /**
     * appname : 夺股奇兵
     * apkname : taolibao.apk
     * verName : 1.0.0
     * verCode : 0
     */

    private String appname;
    private String apkname;
    private String verName;
    private int verCode;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getApkname() {
        return apkname;
    }

    public void setApkname(String apkname) {
        this.apkname = apkname;
    }

    public String getVerName() {
        return verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }

    public int getVerCode() {
        return verCode;
    }

    public void setVerCode(int verCode) {
        this.verCode = verCode;
    }
}
