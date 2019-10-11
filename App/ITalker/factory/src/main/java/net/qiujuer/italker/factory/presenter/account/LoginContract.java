package net.qiujuer.italker.factory.presenter.account;

import android.support.annotation.StringRes;

import net.qiujuer.italker.common.factory.presenter.BaseContract;

public class LoginContract {

    interface View extends BaseContract.View<Presenter>{
        //注册成功
        void loginSuccess();

    }

    interface Presenter extends BaseContract.Presenter {
        void login(String phone, String name, String password);
    }

}
