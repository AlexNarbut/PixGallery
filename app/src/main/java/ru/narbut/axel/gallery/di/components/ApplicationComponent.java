package ru.narbut.axel.gallery.di.components;

import dagger.Component;
import ru.narbut.axel.data.di.scope.ApplicationScope;
import ru.narbut.axel.data.repository.network.manager.NetworkStateManager;
import ru.narbut.axel.domain.executor.PostExecutionThread;
import ru.narbut.axel.domain.executor.ThreadExecutor;
import ru.narbut.axel.domain.repository.NotificationRepository;
import ru.narbut.axel.domain.repository.SavedConstantsRepository;
import ru.narbut.axel.gallery.App;
import ru.narbut.axel.gallery.di.modules.ApplicationModule;
import ru.narbut.axel.gallery.di.modules.NetworkModule;
import ru.narbut.axel.gallery.di.modules.NotificationModule;
import ru.narbut.axel.gallery.di.modules.PreferenceModule;
import ru.narbut.axel.gallery.di.modules.PresenterModule;
import ru.narbut.axel.gallery.navigation.Navigator;
import ru.narbut.axel.gallery.view.about.AboutPresenter;
import ru.narbut.axel.gallery.view.hullScreenPhotoActivity.FullPhotoPresenter;
import ru.narbut.axel.gallery.view.mainActivity.MainPresenter;
import ru.narbut.axel.gallery.view.photoFragment.PhotoSearchPresenter;
import ru.narbut.axel.gallery.view.slash.SlashPresenter;


@ApplicationScope
@Component(modules = {ApplicationModule.class , NetworkModule.class,
        PresenterModule.class, PreferenceModule.class, NotificationModule.class})
public interface ApplicationComponent {

    void inject(App app);

    Navigator getNavigator();

    ThreadExecutor getThreadExecutor();

    PostExecutionThread getPostExecutionThread();

    NetworkStateManager getNetworkManager();

    SavedConstantsRepository getSavedConstantsRepository();
    NotificationRepository getNotificationRepository();

    //presenter
    AboutPresenter getAboutPresenter();
    SlashPresenter getSlashPresenter();
    MainPresenter getMainPresenter();
    PhotoSearchPresenter getPhotoSearchPresenter();
    FullPhotoPresenter getFullPhotoPresenter();
}

