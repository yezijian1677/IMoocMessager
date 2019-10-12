package net.qiujuer.italker.push;

import com.igexin.sdk.PushManager;
import com.igexin.sdk.PushService;

import net.qiujuer.italker.common.app.Application;
import net.qiujuer.italker.factory.Factory;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //调用factory进行初始化
        Factory.setup();
        PushManager.getInstance().initialize(this, PushService.class);
    }
}
