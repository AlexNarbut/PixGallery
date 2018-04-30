package ru.narbut.axel.gallery.view.adapter;


import android.annotation.SuppressLint;
import android.view.View;

import javax.inject.Inject;
import ru.narbut.axel.gallery.view.adapter.exception.TypeNotSupportedException;
import ru.narbut.axel.gallery.view.adapter.viewHolder.AbstractItemViewHolder;
import ru.narbut.axel.gallery.view.adapter.viewHolder.PhotoViewHolder;
import ru.narbut.axel.gallery.view.adapter.viewModel.PhotoModel;


public class TypeFactoryForList implements TypeFactory {

    public TypeFactoryForList() {
    }

    @Override
    public int type(PhotoModel model) {
        return PhotoViewHolder.LAYOUT;
    }

    @Override
    @SuppressLint("DefaultLocale")
    public AbstractItemViewHolder createViewHolder(View parent, int type) {
        AbstractItemViewHolder createdViewHolder;
        switch (type) {
            case PhotoViewHolder.LAYOUT:
                createdViewHolder = new PhotoViewHolder(parent);
                break;
            default:
                throw TypeNotSupportedException.create(String.format("LayoutType: %d", type));
        }
        return createdViewHolder;
    }
}