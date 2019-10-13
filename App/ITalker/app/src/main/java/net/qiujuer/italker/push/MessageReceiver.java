package net.qiujuer.italker.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;

import net.qiujuer.italker.factory.Factory;
import net.qiujuer.italker.factory.data.helper.AccountHelper;
import net.qiujuer.italker.factory.persistence.Account;

/**
 * 个推消息接收器
 */
public class MessageReceiver extends BroadcastReceiver {

    private static final String TAG = MessageReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getExtras();

        //判断当前消息的意图
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_CLIENTID:
                Log.i(TAG, "GET_CLIENTID:" + bundle.toString());
                onClientInit(bundle.getString("clientid"));
                break;
            case PushConsts.GET_MSG_DATA:
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String message = new String(payload);
                    Log.i(TAG, "GET_MSG_DATA:" + message);
                    onMessageArrive(message);
                }
                break;
            default:
                Log.i(TAG, "OTHER:" + bundle.toString());
                break;
        }
    }

    /**
     * 当id初始化的石
     * @param cid 设备id
     */
    private void onClientInit(String cid) {
        Account.setPushId(cid);
        if (Account.isLogin()) {
            //账户登陆状态，进行一次绑定
            AccountHelper.bindPush(null);
        }
    }

    /**
     * 消息到达
     * @param message 新消息
     */
    private void onMessageArrive(String message) {
        Factory.dispatchPush(message);
    }
}
