package ru.narbut.axel.data.repository.network;



import android.os.Environment;
import android.text.TextUtils;
import io.reactivex.Observable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;
import ru.narbut.axel.data.di.scope.ApplicationScope;
import ru.narbut.axel.data.entity.network.mapper.PhotoPageResponseDtoMapper;
import ru.narbut.axel.data.exception.FileWriteException;
import ru.narbut.axel.data.exception.InternetException;
import ru.narbut.axel.data.exception.PhotoNotFoundException;
import ru.narbut.axel.domain.interactor.gallery.DiscoverPhotoParams;
import ru.narbut.axel.domain.interactor.gallery.DownloadPhotoParam;
import ru.narbut.axel.domain.model.PhotoResult;
import ru.narbut.axel.domain.repository.PhotoRepository;


@ApplicationScope
public class PhotoRepositoryImp implements PhotoRepository {
    private PixabayApiService apiService;
    private PixabayConstants constants;
    private PhotoPageResponseDtoMapper photoPageResponseDtoMapper;
    private NetworkProvider networkProvider;

    @Inject
    public PhotoRepositoryImp(PixabayConstants constants,
                              NetworkProvider networkProvider,
                              PhotoPageResponseDtoMapper photoPageResponseDtoMapper) {
        this.constants = constants;
        this.photoPageResponseDtoMapper = photoPageResponseDtoMapper;
        this.networkProvider = networkProvider;
        this.apiService = networkProvider.provideRetrofit(constants.PIXEBAY_BASE_URL).create(PixabayApiService.class);
    }


    @Override
    public Observable<PhotoResult> keywordDiscoverPhotoList(DiscoverPhotoParams param) {
        if(networkProvider.getNetworkStateManager().isNetworkAvailable()) {
            Map<String, String> sendParam = new HashMap<>();
            sendParam.put(constants.PIXABAY_API_KEY_PARAM_NAME, constants.PIXABAY_API_KEY);
            sendParam.put(constants.PIXABAY_PAGE_PARAM_NAME, Integer.toString(param.getPage()));
            sendParam.put(constants.PIXABAY_PER_PAGE_PARAM_NAME,Integer.toString(constants.OPTIMUM_PHOTOS_NUMBER));
            if (!TextUtils.isEmpty(param.getLang()))
                sendParam.put(constants.PIXABAY_LANG_PARAM_NAME, param.getLang());
            if (!TextUtils.isEmpty(param.getQuery()))
                sendParam.put(constants.PIXABAY_QUERY_PARAM_NAME, param.getQuery());
            return apiService.discoverPhotos(sendParam).flatMap(photoPageResponseDto -> {
                if (photoPageResponseDto == null ||
                        photoPageResponseDto.getHits() == null
                        || photoPageResponseDto.getHits().isEmpty())
                    throw new PhotoNotFoundException();
                return Observable.just(photoPageResponseDtoMapper.mapDirect(photoPageResponseDto));
            });
        }else {
            return Observable.error(new InternetException());
        }
    }

    @Override
    public Observable<File> downloadFile(DownloadPhotoParam downloadPhotoParam) {
        if(networkProvider.getNetworkStateManager().isNetworkAvailable()) {
        return apiService.downloadFileByUrl(downloadPhotoParam.getPhotoUrl()).flatMap(responseBodyResponse -> {
            if(responseBodyResponse== null || responseBodyResponse.body() == null)
                throw new PhotoNotFoundException();
            return saveToDiskRx(responseBodyResponse, downloadPhotoParam.getName());
        });}
        else {
            return Observable.error(new InternetException());
        }
    }


    private Observable<File> saveToDiskRx(final Response<ResponseBody> response,String defaultName) {
        return Observable.create(obs -> {
            try {
                String header = response.headers().get("Content-Disposition");
                String fileName;
                if( header!= null)fileName = header.replaceFirst("(?i)^.*filename=\"?([^\"]+)\"?.*$", "$1");
                else fileName = defaultName;
                File destinationFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                obs.onNext(destinationFile);
                BufferedSink bufferedSink = Okio.buffer(Okio.sink(destinationFile));
                bufferedSink.writeAll(response.body().source());
                bufferedSink.close();
                obs.onComplete();
            }catch (IOException e1){
                obs.onError(new FileWriteException());
            }

        });
    }
}
