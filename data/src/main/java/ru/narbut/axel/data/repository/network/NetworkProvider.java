package ru.narbut.axel.data.repository.network;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import lombok.Getter;
import okhttp3.Cache;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.narbut.axel.data.BuildConfig;
import ru.narbut.axel.data.di.qualifiers.ApplicationContext;
import ru.narbut.axel.data.repository.network.component.LoggingInterceptor;
import ru.narbut.axel.data.repository.network.manager.NetworkStateManager;

public class NetworkProvider {
    private Context context;
    @Getter
    private NetworkStateManager networkStateManager;

    @Inject
    public NetworkProvider(@ApplicationContext Context context, NetworkStateManager networkStateManager) {
        this.context = context;
        this.networkStateManager = networkStateManager;
    }


    public Retrofit provideRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(getFactory())
                .build();
    }

    private CallAdapter.Factory getFactory(){
        return RxJava2CallAdapterFactory.create();
    }

    private OkHttpClient getOkHttpClient(){
        OkHttpClient.Builder builder =  new OkHttpClient.Builder()
                .addNetworkInterceptor(getFileCacheInterceptor())
                .cache(getCache(context))
                .connectionSpecs(Collections.singletonList(getConnectionSpec()))
                .followSslRedirects(true)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS);
        if(BuildConfig.DEBUG)
            builder.addNetworkInterceptor(new LoggingInterceptor());
        return builder.build();
    }


    private Interceptor getFileCacheInterceptor(){
        return chain -> {
            Response originalResponse = chain.proceed(chain.request());
            if (networkStateManager.isNetworkAvailable()) {
                int maxAge = 60; // read from cache for 1 minute
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 *7; // tolerate 1 day stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        };
    }

    private ConnectionSpec getConnectionSpec(){
        return  new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .allEnabledTlsVersions()
                .allEnabledCipherSuites()
                .build();
    }


    private Cache getCache(Context context) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(context.getCacheDir(), cacheSize);
    }

}
