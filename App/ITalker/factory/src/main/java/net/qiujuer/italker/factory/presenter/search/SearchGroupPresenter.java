package net.qiujuer.italker.factory.presenter.search;

import net.qiujuer.italker.common.factory.presenter.BasePresenter;

public class SearchGroupPresenter extends BasePresenter<SearchContract.GroupView> implements SearchContract.Presenter {

    public SearchGroupPresenter(SearchContract.GroupView view) {
        super(view);
    }

    @Override
    public void search(String content) {

    }
}
