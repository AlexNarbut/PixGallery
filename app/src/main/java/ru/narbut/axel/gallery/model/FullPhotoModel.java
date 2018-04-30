package ru.narbut.axel.gallery.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullPhotoModel {
    private int id;
    private String imageUrl;
    private int imageWidth;
    private int imageHeight;
    private String userName;
    private String tags;
    private String comments;
    private String favorites;
    private String likes;
}
