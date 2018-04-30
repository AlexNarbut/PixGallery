package ru.narbut.axel.data.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ru.narbut.axel.data.entity.network.PhotoResponseDto;
import ru.narbut.axel.data.entity.network.mapper.PhotoResponseDtoMapper;
import ru.narbut.axel.domain.model.Photo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class PhotoResponseDtoMapperTest {
    private PhotoResponseDtoMapper dtoMapper;

    private static final int FAKE_ID = 123;
    private static final String FAKE_USER_FULLNAME = "Alex Narbut";

    @Before
    public void setUp() {
        dtoMapper = new PhotoResponseDtoMapper();
    }

    @Test
    public void testTransformPhotoEntity() {
        PhotoResponseDto dto = createFakePhotoResponseDto();
        Photo photo = dtoMapper.mapDirect(dto);

        assertThat(photo, is(instanceOf(Photo.class)));
        assertThat(photo.getId(), is(FAKE_ID));
        assertThat(photo.getUserName(), is(FAKE_USER_FULLNAME));
    }

    @Test
    public void testTransformUserEntityCollection() {
        PhotoResponseDto mockPhotoDtoOne = mock(PhotoResponseDto.class);
        PhotoResponseDto mockPhotoDtoTwo = mock(PhotoResponseDto.class);

        List<PhotoResponseDto> userEntityList = new ArrayList<>(5);
        userEntityList.add(mockPhotoDtoOne);
        userEntityList.add(mockPhotoDtoTwo);

        Collection<Photo> photoCollection = dtoMapper.mapList(userEntityList);

        assertThat(photoCollection.toArray()[0], is(instanceOf(Photo.class)));
        assertThat(photoCollection.toArray()[1], is(instanceOf(Photo.class)));
        assertThat(photoCollection.size(), is(2));
    }

    private PhotoResponseDto createFakePhotoResponseDto() {
        PhotoResponseDto dto = new PhotoResponseDto();
        dto.setId(FAKE_ID);
        dto.setUser(FAKE_USER_FULLNAME);
        return dto;
    }
}
