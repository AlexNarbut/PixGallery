package ru.narbut.axel.data.repository.network;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockWebServer;
import ru.narbut.axel.data.ApplicationTestCase;
import ru.narbut.axel.data.entity.network.mapper.PhotoPageResponseDtoMapper;
import ru.narbut.axel.data.entity.network.mapper.PhotoResponseDtoMapper;
import ru.narbut.axel.data.exception.PhotoNotFoundException;
import ru.narbut.axel.data.repository.network.NetworkProvider;
import ru.narbut.axel.data.repository.network.PhotoRepositoryImp;
import ru.narbut.axel.data.repository.network.PixabayConstants;
import ru.narbut.axel.data.repository.network.manager.NetworkStateManagerImpl;
import ru.narbut.axel.domain.interactor.gallery.DiscoverPhotoParams;
import ru.narbut.axel.domain.model.PhotoResult;

public class PhotoRepositoryTest extends ApplicationTestCase {


    private PhotoRepositoryImp photoRepositoryImp;
    @Rule
    public final MockWebServer server = new MockWebServer();

    @Mock private PixabayConstants pixabayConstants;
    @Mock private PhotoResponseDtoMapper pageResponseDtoMapper;

    private final int CHECK_LOAD_PAGE = 1;
    private final String CHECK_LOAD_LAND = "en";

    @Before
    public void setUp() {
        photoRepositoryImp = new PhotoRepositoryImp(pixabayConstants,
                new NetworkProvider(context(),new NetworkStateManagerImpl(context())),
                new PhotoPageResponseDtoMapper(pageResponseDtoMapper));
    }

    @Test
    public void isExistCatsPhoto() {
        TestObserver<PhotoResult> observer = new TestObserver<>();
        photoRepositoryImp.keywordDiscoverPhotoList(
                new DiscoverPhotoParams(CHECK_LOAD_PAGE,CHECK_LOAD_LAND,"cat"))
                .subscribe(observer);
        observer.assertComplete();
    }

    @Test
    public void noValidPhotoQuery() {
        TestObserver<PhotoResult> observer = new TestObserver<>();
        photoRepositoryImp.keywordDiscoverPhotoList(
                new DiscoverPhotoParams(CHECK_LOAD_PAGE,CHECK_LOAD_LAND,"asdwqda/"))
                .subscribe(observer);
        observer.assertError(PhotoNotFoundException.class);
    }

}
