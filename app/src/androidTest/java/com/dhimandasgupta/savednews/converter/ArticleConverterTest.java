package com.dhimandasgupta.savednews.converter;

import com.dhimandasgupta.savednews.common.JsonReader;
import com.dhimandasgupta.savednews.db.entity.ArticleEntity;
import com.dhimandasgupta.savednews.rest.ArticleResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by dhimandasgupta on 12/09/17.
 */

@RunWith(JUnit4.class)
public class ArticleConverterTest {

    @Test
    public void testArticleConverter() {
        final ArticleResponse response = JsonReader.getArticleResponseSuccess();

        final List<ArticleEntity> entities = ArticleConverter.convertToArticleWithSourceList(response);

        assertEquals(10, entities.size());

        for (int i = 0; i < entities.size(); i++) {
            assertEquals(response.getSource(), entities.get(i).getSource());
            assertNotNull(entities.get(i).getPublishedAt());
            assertNotNull(entities.get(i).getDescription());
            assertNotNull(entities.get(i).getTitle());
            assertNotNull(entities.get(i).getUrl());
            assertNotNull(entities.get(i).getUrlToImage());
        }
    }
}
