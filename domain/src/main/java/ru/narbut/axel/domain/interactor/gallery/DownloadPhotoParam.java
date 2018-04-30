package ru.narbut.axel.domain.interactor.gallery;

import lombok.Getter;

@Getter
public class DownloadPhotoParam {
  private String name;
  private String photoUrl;

  public DownloadPhotoParam(String name, String photoUrl) {
    this.name = name;
    this.photoUrl = photoUrl;
  }

  public static DownloadPhotoParam forPhoto( String name, String photoUrl) {
    return new DownloadPhotoParam(name,photoUrl);
  }
}
