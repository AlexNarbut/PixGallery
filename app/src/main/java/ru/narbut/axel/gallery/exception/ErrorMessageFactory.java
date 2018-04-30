package ru.narbut.axel.gallery.exception;

import android.content.Context;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.inject.Inject;
import ru.narbut.axel.data.di.qualifiers.ActivityContext;
import ru.narbut.axel.data.di.scope.ActivityScope;
import ru.narbut.axel.data.exception.FileWriteException;
import ru.narbut.axel.data.exception.InternetException;
import ru.narbut.axel.data.exception.PhotoNotFoundException;
import ru.narbut.axel.gallery.R;

@ActivityScope
public class ErrorMessageFactory {
  private Context context;

  private final int HTTP_BAD_REQUEST_CODE = 400;
  private final int HTTP_TOO_MANY_REQUEST = 429;

  @Inject
  public ErrorMessageFactory(@ActivityContext Context context) {
    this.context = context;
  }

  public String create(Exception exception) {
    if(context == null)return "";
    if(exception instanceof HttpException){
      switch (((HttpException)exception).code()){
        case HTTP_BAD_REQUEST_CODE:{
          return context.getString(R.string.text_error_http_400);
        }
        case HTTP_TOO_MANY_REQUEST:{
          return  context.getString(R.string.text_error_http_429);
        }
        default:
          return  context.getString(R.string.text_error_http);
      }
    }
    else if(exception instanceof SocketTimeoutException){
      return  context.getString(R.string.text_error_http);
    }
    else if(exception instanceof UnknownHostException){
      return  context.getString(R.string.text_error_http);
    }
    else if(exception instanceof FileWriteException){
      return  context.getString(R.string.text_error_file_write);
    }
    else if(exception instanceof InternetException){
      return  context.getString(R.string.text_error_internet_is_not_exist);
    }
    else if(exception instanceof PhotoNotFoundException){
      return  context.getString(R.string.text_error_photos_not_found);
    }
    return context.getString(R.string.text_error_default);
  }
}
