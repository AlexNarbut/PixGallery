package ru.narbut.axel.gallery.navigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import javax.inject.Inject;
import ru.narbut.axel.data.di.qualifiers.ApplicationContext;
import ru.narbut.axel.gallery.view.about.AboutActivity;
import ru.narbut.axel.gallery.view.hullScreenPhotoActivity.FullScreenPhotoActivity;
import ru.narbut.axel.gallery.view.mainActivity.MainActivity;


@ApplicationContext
public class Navigator {
    @Inject
    public Navigator() {}

    public void navigateToMainActivity(Context context){
        if (context != null){
            context.startActivity(new Intent(context, MainActivity.class));
        }
    }

    public void navigateToAboutActivity(Context context){
        if (context != null){
            context.startActivity(new Intent(context, AboutActivity.class));
        }
    }

    public void navigateToWeb(Context context,Uri uri){
        if (context != null){
            context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

    public void navigateToFullScreenActivity(Context context, Bundle bundle){
        Intent intent = new Intent(context, FullScreenPhotoActivity.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP && bundle!= null) {
            context.startActivity(intent,bundle);
        }else {
            context.startActivity(intent);
        }
    }


}
