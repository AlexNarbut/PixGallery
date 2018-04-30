package ru.narbut.alex.domain.interactor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ru.narbut.axel.domain.executor.PostExecutionThread;
import ru.narbut.axel.domain.executor.ThreadExecutor;
import ru.narbut.axel.domain.interactor.gallery.DiscoverPhotoParams;
import ru.narbut.axel.domain.interactor.gallery.DiscoverPhotosUseCase;
import ru.narbut.axel.domain.repository.PhotoRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class DiscoverPhotosTest {

    private DiscoverPhotosUseCase discoverPhotosUseCase;

    @Mock
    private PhotoRepository mockPhotoRepository;
    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        discoverPhotosUseCase = new DiscoverPhotosUseCase(mockPhotoRepository, mockThreadExecutor,
                mockPostExecutionThread);
    }

    @Test
    public void testGetUserDetailsUseCaseObservableHappyCase() {
        discoverPhotosUseCase.buildUseCaseObservable(DiscoverPhotoParams.forPhoto(0,"ru",null));

        verify(mockPhotoRepository).keywordDiscoverPhotoList(new DiscoverPhotoParams(0,"ru",null));
        verifyNoMoreInteractions(mockPhotoRepository);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }

    @Test
    public void testShouldFailWhenNoOrEmptyParameters() {
        expectedException.expect(NullPointerException.class);
        discoverPhotosUseCase.buildUseCaseObservable(null);
    }
}