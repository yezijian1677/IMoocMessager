package net.qiujuer.italker.factory.data.helper;

import net.qiujuer.italker.common.factory.data.DataSource;
import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.model.api.account.RegisterModel;
import net.qiujuer.italker.factory.model.db.User;

public class AccountHeplper {

    /**
     * 传递一个注册的model进来
     * 成功与失败的接口回送
     * @param registerModel
     * @param callback
     */
    public static void register(RegisterModel registerModel, final DataSource.Callback<User> callback) {
        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                callback.onDataNotAvailable(R.string.data_rsp_error_parameters);
            }
        }.start();
    }
}
