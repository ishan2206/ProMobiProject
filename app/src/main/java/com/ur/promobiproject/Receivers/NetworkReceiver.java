package com.ur.promobiproject.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.ur.promobiproject.Events.NetworkEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by Ishan on 08-07-2018.
 */


public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkEvent networkEvent =  new NetworkEvent();
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                networkEvent.setNetworkConnected(true);
                EventBus.getDefault().post(networkEvent);

            } else if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) {
                   networkEvent.setNetworkConnected(false);
                    EventBus.getDefault().post(networkEvent);
            }
        }
    }
}