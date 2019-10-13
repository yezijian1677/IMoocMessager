package net.qiujuer.italker.factory.presenter.user;

import android.text.TextUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;
import net.qiujuer.italker.common.factory.data.DataSource;
import net.qiujuer.italker.common.factory.presenter.BasePresenter;
import net.qiujuer.italker.factory.Factory;
import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.data.helper.UserHelper;
import net.qiujuer.italker.factory.model.api.User.UserUpdateModel;
import net.qiujuer.italker.factory.model.card.UserCard;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.net.UploadHelper;

public class UserUpdateInfoPresenter extends BasePresenter<UpdateInfoContract.View> implements UpdateInfoContract.Presenter ,
        DataSource.Callback<UserCard> {

    public UserUpdateInfoPresenter(UpdateInfoContract.View view) {

        super(view);
    }

    @Override
    public void update(final String photoFilePath, final String desc, final boolean isMan) {
        start();

        final UpdateInfoContract.View view = getView();
        if (TextUtils.isEmpty(photoFilePath) || TextUtils.isEmpty(desc)) {
            view.showError(R.string.data_account_update_invalid_parameter);
        } else {
            Factory.runOnAsync(new Runnable() {
                @Override
                public void run() {
                    String url = UploadHelper.uploadPortrait(photoFilePath);
                    if (TextUtils.isEmpty(url)) {
                        //图片上传失败
                        view.showError(R.string.data_upload_error);
                    } else {
                        //构建用户model
                        UserUpdateModel model = new UserUpdateModel("", url, desc, isMan ? User.SEX_MAN : User.SEX_WOMEN);
                        //进行网络请求上传
                        UserHelper.update(model, UserUpdateInfoPresenter.this);
                    }
                }
            });

        }
    }


    @Override
    public void onDataLoaded(UserCard userCard) {
        //request成功回送callback
        final UpdateInfoContract.View view = getView();
        if (view == null) {
            return;
        }
        //网络回送，强制切换回主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.updateSucceed();
            }
        });

    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final UpdateInfoContract.View view = getView();
        if (view == null) {
            return;
        }
        //网络回送，强制切换回主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(strRes);
            }
        });
    }

}
