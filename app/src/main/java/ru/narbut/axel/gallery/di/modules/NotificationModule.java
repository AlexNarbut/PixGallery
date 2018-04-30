package ru.narbut.axel.gallery.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.narbut.axel.data.di.scope.ApplicationScope;
import ru.narbut.axel.data.repository.notification.NotificationRepositoryImp;
import ru.narbut.axel.domain.repository.NotificationRepository;

@Module
public class NotificationModule {


    @Provides
    @ApplicationScope
    NotificationRepository provideNotificationRepository(NotificationRepositoryImp repository){
        return repository;
    }
}
