package net.qiujuer.italker.common.factory.data;

import android.support.annotation.StringRes;

public interface DataSource {


    interface Callback<T> extends SucceedCallBack<T>, FailedCallback {
    }
    /**
     *
     * 成功回调
     * @param <T>
     */
    interface SucceedCallBack<T>{
        void onDataLoaded(T t);
    }

    /**
     * 失败回调
     */
    interface FailedCallback{
        void onDataNotAvailable(@StringRes int strRes);
    }
}
