package net.qiujuer.italker.factory.presenter.contact;

import net.qiujuer.italker.common.factory.presenter.BasePresenter;

public class ContactPresenter extends BasePresenter<ContactContract.View>
        implements ContactContract.Presenter {
    public ContactPresenter(ContactContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        //todo 加载数据
    }
}
