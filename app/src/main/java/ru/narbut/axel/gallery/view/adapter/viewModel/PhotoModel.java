package ru.narbut.axel.gallery.view.adapter.viewModel;

import lombok.Getter;
import lombok.Setter;
import ru.narbut.axel.gallery.view.adapter.TypeFactory;

@Getter
@Setter
public class PhotoModel  implements  ItemViewModel{
    private int id;
    private String imageUrl;
    private int imageWidth;
    private int imageHeight;
    private String userName;
    private String tags;

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
