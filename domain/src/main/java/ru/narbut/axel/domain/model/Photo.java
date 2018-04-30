package ru.narbut.axel.domain.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Photo {

    private int id;
    private String pageURL;
    private String type;
    private String tags;
    private int favorites;

    private int userId;
    private String userName;
    private String userImageURL;

    private String imageURL;
    private int imageWidth;
    private int imageHeight;
    private int imageSize;

    private String previewURL;
    private int previewHeight;
    private int previewWidth;

    private int likes;
    private int comments;
    private int views;




    public Photo() {}
}
