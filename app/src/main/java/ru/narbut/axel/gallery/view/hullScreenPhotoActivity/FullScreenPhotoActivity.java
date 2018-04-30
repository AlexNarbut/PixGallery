package ru.narbut.axel.gallery.view.hullScreenPhotoActivity;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.tbruyelle.rxpermissions2.RxPermissions;
import javax.inject.Inject;
import ru.narbut.axel.domain.exception.DefaultErrorBundle;
import ru.narbut.axel.gallery.R;
import ru.narbut.axel.gallery.model.FullPhotoModel;
import ru.narbut.axel.gallery.ui.SwipeActivityGestureDetector;
import ru.narbut.axel.gallery.view.base.view.BaseLceActivity;

public class FullScreenPhotoActivity extends
        BaseLceActivity<RelativeLayout,FullPhotoModel, FullPhotoMvpView,FullPhotoPresenter<FullPhotoMvpView>> implements FullPhotoMvpView{

    @Inject
    FullPhotoPresenter presenter;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.actionSave) FloatingActionButton actionSave;

    @BindView(R.id.photoTags) TextView photoTags;
    @BindView(R.id.photoUserName) TextView photoUserName;
    @BindView(R.id.photoComments) TextView photoComments;
    @BindView(R.id.photoFavorites) TextView photoFavorites;
    @BindView(R.id.photoLikes) TextView photoLikes;

    FullPhotoModel photoModel;

    protected AlertDialog pDialog;

    @NonNull
    @Override
    public FullPhotoPresenter<FullPhotoMvpView> createPresenter() {
        return presenter;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_full_screen_photo;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        setTransparentActionBar();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initLayout() {
        setUpToolbar(true);
        RxPermissions rxPermissions = new RxPermissions(this);
        actionSave.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                rxPermissions
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {
                            showLoadingProgressDialog();
                            presenter.downloadPhoto();
                        } else {
                            showError(new Throwable(getString(R.string.permission_error_write_read_external_storage)),true);
                        }
                    });
            }
        });
    }

    private void setTransparentActionBar(){
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
            actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#550000ff")));
        }
    }

    @Override
    public void setData(FullPhotoModel data) {
        setSwipeDetector();
        actionSave.setVisibility(View.GONE);
        photoModel = data;
        photoTags.setText(data.getTags());
        photoComments.setText(data.getComments());
        photoFavorites.setText(data.getFavorites());
        photoLikes.setText(data.getLikes());
        photoUserName.setText(data.getUserName());
        Glide.with(image.getContext()).asBitmap()
                .load(data.getImageUrl())
                .thumbnail(0.1f)
                .apply(new RequestOptions()
                    .placeholder(R.drawable.ic_place_holder)
                    .error(R.drawable.ic_place_holder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH))
                .listener(new RequestListener<Bitmap>() {
                    @Override public boolean onLoadFailed(@Nullable GlideException e, Object model,
                        Target<Bitmap> target, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }

                    @Override public boolean onResourceReady(Bitmap resource, Object model,
                        Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        actionSave.setVisibility(View.VISIBLE);
                        image.setImageBitmap(resource);
                        return false;
                    }
                }).into(image);
    }

    private void setSwipeDetector(){
        GestureDetector d = new GestureDetector(this, new SwipeActivityGestureDetector(this));
        image.setOnTouchListener((v, event) -> d.onTouchEvent(event));
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.initPhoto();
    }

    @Override
    public void initializeDependencies() {
        getActivityComponent().inject(this);
    }


    @Override
    public void cancelLoadingDialog() {
        closeDialog();
    }

    @Override public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        getCastedViewState().setResetSavingState(true);
        closeDialog();
    }

    @Override
    public void showLoadingProgressDialog() {
        closeDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.text_full_screen_loading_title);
        builder.setView(View.inflate(context, R.layout.dialog_loading_file, null));
        pDialog = builder.create();
        pDialog.show();
        getCastedViewState().setSavingState(true);
    }

    private void closeDialog(){
        if (pDialog!=null && pDialog.isShowing())pDialog.dismiss();
    }

    @Override
    public void showFinishLoadingDialog() {
        closeDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.text_full_screen_loading_is_completed);
        builder.setOnDismissListener(
            dialogInterface -> getCastedViewState().setResetSavingState(true));
        builder.setPositiveButton(R.string.button_text_ok, (dialog, id) -> {
                dialog.dismiss();
            });
        pDialog = builder.create();
        pDialog.show();
        getCastedViewState().setFinishSavingState(true);
    }

    @Override
    public void closeActivity() {
        finish();
    }


    @Override
    public FullPhotoModel getData() {
        return photoModel;
    }

    @NonNull
    @Override
    public LceViewState<FullPhotoModel, FullPhotoMvpView> createViewState() {
        return new FullScreenPhotoViewState();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return getErrorMessageFactory().create((new DefaultErrorBundle((Exception) e)).getException());
    }

    private FullScreenPhotoViewState getCastedViewState(){
        return (FullScreenPhotoViewState) getViewState();
    }

}
