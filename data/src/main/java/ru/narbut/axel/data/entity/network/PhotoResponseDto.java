package ru.narbut.axel.data.entity.network;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class PhotoResponseDto
{
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("pageURL")
    @Expose
    private String pageURL;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("downloads")
    @Expose
    private int downloads;
    @SerializedName("favorites")
    @Expose
    private int favorites;

    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("userImageURL")
    @Expose
    private String userImageURL;

    @SerializedName("largeImageURL")
    @Expose
    private String largeImageURL;
    @SerializedName("imageWidth")
    @Expose
    private int largeImageWidth;
    @SerializedName("imageHeight")
    @Expose
    private int largeImageHeight;
    @SerializedName("imageSize")
    @Expose
    private int largeImageSize;


    @SerializedName("previewURL")
    @Expose
    private String previewURL;
    @SerializedName("previewWidth")
    @Expose
    private int previewWidth;
    @SerializedName("previewHeight")
    @Expose
    private int previewHeight;


    @SerializedName("webformatURL")
    @Expose
    private String webformatURL;
    @SerializedName("webformatWidth")
    @Expose
    private int webformatWidth;
    @SerializedName("webformatHeight")
    @Expose
    private int webformatHeight;

    @SerializedName("likes")
    @Expose
    private int likes;

    @SerializedName("views")
    @Expose
    private int views;
    @SerializedName("comments")
    @Expose
    private int comments;


}