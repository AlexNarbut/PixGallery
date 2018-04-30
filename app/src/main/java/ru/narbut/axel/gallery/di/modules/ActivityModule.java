package ru.narbut.axel.gallery.di.modules;


import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;
import ru.narbut.axel.data.di.qualifiers.ActivityContext;
import ru.narbut.axel.data.di.scope.ActivityScope;


@Module
public class ActivityModule {

    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @ActivityContext
    @Provides
    Context provideContext() {
        return activity;
    }

    @ActivityScope
    @Provides
    Activity provideActivity() {
        return activity;
    }


}
