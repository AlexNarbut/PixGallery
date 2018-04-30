package ru.narbut.axel.domain.repository;

import io.reactivex.Observable;
import ru.narbut.axel.domain.model.Photo;

public interface SavedConstantsRepository {
    Observable<String> getStringByKey(String key);
    Observable<Boolean> saveSelectedPhoto(Photo photo);
    Observable<Photo> getSelectedPhoto();
}
