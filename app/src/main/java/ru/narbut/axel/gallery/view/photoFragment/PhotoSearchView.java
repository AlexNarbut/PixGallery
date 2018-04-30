package ru.narbut.axel.gallery.view.photoFragment;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

public interface PhotoSearchView<M> extends MvpLceView<M> {
    void showFullScreenActivity();
    void showAboutActivity();
    void clearRecyclerAdapter();
}
