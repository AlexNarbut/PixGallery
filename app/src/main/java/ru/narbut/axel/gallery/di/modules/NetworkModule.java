package ru.narbut.axel.gallery.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.narbut.axel.data.di.scope.ApplicationScope;
import ru.narbut.axel.data.repository.network.PhotoRepositoryImp;
import ru.narbut.axel.data.repository.network.manager.NetworkStateManager;
import ru.narbut.axel.data.repository.network.manager.NetworkStateManagerImpl;
import ru.narbut.axel.domain.repository.PhotoRepository;

@Module
public class NetworkModule {

    @ApplicationScope
    @Provides
    NetworkStateManager getNetworkManager(NetworkStateManagerImpl impl){
        return impl;
    }

    @ApplicationScope
    @Provides
    PhotoRepository getPhotoRepository(PhotoRepositoryImp repository) {
        return repository;
    }

}
