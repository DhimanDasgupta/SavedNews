package com.dhimandasgupta.savednews.model;

/**
 * Created by dhimandasgupta on 09/09/17.
 */

public interface Article {
    String getAuthor();
    String getTitle();
    String getDescription();
    String getUrl();
    String getUrlToImage();
    String getPublishedAt();
}
