package net.qiujuer.italker.factory.data.helper;

import android.text.TextUtils;

import net.qiujuer.italker.common.factory.data.DataSource;
import net.qiujuer.italker.factory.Factory;
import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.model.api.RspModel;
import net.qiujuer.italker.factory.model.api.account.AccountRspModel;
import net.qiujuer.italker.factory.model.api.account.LoginModel;
import net.qiujuer.italker.factory.model.api.account.RegisterModel;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.net.Network;
import net.qiujuer.italker.factory.net.RemoteService;
import net.qiujuer.italker.factory.persistence.Account;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountHelper {

    /**
     * 传递一个注册的model进来
     * 成功与失败的接口回送
     *
     * @param registerModel
     * @param callback
     */
    public static void register(RegisterModel registerModel, final DataSource.Callback<User> callback) {
        //调用retrofit对网络请求接口做代理
        RemoteService service = Network.getRetrofit().create(RemoteService.class);
        //得到一个call
        Call<RspModel<AccountRspModel>> call = service.accountRegister(registerModel);
        //异步请求
        call.enqueue(new AccountRspCallback(callback));
    }

    /**
     * 登陆调用
     * @param loginModel 登录model
     * @param callback rsp
     */
    public static void login(LoginModel loginModel, final DataSource.Callback<User> callback) {
        //调用retrofit对网络请求接口做代理
        RemoteService service = Network.getRetrofit().create(RemoteService.class);
        //得到一个call
        Call<RspModel<AccountRspModel>> call = service.accountLogin(loginModel);
        //异步请求
        call.enqueue(new AccountRspCallback(callback));
    }

    /**
     * 对设备id进行绑定的操作
     *
     * @param callback callback
     */
    public static void bindPush(final DataSource.Callback<User> callback) {
        String pushId = Account.getPushId();
        if (TextUtils.isEmpty(pushId)) return;
        RemoteService service = Network.remote();
        Call<RspModel<AccountRspModel>> call = service.accountBind(pushId);
        call.enqueue(new AccountRspCallback(callback));
    }

    private static class AccountRspCallback implements Callback<RspModel<AccountRspModel>> {
        final DataSource.Callback<User> callback;

        AccountRspCallback(DataSource.Callback<User> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<AccountRspModel>> call, Response<RspModel<AccountRspModel>> response) {
            //请求成功返回
            //从返回中得到全局model，内部是使用的json 进行解析
            RspModel<AccountRspModel> rspModel = response.body();
            if (rspModel.success()) {
                //拿到实体
                //进行数据库写入和缓存绑定
                AccountRspModel accountRspModel = rspModel.getResult();
                User user = accountRspModel.getUser();
                user.save();
                Account.login(accountRspModel);
                //是否绑定设备
                if (accountRspModel.isBind()) {
                    //然后返回
                    if (callback!=null)
                        callback.onDataLoaded(user);
                } else {
                    bindPush(callback);
                }
            } else {
                // 对返回的封装，code进行解析
                Factory.decodeRspCode(rspModel, callback);
            }
        }

        @Override
        public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
            //失败
            if (callback!=null)
                callback.onDataNotAvailable(R.string.data_network_error);
        }
    }

}
