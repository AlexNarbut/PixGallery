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
import ru.narbut.axel.domain.interactor.gallery.DownloadPhotoUseCase;
import ru.narbut.axel.domain.interactor.gallery.DownloadPhotoParam;
import ru.narbut.axel.domain.repository.PhotoRepository;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class LoadFileTest {
    private final String PHOTO_URL = "https://pixabay.com/get/ea37b00a29fc093ed1584d05fb1d4596e07ee7d01bac104497f3c678a4e8b2b8_1280.jpg";
    private DownloadPhotoUseCase downloadPhotoUseCase;

    @Mock
    private PhotoRepository mockPhotoRepository;
    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        downloadPhotoUseCase = new DownloadPhotoUseCase(mockPhotoRepository, mockThreadExecutor,
                mockPostExecutionThread);
    }

    @Test
    public void testGetUserDetailsUseCaseObservableHappyCase() {
        downloadPhotoUseCase.buildUseCaseObservable(DownloadPhotoParam.forPhoto("aaa",PHOTO_URL));

        verify(mockPhotoRepository).downloadFile(new DownloadPhotoParam("aaa",PHOTO_URL));
        verifyNoMoreInteractions(mockPhotoRepository);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }

    @Test
    public void testShouldFailWhenNoOrEmptyParameters() {
        expectedException.expect(NullPointerException.class);
        downloadPhotoUseCase.buildUseCaseObservable(null);
    }
}
