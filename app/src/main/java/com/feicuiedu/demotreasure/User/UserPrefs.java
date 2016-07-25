package com.feicuiedu.demotreasure.User;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用户仓库,使用前必须先对其进行初始化 init(),否则就报错啦！！！
 * <p/>
 * 作者：yuanchao on 2016/7/14 0014 17:16
 * 邮箱：yuanchao@feicuiedu.com
 */
public class UserPrefs {

    private final SharedPreferences preferences;

    private static UserPrefs userPrefs;

    public static void init(Context context) {
        userPrefs = new UserPrefs(context);
    }

    private static final String PREFS_NAME = "user_info";
    private static final String KEY_USERNAME="key_userName";
    private static final String KEY_USERPASSWORD="key_userPassWord";
    private static final String KEY_TOKENID = "key_tokenid";
    private static final String KEY_PHOTO = "key_photo";


    private UserPrefs(Context context) {
        preferences = context.getApplicationContext().
                getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static UserPrefs getInstance() {
        return userPrefs;
    }
    public void setKeyUsername(String username){
        preferences.edit().putString(KEY_USERNAME, username).apply();
    }

    public void setKeyUserpassword(String userpasswordy){
        preferences.edit().putString(KEY_USERPASSWORD, userpasswordy).apply();
    }
    public void setTokenid(int tokenid) {
        preferences.edit().putInt(KEY_TOKENID, tokenid).apply();
    }

    public void setPhoto(String photoUrl) {
        preferences.edit().putString(KEY_PHOTO, photoUrl).apply();
    }
    public String getKeyUsername(){
        return preferences.getString(KEY_USERNAME, null);
    }
    public String getKeyUserpassword(){
        return preferences.getString(KEY_USERPASSWORD,null);
    }
    public int getTokenid() {
        return preferences.getInt(KEY_TOKENID, -1);
    }

    public String getPhoto() {
        return preferences.getString(KEY_PHOTO, null);
    }
}
