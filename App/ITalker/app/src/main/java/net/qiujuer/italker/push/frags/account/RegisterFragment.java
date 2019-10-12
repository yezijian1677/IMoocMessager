package net.qiujuer.italker.push.frags.account;

import android.content.Context;
import android.widget.EditText;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.factory.presenter.account.RegisterContract;
import net.qiujuer.italker.factory.presenter.account.RegisterPresenter;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.frags.activities.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter> implements RegisterContract.View {
    private AccountTrigger mAccountTrigger;


    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_name)
    EditText mName;
    @BindView(R.id.edit_password)
    EditText mPassword;

    @BindView(R.id.loading)
    Loading mLoading;

    @BindView(R.id.btn_submit)
    Button mSubmit;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_regitster;
    }

    @Override
    public void registerSuccess() {
        //注册成功，账户已经登陆，进行跳转到mainActivity
        MainActivity.show(getContext());
        getActivity().finish();
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String phone = mPhone.getText().toString();
        String name = mName.getText().toString();
        String password = mPassword.getText().toString();

        //调用P层进行注册
        mPresenter.register(phone, name, password);
    }

    @OnClick(R.id.text_go_login)
    void onShowLoginClick() {
        mAccountTrigger.triggerView();
    }

    @Override
    public void showError(int str) {
        super.showError(str);
        //发生错误
        mLoading.stop();
        mPhone.setEnabled(true);
        mName.setEnabled(true);
        mPassword.setEnabled(true);

        mSubmit.setEnabled(true);

    }

    @Override
    public void showLoading() {
        super.showLoading();

        //提交申请
        mLoading.start();
        mPhone.setEnabled(false);
        mName.setEnabled(false);
        mPassword.setEnabled(false);

        mSubmit.setEnabled(false);
    }
}
