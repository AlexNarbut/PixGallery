package ru.narbut.axel.gallery.view.adapter.viewModel;


import ru.narbut.axel.gallery.view.adapter.TypeFactory;

public interface ItemViewModel {
    int getId();
    int type(TypeFactory typeFactory);
}
