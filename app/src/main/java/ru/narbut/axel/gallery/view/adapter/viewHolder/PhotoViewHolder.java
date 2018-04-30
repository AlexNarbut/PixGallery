package ru.narbut.axel.gallery.view.adapter.viewHolder;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import ru.narbut.axel.gallery.R;
import ru.narbut.axel.gallery.ui.DynamicHeightImageView;
import ru.narbut.axel.gallery.view.adapter.viewModel.PhotoModel;

public class PhotoViewHolder extends AbstractItemViewHolder<PhotoModel> {

    @BindView(R.id.backdrop) DynamicHeightImageView backdrop;
    @BindView(R.id.tags) TextView tags;

    @LayoutRes
    public static final int LAYOUT = R.layout.view_photo_item;

    private PhotoModel photoModel;


    public static PhotoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(LAYOUT, parent, false);
        return new PhotoViewHolder(view);
    }

    public PhotoViewHolder(View itemView) {
        super(itemView);
    }


    @Override
    public void bind(PhotoModel photo) {
        photoModel = photo;
        float ratio = (float) photo.getImageHeight()/photo.getImageWidth();
        backdrop.setHeightRatio(ratio);
        tags.setText(photo.getTags());
        loadImage();
    }

    private void loadImage(){
        Glide.with(itemView.getContext())
                .load(photoModel.getImageUrl())
                .thumbnail(0.1f)
                .apply(new RequestOptions()
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_place_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH))
                .into(backdrop);
    }

    @Override
    public void unbind() {
        Glide.with(backdrop.getContext()).clear(backdrop);
    }
}
