package ru.narbut.axel.gallery.utils;

import android.text.TextUtils;

public class UiUtils {
    private static UiUtils instance;

    public static synchronized UiUtils getInstance() {
        if(instance == null) {
            instance = new UiUtils();
        }
        return instance;
    }

    private UiUtils() {
    }

    // remove except notUrl symbols (ava.net.URLDecoder  does not fit for Pixabay API)
    public String getUrlDecodeString(String str){
        if(TextUtils.isEmpty(str))return null;
        String[]strings = str.trim().replaceAll("\\s{2,}", " ").split("\\s");
        StringBuilder sBuilder = new StringBuilder();
        //delete not word character and concat with space
        for(String s:strings){
            sBuilder.append(s.replaceAll("\\W", "")).append(" ");
        }
        return sBuilder.toString().toLowerCase().trim();
    }

}
