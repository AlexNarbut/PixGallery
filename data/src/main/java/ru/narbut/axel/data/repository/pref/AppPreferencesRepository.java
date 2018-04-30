package ru.narbut.axel.data.repository.pref;


import android.content.Context;
import android.content.SharedPreferences;
import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.gson.Gson;
import javax.inject.Inject;
import io.reactivex.Completable;
import io.reactivex.Observable;
import ru.narbut.axel.data.di.qualifiers.ApplicationContext;
import ru.narbut.axel.data.di.qualifiers.PrefFile;
import ru.narbut.axel.data.di.scope.ApplicationScope;
import ru.narbut.axel.domain.model.Photo;
import ru.narbut.axel.domain.repository.SavedConstantsRepository;

@ApplicationScope
public class AppPreferencesRepository implements SavedConstantsRepository {
    private RxSharedPreferences rxPreferences;
    private Gson gson;
    private final String PHOTO_KEY = "photo_21312";


    @Inject
    public AppPreferencesRepository(@ApplicationContext Context context, @PrefFile String prefName) {
        SharedPreferences preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        gson = new Gson();
        this.rxPreferences =  RxSharedPreferences.create(preferences);

    }

    @Override
    public Observable<String> getStringByKey(String s) {
        return rxPreferences.getString(s).asObservable();
    }

    @Override
    public Observable<Boolean> saveSelectedPhoto(Photo photo) {
        Preference<String> pref = rxPreferences.getString(PHOTO_KEY);
        pref.set(gson.toJson(photo));
        return Observable.just(pref.isSet());
    }

    @Override
    public Observable<Photo> getSelectedPhoto() {
        Preference <String> pref = rxPreferences.getString(PHOTO_KEY,"");
        if(pref.get().equals(""))
            return Completable.complete().toObservable();
        else return Observable.just(gson.fromJson(pref.get(),Photo.class));
    }

}
