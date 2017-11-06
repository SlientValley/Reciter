package com.just.valley.reciter.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Valley on 2017/10/31.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (listener != null)
            listener.receive(context, intent);
    }

    private ActionListener listener;

    public void setListener(ActionListener l) {
        listener = l;
    }

    public interface ActionListener {
        void receive(Context context, Intent intent);
    }
}
