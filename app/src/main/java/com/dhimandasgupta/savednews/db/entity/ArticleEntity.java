package com.dhimandasgupta.savednews.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.dhimandasgupta.savednews.model.Article;
import com.dhimandasgupta.savednews.model.ArticleWithSource;

/**
 * Created by dhimandasgupta on 09/09/17.
 */

@Entity(tableName = "articles")
public class ArticleEntity implements ArticleWithSource {
    @PrimaryKey
    private String id;
    private String source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public ArticleEntity() {

    }

    public ArticleEntity(final Article article, final String articleSource) {
        source = articleSource;
        id = articleSource + article.getTitle() + article.getDescription();
        author = article.getAuthor();
        title = article.getTitle();
        description = article.getDescription();
        url = article.getUrl();
        urlToImage = article.getUrlToImage();
        publishedAt = article.getPublishedAt();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUrlToImage() {
        return urlToImage;
    }

    @Override
    public String getPublishedAt() {
        return publishedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
