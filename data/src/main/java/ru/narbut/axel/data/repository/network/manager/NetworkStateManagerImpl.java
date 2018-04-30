package ru.narbut.axel.data.repository.network.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import ru.narbut.axel.data.di.qualifiers.ApplicationContext;
import ru.narbut.axel.data.di.scope.ApplicationScope;

@ApplicationScope
public class NetworkStateManagerImpl extends BroadcastReceiver implements NetworkStateManager {

    private Context context;

    private IntentFilter connectivityIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

    private Map<String, Listener> listeners = new HashMap<>();

    @Inject
    public NetworkStateManagerImpl(@ApplicationContext Context context) {
        this.context = context;
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void start() {
        context.registerReceiver(this, connectivityIntentFilter);
    }

    @Override
    public void stop() {
        context.unregisterReceiver(this);
    }

    @Override
    public void add(String tag, Listener listener) {
        listeners.put(tag, listener);
    }

    @Override
    public void remove(String tag) {
        listeners.remove(tag);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isNetworkAvailable()) {
            if (!isInitialStickyBroadcast()) {
                for (Listener listener : listeners.values()) {
                    if (listener != null) {
                        listener.onNetworkAvailable();
                    }
                }
            }
        }
    }
}
