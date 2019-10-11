package net.qiujuer.italker.push.frags.account;

import android.content.Context;

import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.push.R;

public class LoginFragment extends Fragment {
    private AccountTrigger mAccountTrigger;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAccountTrigger = (AccountTrigger) context;

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onResume() {
        super.onResume();
        //进行一次切换，默认切换到注册界面
        mAccountTrigger.triggerView();
    }
}
