package ru.narbut.axel.gallery.view.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;

public abstract class AbstractItemViewHolder<T> extends RecyclerView.ViewHolder {
    protected StringBuilder stringBuilder;


    public AbstractItemViewHolder(View view) {
        super(view);
        stringBuilder = new StringBuilder();
        ButterKnife.bind(this, view);
    }

    public abstract void bind(T element);

    public abstract void unbind();

    protected void clearStringBuilder(){
        stringBuilder.setLength(0);
        stringBuilder.trimToSize();
    }
}