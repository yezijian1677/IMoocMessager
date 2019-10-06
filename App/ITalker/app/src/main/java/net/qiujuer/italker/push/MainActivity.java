package net.qiujuer.italker.push;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.common.widget.PortraitView;
import net.qiujuer.italker.push.frags.main.ActiveFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener {
    //APPBar 导航条
    @BindView(R.id.appbar)
    View mLayAppbar;
    //头像
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;
    //标题
    @BindView(R.id.txt_title)
    TextView mTitle;
    //容器
    @BindView(R.id.lay_container)
    FrameLayout mContainer;
    //导航键
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mNavigation.setOnNavigationItemSelectedListener(this);

        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .into(new ViewTarget<View, GlideDrawable>(mLayAppbar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        //this -> layAppbar, 加载当前的drawable
                        this.view.setBackground(resource.getCurrent());
                    }
                });

    }

    @Override
    protected void initData() {
        super.initData();
    }

    @OnClick(R.id.im_search)
    void onSearchMenuClick(){}

    @OnClick(R.id.btn_action)
    void onActionClick(){}

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.action_home) {
            menuItem.setTitle(R.string.title_home);
            ActiveFragment activeFragment = new ActiveFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.lay_container, activeFragment)
                    .commit();
        }
        mTitle.setText(menuItem.getTitle());
        return true;
    }
}
