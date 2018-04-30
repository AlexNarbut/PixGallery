package ru.narbut.axel.gallery.view.about;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import javax.inject.Inject;
import ru.narbut.axel.gallery.BuildConfig;
import ru.narbut.axel.gallery.R;
import ru.narbut.axel.gallery.view.base.view.BaseActivity;

public class AboutActivity extends BaseActivity<AboutMvpView,AboutPresenter<AboutMvpView>> implements AboutMvpView  {
    @BindView(R.id.version)AppCompatTextView version;

    @Inject
    AboutPresenter aboutPresenter;

    @NonNull
    @Override
    public AboutPresenter<AboutMvpView> createPresenter() {
        return aboutPresenter;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_about;
    }


    @Override
    public void initLayout() {
        setUpToolbar(true);
        version.setText(getString(R.string.text_about_title_version).concat(" ")
            .concat(BuildConfig.VERSION_NAME).concat("(").concat(String.valueOf(BuildConfig.VERSION_CODE)).concat(")"));
    }

    @OnClick({R.id.github, R.id.devContact})
    void onClickButton(View view) {
        int urlResId;
        switch (view.getId()) {
            case R.id.github:
                urlResId = R.string.url_github;
                break;
            case R.id.devContact:
                urlResId = R.string.url_developer;
                break;
            default:
                return;
        }
        navigator.navigateToWeb(this,Uri.parse(getString(urlResId)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            openMainActivity();
        }
        return super.onOptionsItemSelected(item);
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
    public void openMainActivity() {
        navigator.navigateToMainActivity(context);
        finish();
    }

}
