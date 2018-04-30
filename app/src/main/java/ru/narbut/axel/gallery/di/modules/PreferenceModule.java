package ru.narbut.axel.gallery.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.narbut.axel.data.di.qualifiers.PrefFile;
import ru.narbut.axel.data.di.scope.ApplicationScope;
import ru.narbut.axel.data.repository.pref.AppPreferencesRepository;
import ru.narbut.axel.domain.repository.SavedConstantsRepository;
import ru.narbut.axel.gallery.utils.Constants;


@Module
public class PreferenceModule {

    @Provides
    @PrefFile
    @ApplicationScope
    String getPrefName(){
        return Constants.PREF_FILENAME;
    }

    @Provides
    @ApplicationScope
    SavedConstantsRepository provideSavedConstantsRepository(AppPreferencesRepository repository){
        return repository;
    }
}
