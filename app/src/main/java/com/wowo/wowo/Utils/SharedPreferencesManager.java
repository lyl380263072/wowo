package com.wowo.wowo.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.wowo.wowo.Model.UserModel;

public class SharedPreferencesManager {
	public static final String SP_FILE_USER = "user";
	public static final String LOGIN = "Login";
	private static SharedPreferencesManager manager = null;
	private SharedPreferencesManager(){}
	
	public synchronized static SharedPreferencesManager getInstance(){
		if(null==manager){
			synchronized(SharedPreferencesManager.class){
				if(null==manager){
					manager = new SharedPreferencesManager();
				}
			}
		}
		return manager;
	}
	
	public String getData(Context context, String fileName, String key){
		SharedPreferences sf = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return sf.getString(key,"");
	}

	public UserModel getUserData(Context context){
		SharedPreferences sf = context.getSharedPreferences(SP_FILE_USER, context.MODE_PRIVATE);
		UserModel userModel =new UserModel();
		userModel.setUserId(sf.getString("uids",""));
		userModel.setUserPhone(sf.getString("uphone",""));
		userModel.setUserName(sf.getString("uname",""));
		userModel.setUserHead(sf.getString("uimage",""));
		userModel.setUserSex(sf.getString("usex",""));
		return userModel;
	}

	public void putData(Context context,String fileName,String key,String value){
		SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		Editor editor = sf.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public void out(Context context,String fileName){
		SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		Editor editor = sf.edit();
		editor.clear();
		editor.commit();
	}

	public void putUserData(Context context, UserModel userModel){
		SharedPreferences sf = context.getSharedPreferences(SP_FILE_USER, context.MODE_PRIVATE);
		Editor editor = sf.edit();
		editor.putString("uids",userModel.getUserId());
		editor.putString("uphone",userModel.getUserPhone());
		editor.putString("uname",userModel.getUserName());
		editor.putString("uimage",userModel.getUserHead());
		editor.putString("usex",userModel.getUserSex());
		editor.commit();
	}


	public int getIntData(Context context,String fileName,String key){
		SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		return sf.getInt(key, 0);
	}
	
	public void putBooleanData(Context context,String fileName,String key,boolean value){
		SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		Editor editor = sf.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	public boolean getBooleanData(Context context,String fileName,String key){
		SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		return sf.getBoolean(key, true);
	}

	public void putIntData(Context context,String fileName,String key,int value){
		SharedPreferences sf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		Editor editor = sf.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	public void putLoginData(Context context,String userKey,String userValue,String pwdKey,String pwdValue){
		SharedPreferences sf = context.getSharedPreferences(LOGIN, context.MODE_PRIVATE);
		Editor editor = sf.edit();
		editor.putString(userKey, userValue);
		editor.putString(pwdKey,pwdValue);
		editor.commit();
	}
}	
