package net.qiujuer.italker.factory.presenter.account;


import net.qiujuer.italker.common.factory.presenter.BaseContract;

public class RegisterContract{

    public interface View extends  BaseContract.View<Presenter> {
        //注册成功
        void registerSuccess();

    }

    public interface Presenter extends BaseContract.Presenter{
        void register(String phone, String name, String password);

        boolean checkMobile(String phone);
    }

}
