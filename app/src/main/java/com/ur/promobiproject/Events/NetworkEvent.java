package com.ur.promobiproject.Events;

/**
 * Created by Ishan on 08-07-2018.
 */

public class NetworkEvent { // Green Robot Network event.

    private boolean isNetworkConnected;

    public boolean isNetworkConnected() {
        return isNetworkConnected;
    }

    public void setNetworkConnected(boolean networkConnected) {
        isNetworkConnected = networkConnected;
    }
}
