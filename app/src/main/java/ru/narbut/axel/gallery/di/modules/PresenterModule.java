package ru.narbut.axel.gallery.di.modules;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import ru.narbut.axel.data.di.scope.ApplicationScope;
import ru.narbut.axel.gallery.view.about.AboutMvpView;
import ru.narbut.axel.gallery.view.about.AboutPresenter;
import ru.narbut.axel.gallery.view.adapter.viewModel.PhotoModel;
import ru.narbut.axel.gallery.view.hullScreenPhotoActivity.FullPhotoMvpView;
import ru.narbut.axel.gallery.view.hullScreenPhotoActivity.FullPhotoPresenter;
import ru.narbut.axel.gallery.view.mainActivity.MainMvpView;
import ru.narbut.axel.gallery.view.mainActivity.MainPresenter;
import ru.narbut.axel.gallery.view.photoFragment.PhotoSearchPresenter;
import ru.narbut.axel.gallery.view.photoFragment.PhotoSearchView;
import ru.narbut.axel.gallery.view.slash.SlashMvpView;
import ru.narbut.axel.gallery.view.slash.SlashPresenter;


@Module
public class PresenterModule {
    @ApplicationScope
    @Provides
    SlashPresenter getSlashPresenter(SlashPresenter<SlashMvpView> presenter){
        return presenter;
    }

    @ApplicationScope
    @Provides
    MainPresenter getMainPresenter(MainPresenter<MainMvpView> presenter){
        return presenter;
    }

    @ApplicationScope
    @Provides
    PhotoSearchPresenter getPhotoSearchPresenter(PhotoSearchPresenter<PhotoSearchView<List<PhotoModel>>> presenter){
        return presenter;
    }

    @ApplicationScope
    @Provides
    FullPhotoPresenter getFullPhotoPresenter(FullPhotoPresenter<FullPhotoMvpView> presenter){
        return presenter;
    }

    @ApplicationScope
    @Provides AboutPresenter getAboutPresenter(AboutPresenter<AboutMvpView> presenter){
        return presenter;
    }

}
