package ru.narbut.axel.data.repository.network;

import javax.inject.Inject;

import ru.narbut.axel.data.di.scope.ApplicationScope;

@ApplicationScope
public class PixabayConstants {
    @Inject
    public PixabayConstants() {
    }

    protected final String PIXEBAY_BASE_URL = "https://pixabay.com/";

    protected final String PIXABAY_API_KEY_PARAM_NAME = "key";
    protected final String  PIXABAY_API_KEY = "3039552-fdfb17e19bff4597c9545a4df";

    protected  final String PIXABAY_PAGE_PARAM_NAME = "page";
    protected  final String PIXABAY_PER_PAGE_PARAM_NAME = "per_page";
    protected  final String PIXABAY_LANG_PARAM_NAME = "lang";
    protected  final String PIXABAY_QUERY_PARAM_NAME = "q";

    protected final int OPTIMUM_PHOTOS_NUMBER = 30;
}
