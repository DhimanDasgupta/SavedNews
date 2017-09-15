package com.dhimandasgupta.savednews.converter;

import com.dhimandasgupta.savednews.db.entity.ArticleEntity;
import com.dhimandasgupta.savednews.rest.ArticleResponse;
import com.dhimandasgupta.savednews.rest.ArticleRestImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dhimandasgupta on 09/09/17.
 */

public class ArticleConverter {
    public static List<ArticleEntity> convertToArticleWithSourceList(final ArticleResponse response) {
        List<ArticleEntity> articleWithSources = null;

        if (response == null) {
            articleWithSources = Collections.emptyList();
        } else {
            articleWithSources = new ArrayList<>(response.getArticles().size());

            final List<ArticleRestImpl> articles = response.getArticles();

            for (int i = 0; i < articles.size(); i++) {
                final ArticleEntity entity = new ArticleEntity(articles.get(i), response.getSource());
                articleWithSources.add(entity);
            }
        }

        return articleWithSources;
    }
}
