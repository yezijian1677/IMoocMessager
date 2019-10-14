package net.qiujuer.italker.factory.presenter.search;

import net.qiujuer.italker.common.factory.presenter.BaseContract;
import net.qiujuer.italker.factory.model.card.GroupCard;
import net.qiujuer.italker.factory.model.card.UserCard;

import java.util.List;

public interface SearchContract {
    interface Presenter extends BaseContract.Presenter {
        void search(String content);
    }

    //搜索人界面
    interface UserView extends BaseContract.View<Presenter> {
        void onSearchDone(List<UserCard> userCards);
    }

    //搜索群的界面
    interface GroupView extends BaseContract.View<Presenter> {
        void onSearchDone(List<GroupCard> groupCards);
    }

}
