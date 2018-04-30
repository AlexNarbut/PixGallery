package ru.narbut.axel.gallery.view.base.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.narbut.axel.gallery.view.adapter.viewHolder.AbstractItemViewHolder;

public abstract class RecyclerAdapter<T , V extends RecyclerView.ViewHolder>extends RecyclerView.Adapter<V>{
    protected Context context;
    protected List<T>  items;

    public RecyclerAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    @NonNull @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(V holder, int position) {}

    @Override public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public List<T> getItems() {
        return items;
    }

    public void addItems(List<T> items) {
        if( items== null || items.isEmpty()) return;
        int curSize = getItemCount();
        this.items.addAll(items);
        notifyItemRangeChanged(curSize,items.size());
    }

    public void removeItemByPos(int pos){
        if(pos < 0 || pos >= items.size()
                || items== null || items.isEmpty()) return;
        items.remove(pos);
        notifyItemRemoved(pos);
    }

    public void clearItems(){
        this.items.clear();
        notifyDataSetChanged();
    }

    public abstract void onBindViewHolder(AbstractItemViewHolder holder, int position);
}
