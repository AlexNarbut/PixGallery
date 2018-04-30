package ru.narbut.axel.gallery.view.base.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.hannesdorfmann.mosby3.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import icepick.Icepick;
import lombok.Getter;
import ru.narbut.axel.data.di.qualifiers.ActivityContext;
import ru.narbut.axel.gallery.App;
import ru.narbut.axel.gallery.R;
import ru.narbut.axel.gallery.di.components.ActivityComponent;
import ru.narbut.axel.gallery.di.components.DaggerActivityComponent;
import ru.narbut.axel.gallery.di.modules.ActivityModule;
import ru.narbut.axel.gallery.exception.ErrorMessageFactory;
import ru.narbut.axel.gallery.navigation.Navigator;

public abstract  class BaseViewStateActivity<V extends MvpView, P extends MvpPresenter<V>, VS extends ViewState<V>>
        extends MvpViewStateActivity<V, P, VS> {

    private Unbinder unbinder;
    @Inject
    protected Navigator navigator;
    @Inject
    @ActivityContext
    protected Context context;

    @Getter
    @Inject
    protected ErrorMessageFactory errorMessageFactory;

    @Getter
    private Bundle bundle;

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public void setToolbarTitle(int titleId) {
        if(toolbar!= null)
            toolbar.setTitle(titleId);
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    private ActivityComponent activityComponent;

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        this.activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((App)getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
        initializeDependencies();
        super.onCreate(savedInstanceState);
        bundle = savedInstanceState;
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(getLayoutRes());
    }

    @Override public void onContentChanged() {
        super.onContentChanged();
        unbinder = ButterKnife.bind(this);
        initLayout();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        navigator = null;
        context = null;
        errorMessageFactory = null;
        activityComponent = null;
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    protected void setUpToolbar(boolean showUpButton) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showUpButton);
        }
    }

    public abstract void initLayout();

    public abstract void initializeDependencies();
}