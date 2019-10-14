package net.qiujuer.italker.factory.presenter.search;

import net.qiujuer.italker.common.factory.presenter.BasePresenter;

public class SearchUserPresenter extends BasePresenter<SearchContract.UserView> implements SearchContract.Presenter {
    public SearchUserPresenter(SearchContract.UserView view) {
        super(view);
    }

    @Override
    public void search(String content) {

    }
}
