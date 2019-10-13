package net.qiujuer.italker.factory.presenter.account;

import android.text.TextUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;
import net.qiujuer.italker.common.Common;
import net.qiujuer.italker.common.factory.data.DataSource;
import net.qiujuer.italker.common.factory.presenter.BasePresenter;
import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.data.helper.AccountHelper;
import net.qiujuer.italker.factory.model.api.account.RegisterModel;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.persistence.Account;

import java.util.regex.Pattern;

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter, DataSource.Callback<User> {

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(String phone, String name, String password) {
        //启动loading
        start();
        //得到view接口
        RegisterContract.View view = getView();
        //检查参数
        if (!checkMobile(phone)) {
            //提示
            view.showError(R.string.data_account_register_invalid_parameter_mobile);
        } else if (name.length() < 2) {
            //姓名需要大于两位
            view.showError(R.string.data_account_register_invalid_parameter_name);
        } else if (password.length() < 6) {
            //密码需要大于六位
            view.showError(R.string.data_account_register_invalid_parameter_password);
        } else {
            //成功的请求
            RegisterModel model = new RegisterModel(phone, password, name, Account.getPushId());
            //进行网络请求，并设置回送接口为自己
            AccountHelper.register(model, this);
        }

    }

    /**
     * 检查手机号码是否违法
     * @param phone
     * @return
     */
    @Override
    public boolean checkMobile(String phone) {
        return !TextUtils.isEmpty(phone) && Pattern.matches(Common.Constance.REGEX_MOBILE, phone);
    }

    @Override
    public void onDataLoaded(User user) {
        //request成功回送callback
        final RegisterContract.View view = getView();
        if (view == null) {
            return;
        }
        //网络回送，强制切换回主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.registerSuccess();
            }
        });

    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        final RegisterContract.View view = getView();
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
