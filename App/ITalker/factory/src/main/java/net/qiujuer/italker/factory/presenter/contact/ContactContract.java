package net.qiujuer.italker.factory.presenter.contact;

import net.qiujuer.italker.common.factory.presenter.BaseContract;
import net.qiujuer.italker.factory.model.db.User;

public interface ContactContract {

    //什么都不需要额外定义
    interface Presenter extends BaseContract.Presenter {
    }

    //都在基类完成
    interface View extends BaseContract.RecyclerView<Presenter, User> {

    }



}
