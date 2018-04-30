package ru.narbut.axel.gallery.view.mainActivity;

import android.support.annotation.NonNull;
import javax.inject.Inject;
import ru.narbut.axel.gallery.R;
import ru.narbut.axel.gallery.di.components.ActivityComponent;
import ru.narbut.axel.gallery.navigation.Navigator;
import ru.narbut.axel.gallery.view.base.view.BaseActivity;
import ru.narbut.axel.gallery.view.base.view.OnFragmentInteractionListener;
import ru.narbut.axel.gallery.view.photoFragment.PhotoSearchFragment;


public class MainActivity extends BaseActivity<MainMvpView,MainPresenter<MainMvpView>> implements MainMvpView,
        OnFragmentInteractionListener {

    @Inject
    MainPresenter mainPresenter;


    @NonNull
    @Override
    public MainPresenter<MainMvpView> createPresenter() {
        return mainPresenter;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }


    @Override
    public void initLayout() {
        setUpToolbar(false);
        if(getBundle()== null)
            showPhotoFrag();
    }


    @Override
    public void showPhotoFrag() {
        replaceFragment(new PhotoSearchFragment(),R.string.text_photo_search_title);
    }


    @Override
    public void initializeDependencies() {
        getActivityComponent().inject(this);
    }


    @Override
    public ActivityComponent getActivityComponentCallback() {
        return getActivityComponent();
    }


    @Override
    public Navigator getNavigator() {
        return navigator;
    }
}
