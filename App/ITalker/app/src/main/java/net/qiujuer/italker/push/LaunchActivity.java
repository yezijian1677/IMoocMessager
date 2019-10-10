package net.qiujuer.italker.push;

import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.push.frags.activities.MainActivity;
import net.qiujuer.italker.push.frags.assist.PermissionFragment;

public class LaunchActivity extends Activity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionFragment.haveAll(this, getSupportFragmentManager())) {
            MainActivity.show(this);
            finish();
        }
    }
}
