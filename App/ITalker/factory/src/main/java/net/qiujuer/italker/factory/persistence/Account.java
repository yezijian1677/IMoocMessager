package net.qiujuer.italker.factory.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import net.qiujuer.italker.factory.Factory;

public class Account {

    private static final String KEY_PUSH_ID = "KEY_PUSH_ID";
    private static final String KEY_IS_BIND = "KEY_IS_BIND";
    //设备推送id
    private static String pushId;
    private static boolean isBind;

    /**
     * 存储到xml文件
     */
    private static void save(Context context) {
        //获取数据持久化的sp
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        sp.edit().putString(KEY_PUSH_ID, pushId)
                .putBoolean(KEY_IS_BIND, isBind)
                .apply();
    }

    public static void setPushId(String pushId) {
        Account.pushId = pushId;
        Account.save(Factory.app());
    }

    public static String getPushId() {
        return pushId;
    }

    /**
     * 数据加载
     * @param context
     */
    public static void load(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        pushId = sp.getString(KEY_PUSH_ID, "");
        isBind = sp.getBoolean(KEY_IS_BIND, false);
    }

    /**
     * 返回当前账号是否登陆
     * @return
     */
    public static boolean isLogin() {
        return true;
    }

    public static boolean isBind() {
        return isBind;
    }

    public static void setBind(boolean isBind) {
        Account.isBind = isBind;
        Account.save(Factory.app());

    }
}
