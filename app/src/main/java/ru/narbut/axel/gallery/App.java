package ru.narbut.axel.gallery;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import javax.inject.Inject;

import ru.narbut.axel.gallery.utils.ExceptionHandler;
import ru.narbut.axel.data.di.qualifiers.ApplicationContext;
import ru.narbut.axel.gallery.di.components.ApplicationComponent;
import ru.narbut.axel.gallery.di.components.DaggerApplicationComponent;
import ru.narbut.axel.gallery.di.modules.ApplicationModule;
import ru.narbut.axel.gallery.utils.inputMethodManagerLeakRemover.IMMLeaks;

public class App extends Application {

    private ApplicationComponent mApplicationComponent;

    @Inject
    @ApplicationContext
    Context applicationContext;


    @Override
    public void onCreate() {
        super.onCreate();
        IMMLeaks.fixFocusedViewLeak(this);
        initializeInjector();
        initializeExceptionAndLeakDetection();
    }

    private void initializeInjector() {
        mApplicationComponent = DaggerApplicationComponent
                .builder().applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
    }


    private void initializeExceptionAndLeakDetection() {
        if (BuildConfig.DEBUG) {
            Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler.inContext(getApplicationContext()));
            LeakCanary.install(this);
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    @Override
    public Context getApplicationContext() {
        return applicationContext;
    }
}
