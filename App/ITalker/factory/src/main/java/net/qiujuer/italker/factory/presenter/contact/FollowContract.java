package net.qiujuer.italker.factory.presenter.contact;

import net.qiujuer.italker.common.factory.presenter.BaseContract;
import net.qiujuer.italker.factory.model.card.UserCard;

public interface FollowContract {
    //任务调度者
    interface Presenter extends BaseContract.Presenter {
        void follow(String id);
    }

    interface View extends BaseContract.View<Presenter> {
        void onFollowSucceed(UserCard userCard);
    }
}
