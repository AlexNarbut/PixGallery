package ru.narbut.axel.data.repository.network.manager;

public interface NetworkStateManager {

    boolean isNetworkAvailable();
    void start();
    void stop();
    void add(String tag, Listener listener);
    void remove(String tag);
    interface Listener {
        void onNetworkAvailable();
    }
}
