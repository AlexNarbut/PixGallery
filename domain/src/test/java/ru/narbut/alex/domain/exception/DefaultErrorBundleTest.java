package ru.narbut.alex.domain.exception;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.verify;

import ru.narbut.axel.domain.exception.DefaultErrorBundle;

@RunWith(MockitoJUnitRunner.class)
public class DefaultErrorBundleTest {
    private DefaultErrorBundle defaultErrorBundle;

    @Mock
    private Exception mockException;

    @Before
    public void setUp() {
        defaultErrorBundle = new DefaultErrorBundle(mockException);
    }

    @Test
    public void testGetErrorMessageInteraction() {
        defaultErrorBundle.getErrorMessage();
        verify(mockException).getMessage();
    }
}
