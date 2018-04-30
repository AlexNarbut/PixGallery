package ru.narbut.axel.gallery.view.base.view;

import ru.narbut.axel.gallery.di.components.ActivityComponent;
import ru.narbut.axel.gallery.exception.ErrorMessageFactory;
import ru.narbut.axel.gallery.navigation.Navigator;


public interface OnFragmentInteractionListener {
    Navigator getNavigator();
    ErrorMessageFactory getErrorMessageFactory();
    ActivityComponent getActivityComponentCallback();
}
