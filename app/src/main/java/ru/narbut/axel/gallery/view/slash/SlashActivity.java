package ru.narbut.axel.gallery.view.slash;

import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v7.widget.AppCompatTextView;
import butterknife.BindView;
import javax.inject.Inject;

import ru.narbut.axel.gallery.R;
import ru.narbut.axel.gallery.view.base.view.BaseActivity;


public class SlashActivity extends BaseActivity<SlashMvpView,SlashPresenter<SlashMvpView>> implements SlashMvpView  {


    @Inject
    SlashPresenter slashPresenter;

    @NonNull
    @Override
    public SlashPresenter<SlashMvpView> createPresenter() {
        return slashPresenter;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_slash;
    }


    @Override
    public void initLayout() {
        setUpToolbar(false);
        if(getBundle()== null)
            prepareMainActivityOpening();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void initializeDependencies() {
        getActivityComponent().inject(this);
    }

    @Override
    public void prepareMainActivityOpening() {
        slashPresenter.startMainActivity();
    }

    @Override
    public void openMainActivity() {
        navigator.navigateToMainActivity(context);
        finish();
    }

}
