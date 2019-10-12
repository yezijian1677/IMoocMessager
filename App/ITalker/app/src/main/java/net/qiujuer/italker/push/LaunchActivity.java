package net.qiujuer.italker.push;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Property;
import android.view.View;

import net.qiujuer.genius.res.Resource;
import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.factory.persistence.Account;
import net.qiujuer.italker.push.frags.activities.MainActivity;
import net.qiujuer.italker.push.frags.assist.PermissionFragment;

public class LaunchActivity extends Activity {

    //Drawable
    private ColorDrawable mBgDrawable;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        View root = findViewById(R.id.activity_launch);
        //获取根布局颜色
        int color = UiCompat.getColor(getResources(), R.color.colorPrimary);
        //创建一个drawable
        ColorDrawable drawable = new ColorDrawable(color);
        //设置给背景
        root.setBackground(drawable);
        mBgDrawable = drawable;
    }

    @Override
    protected void initData() {
        super.initData();

        //动画开始进入到50%等待pushId获取到推送
        startAnim(0.5f, new Runnable() {
            @Override
            public void run() {
                //检查等待状态
                waitPushReceiverId();
            }
        });
    }

    /**
     * 等待对我们的pushId设好值
     */
    private void waitPushReceiverId() {
        //如果拿到了pushId
        if (!TextUtils.isEmpty(Account.getPushId())) {
            skip();
            return;
        }

        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                waitPushReceiverId();
            }
        }, 500);
    }

    /**
     * 跳转之前需要把剩下的百分之五十完成
     */
    private void skip() {

        startAnim(1f, new Runnable() {
            @Override
            public void run() {
                reallySkip();
            }
        });
    }

    /**
     * 跳转方法
     */
    private void reallySkip() {
        //权限检测，跳转
        if (PermissionFragment.haveAll(this, getSupportFragmentManager())) {
            MainActivity.show(this);
            finish();
        }
    }

    /**
     * 给背景设置一个动画，动画的结束进度
     *
     * @param endProgress 结束进度
     * @param endCallback 结束时触发
     */
    private void startAnim(float endProgress, Runnable endCallback) {
        //获取一个结束的颜色
        int finalColor = Resource.Color.WHITE;
        //运算当前进度的颜色
        ArgbEvaluator evaluator = new ArgbEvaluator();
        int endColor = (int) evaluator.evaluate(endProgress, mBgDrawable.getColor(), finalColor);

        ValueAnimator valueAnimator = ObjectAnimator.ofObject(this, property, evaluator, endColor);
        valueAnimator.setDuration(1500);
        valueAnimator.setIntValues(mBgDrawable.getColor(), endColor);//背景颜色结束值
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                endCallback.run();
            }
        });
        valueAnimator.start();
    }

    private Property<LaunchActivity, Object> property = new Property<LaunchActivity, Object>(Object.class, "color") {
        @Override
        public Object get(LaunchActivity launchActivity) {
            return mBgDrawable.getColor();
        }

        @Override
        public void set(LaunchActivity object, Object value) {
            object.mBgDrawable.setColor((Integer) value);
        }
    };
}
