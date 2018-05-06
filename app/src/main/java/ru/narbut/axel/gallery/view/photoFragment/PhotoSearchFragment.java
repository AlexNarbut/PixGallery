package ru.narbut.axel.gallery.view.photoFragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.OnClick;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import icepick.State;
import ru.narbut.axel.domain.exception.DefaultErrorBundle;
import ru.narbut.axel.gallery.utils.UiUtils;
import ru.narbut.axel.gallery.R;
import ru.narbut.axel.gallery.view.adapter.PhotoAdapter;
import ru.narbut.axel.gallery.view.adapter.component.EndlessScrollListener;
import ru.narbut.axel.gallery.view.adapter.viewModel.ItemViewModel;
import ru.narbut.axel.gallery.view.adapter.viewModel.PhotoModel;
import ru.narbut.axel.gallery.view.base.view.BaseLceFragment;

public class PhotoSearchFragment
        extends BaseLceFragment<CoordinatorLayout,List<PhotoModel>,PhotoSearchView<List<PhotoModel>>,PhotoSearchPresenter<PhotoSearchView<List<PhotoModel>>>>
        implements PhotoSearchView<List<PhotoModel>>,SwipeRefreshLayout.OnRefreshListener, PhotoAdapter.OnItemClickListener,
    SearchView.OnQueryTextListener {

    @Inject
    PhotoSearchPresenter photoPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeView)
    SwipeRefreshLayout swipeView;

    @Inject
    PhotoAdapter photoAdapter;
    EndlessScrollListener scrollListener;

    @State String query = "";
    @State int currentLoadPage = 1;

    private final int MAX_QUERY_LENGTH = 30;

    private final int START_LOAD_PAGE = 1;
    private int VERTICAL_COLUMNS_NUMBER = 2;
    private int HORIZONTAL_COLUMNS_NUMBER = 3;

    private View selectedPhoto;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_photo_search;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeView.setOnRefreshListener(this);
        photoAdapter.setOnItemClick(this);
        StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
                        ? VERTICAL_COLUMNS_NUMBER : HORIZONTAL_COLUMNS_NUMBER,
                    StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(photoAdapter);
        recyclerView.setOnTouchListener((v, event) -> false);
        scrollListener = new EndlessScrollListener(lm,currentLoadPage) {
            @Override public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                currentLoadPage = page;
                loadData(false);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    private void clearPhotoList(){
        if(photoAdapter == null)return;
        photoAdapter.clearItems();
        currentLoadPage = START_LOAD_PAGE;
        scrollListener.resetState();
    }

    @Override
    public void showFullScreenActivity() {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),
                selectedPhoto,
                ViewCompat.getTransitionName(selectedPhoto));
        getmListener().getNavigator().navigateToFullScreenActivity(getContext(),options.toBundle());
    }

    @Override public void showAboutActivity() {
        getmListener().getNavigator().navigateToAboutActivity(getContext());
    }

    @Override
    protected void injectDependencies() {
        getActivityComponent().inject(this);
    }

    @NonNull @Override
    public PhotoSearchPresenter<PhotoSearchView<List<PhotoModel>>> createPresenter() {
        return photoPresenter;
    }

    @Override public void onDestroyView() {
        recyclerView.setAdapter(null);
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        clearPhotoList();
        loadData(true);
    }

    @Override public void clearRecyclerAdapter() {
        if(photoAdapter == null)return;
        photoAdapter.clearItems();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.action_search));
        /*try fix bug with landscape orientation**/
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        searchView.setMaxWidth(width);
        /**/

        /*reload query to searchView*/
        if (!TextUtils.isEmpty(query)) {
            searchView.setIconified(true);
            searchItem.expandActionView();
            searchView.setQuery(query, true);
            searchView.setFocusable(true);
        }

        /**/
        searchView.setOnQueryTextListener(this);
    }

    @OnClick(R.id.aboutButton)
    public void onClickAbout(){
        showAboutActivity();
    }

    @Override public boolean onQueryTextSubmit(String q) {
        if(q.length()<= MAX_QUERY_LENGTH) {
            boolean isChange = !q.equals(query);
            query = q;
            if (isChange) {
                clearPhotoList();
                loadData(true);
            }
        }else
            Toast.makeText(getContext(),
                getString(R.string.text_error_max_query_length).concat(String.valueOf(MAX_QUERY_LENGTH)),
                Toast.LENGTH_LONG ).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        boolean reset =  TextUtils.isEmpty(newText) && !newText.equals(query);
        if(reset){
            query = newText;
            clearPhotoList();
            loadData(true);
        }
        return false;
    }


    @NonNull
    @Override
    public LceViewState<List<PhotoModel>, PhotoSearchView<List<PhotoModel>>> createViewState() {
        return new RetainingLceViewState<>();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return getErrorMessageBundle((new DefaultErrorBundle((Exception) e)).getException());
    }


    @Override
    public List<PhotoModel> getData() {
        return photoAdapter == null ? null : photoAdapter.getItems();
    }

    @Override
    public void setData(List<PhotoModel> data) {
        photoAdapter.addItems(data);
    }

    @Override public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        swipeView.setRefreshing(false);
        scrollListener.resetLoading();
        scrollListener.reduceCurrentLoadPage();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadPhotos(currentLoadPage,UiUtils.getInstance().getUrlDecodeString(query),pullToRefresh);
    }

    @Override
    public void showContent() {
        super.showContent();
        swipeView.setRefreshing(false);
    }

    @Override
    public void onItemClick(View view, ItemViewModel item) {
        selectedPhoto = view;
        presenter.prepareForFullScreenActivity(((PhotoModel)item));
    }
}
