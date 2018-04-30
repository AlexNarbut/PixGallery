package ru.narbut.axel.data.repository.network;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import ru.narbut.axel.data.entity.network.PhotoPageResponseDto;

public interface PixabayApiService {
    @GET("/api/")
    Observable<PhotoPageResponseDto> discoverPhotos(@QueryMap Map<String, String> param);

    @Streaming
    @GET
    Observable<Response<ResponseBody>> downloadFileByUrl(@Url String fileUrl);
}
