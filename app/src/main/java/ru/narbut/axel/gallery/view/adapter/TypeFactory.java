package ru.narbut.axel.gallery.view.adapter;

import android.view.View;

import ru.narbut.axel.gallery.view.adapter.viewHolder.AbstractItemViewHolder;
import ru.narbut.axel.gallery.view.adapter.viewModel.PhotoModel;


public interface TypeFactory {
    int type(PhotoModel model);

    AbstractItemViewHolder createViewHolder(View parent, int type);
}
