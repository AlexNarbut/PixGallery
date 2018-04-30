package ru.narbut.axel.domain.interactor.gallery;

import lombok.Getter;

@Getter
public class DiscoverPhotoParams {
  private int page;
  private String lang;
  private String query;

  public DiscoverPhotoParams(int page, String lang, String query) {
    this.page = page;
    this.lang = lang;
    this.query = query;
  }

  public static DiscoverPhotoParams forPhoto(int page,String lang,String query) {
    return new DiscoverPhotoParams(page,lang,query);
  }
}
