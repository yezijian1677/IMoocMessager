package net.qiujuer.italker.factory.presenter.account;

import net.qiujuer.italker.common.factory.presenter.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {

    }
}
