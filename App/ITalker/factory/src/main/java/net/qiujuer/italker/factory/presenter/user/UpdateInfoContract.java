package net.qiujuer.italker.factory.presenter.user;

import net.qiujuer.italker.common.factory.presenter.BaseContract;

/**
 * 更新用户的基本契约
 */
public class UpdateInfoContract {
    //更新
    public interface Presenter extends BaseContract.Presenter {
        void update(String photoFilePath, String desc, boolean isMan);
    }

    //回调成功
    public interface View extends BaseContract.View<Presenter> {
        void updateSucceed();
    }

}
