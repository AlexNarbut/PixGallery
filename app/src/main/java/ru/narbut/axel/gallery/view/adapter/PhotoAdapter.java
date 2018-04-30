package ru.narbut.axel.gallery.view.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.narbut.axel.gallery.view.adapter.viewHolder.AbstractItemViewHolder;
import ru.narbut.axel.gallery.view.adapter.viewModel.ItemViewModel;
import ru.narbut.axel.gallery.view.adapter.viewModel.PhotoModel;
import ru.narbut.axel.gallery.view.base.view.RecyclerAdapter;

public class PhotoAdapter extends RecyclerAdapter<PhotoModel,AbstractItemViewHolder> {

    TypeFactory typeFactory;
    private OnItemClickListener onItemClick;

    @Inject
    public PhotoAdapter(Activity context) {
        super(context);
        this.typeFactory =new TypeFactoryForList();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ItemViewModel item);
    }

    public void setOnItemClick(OnItemClickListener onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull @Override
    public AbstractItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(viewType, parent, false);
        AbstractItemViewHolder holder = typeFactory.createViewHolder(view, viewType);
        view.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if(adapterPosition!= RecyclerView.NO_POSITION){
                if(onItemClick == null)return;
                onItemClick.onItemClick(view,items.get(adapterPosition));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(AbstractItemViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).type(typeFactory);
    }


    @Override
    public boolean onFailedToRecycleView(AbstractItemViewHolder holder) {
        return false;
    }
}