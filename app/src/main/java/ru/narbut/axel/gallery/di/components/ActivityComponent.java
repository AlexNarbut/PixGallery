package ru.narbut.axel.gallery.di.components;

import dagger.Component;
import ru.narbut.axel.data.di.scope.ActivityScope;
import ru.narbut.axel.gallery.di.modules.ActivityModule;
import ru.narbut.axel.gallery.view.about.AboutActivity;
import ru.narbut.axel.gallery.view.hullScreenPhotoActivity.FullScreenPhotoActivity;
import ru.narbut.axel.gallery.view.mainActivity.MainActivity;
import ru.narbut.axel.gallery.view.photoFragment.PhotoSearchFragment;
import ru.narbut.axel.gallery.view.slash.SlashActivity;


@ActivityScope
@Component(modules = {ActivityModule.class,},
        dependencies = {ApplicationComponent.class})
public interface ActivityComponent {
    void inject(SlashActivity activity);
    void inject(AboutActivity activity);
    void inject(MainActivity activity);
    void inject(FullScreenPhotoActivity activity);
    void inject(PhotoSearchFragment fragment);
}
