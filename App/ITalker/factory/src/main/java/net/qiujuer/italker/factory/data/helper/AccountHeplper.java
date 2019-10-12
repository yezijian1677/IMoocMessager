package net.qiujuer.italker.factory.data.helper;

import net.qiujuer.italker.common.factory.data.DataSource;
import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.model.api.RspModel;
import net.qiujuer.italker.factory.model.api.account.AccountRspModel;
import net.qiujuer.italker.factory.model.api.account.RegisterModel;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.net.Network;
import net.qiujuer.italker.factory.net.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountHeplper {

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
        call.enqueue(new Callback<RspModel<AccountRspModel>>() {
            @Override
            public void onResponse(Call<RspModel<AccountRspModel>> call, Response<RspModel<AccountRspModel>> response) {
                //请求成功返回
                //从返回中得到全局model，内部是使用的json 进行解析
                RspModel<AccountRspModel> rspModel = response.body();
                if (rspModel.success()) {
                    //拿到实体
                    AccountRspModel accountRspModel = rspModel.getResult();
                    //是否绑定设备
                    if (accountRspModel.isBind()) {
                        User user = accountRspModel.getUser();
                        //进行数据库写入和缓存绑定
                        //然后返回
                        callback.onDataLoaded(user);
                    } else {
                        bindPush(callback);
                    }
                } else {
                    // 对返回的封装，code进行解析
                    //todo 解析
                }
            }

            @Override
            public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
                //失败
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    /**
     * 对设备id进行绑定的操作
     *
     * @param callback
     */
    public static void bindPush(final DataSource.Callback<User> callback) {
        //TODO 先跑出一个app名字错误
        callback.onDataNotAvailable(R.string.app_name);
    }
}
