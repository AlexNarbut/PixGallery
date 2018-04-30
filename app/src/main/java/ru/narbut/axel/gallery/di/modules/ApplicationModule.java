package ru.narbut.axel.gallery.di.modules;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import ru.narbut.axel.data.di.qualifiers.ApplicationContext;
import ru.narbut.axel.data.di.scope.ApplicationScope;
import ru.narbut.axel.data.executor.JobExecutor;
import ru.narbut.axel.domain.executor.PostExecutionThread;
import ru.narbut.axel.domain.executor.ThreadExecutor;
import ru.narbut.axel.gallery.App;
import ru.narbut.axel.gallery.UIThread;
import ru.narbut.axel.gallery.utils.Constants;


@Module
public class ApplicationModule {

    private App app;

    public ApplicationModule(App app) {
        this.app = app;
    }

    @Provides
    @ApplicationContext
    @ApplicationScope
    Context provideContext() {
        return app;
    }

    @Provides
    String providePrefFile() {
        return Constants.PREF_FILENAME;
    }


    @Provides
    @ApplicationScope
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @ApplicationScope
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

}
