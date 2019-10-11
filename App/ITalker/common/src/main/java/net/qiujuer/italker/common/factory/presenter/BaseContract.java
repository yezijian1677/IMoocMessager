package net.qiujuer.italker.common.factory.presenter;

import android.support.annotation.StringRes;

public class BaseContract {
    public interface View<T extends Presenter>{

        void showError(@StringRes int str);
        //显示一个进度条
        void showLoading();

        //支持一个presenter
        void setPresenter(T presenter);

    }

    public interface Presenter {

        void start();

        void destroy();
    }
}
