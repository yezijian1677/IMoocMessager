package net.qiujuer.italker.push.frags.account;

import android.content.Context;
import android.widget.EditText;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.factory.presenter.account.LoginContract;
import net.qiujuer.italker.factory.presenter.account.LoginPresenter;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.frags.activities.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends PresenterFragment<LoginContract.Presenter> implements LoginContract.View {
    private AccountTrigger mAccountTrigger;

    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_password)
    EditText mPassword;

    @BindView(R.id.loading)
    Loading mLoading;

    @BindView(R.id.btn_submit)
    Button mSubmit;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;

    }

    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void loginSuccess() {
        MainActivity.show(getContext());
        getActivity().finish();
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String phone = mPhone.getText().toString();
        String password = mPassword.getText().toString();
        //调用P层进行注册
        mPresenter.login(phone, password);
    }

    @OnClick(R.id.txt_go_register)
    void onShowRegisterClick() {
        mAccountTrigger.triggerView();
    }

    @Override
    public void showError(int str) {
        super.showError(str);
        //发生错误
        mLoading.stop();
        mPhone.setEnabled(true);
        mPassword.setEnabled(true);

        mSubmit.setEnabled(true);

    }

    @Override
    public void showLoading() {
        super.showLoading();

        mLoading.start();
        mPhone.setEnabled(false);
        mPassword.setEnabled(false);

        mSubmit.setEnabled(false);
    }
}
