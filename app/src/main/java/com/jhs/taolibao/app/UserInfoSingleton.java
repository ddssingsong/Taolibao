package com.jhs.taolibao.app;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import com.jhs.taolibao.entity.UserInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * 用户运行时信息
 */
public class UserInfoSingleton {
	private static String userId;
	private static UserInfo userInfo;

	private static SharedPreferences spUserInfo;
	private static Editor spUserInfoEditor;
	
	public static void setSPUserInfo(){
		spUserInfo = BaseApplication.spUserInfo;
	}
	
	public static void setSPUserInfoEditor(){
		spUserInfoEditor = BaseApplication.spUserInfoEditor;
	}
	
	public static String getUserId() {
		userId = spUserInfo.getString("userId", "");
		return userId;
	}
	public static void setUserId(String userId) {
		UserInfoSingleton.userId = userId;
		spUserInfoEditor.putString("userId", userId);
		spUserInfoEditor.commit();
	}
	

	public static UserInfo getUserInfo() {
		String userInfo_base64 = spUserInfo.getString("userInfo", "");
		if(userInfo_base64.isEmpty()){
			return userInfo;
		}
		byte[] base64 = Base64.decode(userInfo_base64, Base64.DEFAULT);
		ByteArrayInputStream is = new ByteArrayInputStream(base64);
		try {
			ObjectInputStream ois = new ObjectInputStream(is);
			userInfo = (UserInfo)ois.readObject();
		} catch (Exception e) {
		}
		return userInfo;
	}
	public static void setUserInfo(UserInfo userInfo) {
		UserInfoSingleton.userInfo = userInfo;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(userInfo);
			String userInfo_base64 = new String(Base64.encode(os.toByteArray(),Base64.DEFAULT));
			spUserInfoEditor.putString("userInfo", userInfo_base64);
			spUserInfoEditor.commit();
		} catch (IOException e) {
		}
	}
}
