package net.qiujuer.italker.factory.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.qiujuer.italker.factory.Factory;
import net.qiujuer.italker.factory.model.api.account.AccountRspModel;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.model.db.User_Table;

public class Account {

    private static final String KEY_PUSH_ID = "KEY_PUSH_ID";
    private static final String KEY_IS_BIND = "KEY_IS_BIND";

    private static final String KEY_TOKEN = "KEY_TOKEN";
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";
    //设备推送id
    private static String pushId;
    private static boolean isBind;

    //用户信息
    private static String token;
    private static String userId;
    private static String account;

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
        return !TextUtils.isEmpty(userId) && !TextUtils.isEmpty(token);
    }

    /**
     * 用户信息是否完善
     * @return
     */
    public static boolean isComplete() {
        return isLogin();
    }

    public static boolean isBind() {
        return isBind;
    }

    public static void setBind(boolean isBind) {
        Account.isBind = isBind;
        Account.save(Factory.app());

    }

    /**
     * 持久化信息到xml中
     */
    public static void login(AccountRspModel model) {
        Account.token = model.getToken();
        Account.account = model.getAccount();
        Account.userId = model.getUser().getId();
        save(Factory.app());
    }

    /**
     * 本地获取用户信息
     * @return
     */
    public static User getUser() {
        return TextUtils.isEmpty(userId) ? new User() : SQLite.select()
                .from(User.class)
                .where(User_Table.id.eq(userId))
                .querySingle();
    }
}
