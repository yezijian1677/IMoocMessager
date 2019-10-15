package net.qiujuer.italker.push.frags.activities;

import android.content.Context;

import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.common.factory.model.Author;
import net.qiujuer.italker.push.R;

public class MessageActivity extends Activity {

    public static void show(Context context, Author author) {

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_message;
    }
}
