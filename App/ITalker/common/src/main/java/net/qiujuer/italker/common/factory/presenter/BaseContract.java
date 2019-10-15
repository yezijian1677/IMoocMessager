package net.qiujuer.italker.common.factory.presenter;

import android.support.annotation.StringRes;

import net.qiujuer.italker.common.widget.recycler.RecyclerAdapter;

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

    public interface RecyclerView<T extends Presenter, ViewModel> extends View<T> {
        //界面端只能刷新整个数据集合，不能精确到某一条数据更新
        //拿到一个适配器，然后自主的进行刷新
        RecyclerAdapter<ViewModel> getRecyclerAdapter();

        //当数据更改了的时候触发
        void onAdapterDataChanged();
    }
}
