package com.dhimandasgupta.savednews.db;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dhimandasgupta.savednews.common.JsonReader;
import com.dhimandasgupta.savednews.converter.ArticleConverter;
import com.dhimandasgupta.savednews.db.dao.ArticleDao;
import com.dhimandasgupta.savednews.db.entity.ArticleEntity;
import com.dhimandasgupta.savednews.rest.ArticleResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by dhimandasgupta on 11/09/17.
 */

@RunWith(AndroidJUnit4.class)
public class ArticleDatabaseTest {
    ArticleDatabase articleDatabase;
    ArticleDao articleDao;

    @Before
    public void setUp() {
        articleDatabase = ArticleDatabase.createForTesting(InstrumentationRegistry.getContext());
        articleDao = articleDatabase.getArticleDao();
    }

    @Test
    public void initialTest() throws FileNotFoundException {
        assertEquals( 0, articleDao.loadAllArticlesForTesting().size());
        assertEquals( 0, articleDao.loadArticlesBySourceForTesting("abc-news-au").size());
    }

    @Test
    public void singleInsertTest() {
        final List<ArticleEntity> entities = new ArrayList<>();
        entities.add(createArticleEntity());

        articleDao.insertArticles(entities);

        // Should be equal to 1
        assertEquals(1, articleDao.loadAllArticlesForTesting().size());
        assertEquals(1, articleDao.loadArticlesBySourceForTesting("abc-news-au").size());

        // Should be equal to 0
        assertEquals(0, articleDao.loadArticlesBySourceForTesting("non_existing_source").size());
        assertNotEquals(1, articleDao.loadArticlesBySourceForTesting("non_existing_source").size());

        final List<ArticleEntity> articleResponses = articleDao.loadAllArticlesForTesting();
        final ArticleEntity entity = articleResponses.get(0);

        assertEquals("abc-news-au", entity.getSource());
        assertEquals("article_id", entity.getId());
        assertEquals("this is description", entity.getDescription());
        assertEquals("2017-01-13'T'11:59:10", entity.getPublishedAt());
        assertEquals("this is title", entity.getTitle());
        assertEquals("https://www.whatever.image_link.jpg", entity.getUrl());
        assertEquals("https://www.whatever.url_to_image_link.jpg", entity.getUrlToImage());
    }

    @Test
    public void singleUpdateTest() {
        final List<ArticleEntity> entities = new ArrayList<>();
        entities.add(createArticleEntity());

        articleDao.insertArticles(entities);

        assertEquals(1, articleDao.loadAllArticlesForTesting().size());

        final List<ArticleEntity> articleResponses = articleDao.loadAllArticlesForTesting();
        final ArticleEntity entity = articleResponses.get(0);

        assertEquals("abc-news-au", entity.getSource());
        assertEquals("article_id", entity.getId());
        assertEquals("this is description", entity.getDescription());
        assertEquals("2017-01-13'T'11:59:10", entity.getPublishedAt());
        assertEquals("this is title", entity.getTitle());
        assertEquals("https://www.whatever.image_link.jpg", entity.getUrl());
        assertEquals("https://www.whatever.url_to_image_link.jpg", entity.getUrlToImage());

        final List<ArticleEntity> entities1 = new ArrayList<>();
        entities1.add(createAnotherArticleEntity());
        articleDao.insertArticles(entities1);

        final List<ArticleEntity> articleResponses1 = articleDao.loadAllArticlesForTesting();
        final ArticleEntity entity1 = articleResponses1.get(0);

        assertEquals("abc-news-au_01", entity1.getSource());
        assertEquals("article_id_01", entity1.getId());
        assertEquals("this is description_01", entity1.getDescription());
        assertEquals("2017-01-13'T'11:59:10_01", entity1.getPublishedAt());
        assertEquals("this is title_01", entity1.getTitle());
        assertEquals("https://www.whatever.image_link_01.jpg", entity1.getUrl());
        assertEquals("https://www.whatever.url_to_image_link_01.jpg", entity1.getUrlToImage());
    }

    @Test
    public void singleDeleteTest() {
        final List<ArticleEntity> entities = new ArrayList<>();
        entities.add(createArticleEntity());

        articleDao.insertArticles(entities);

        assertEquals(1, articleDao.loadAllArticlesForTesting().size());

        articleDao.deleterArticles(entities);

        assertEquals(0, articleDao.loadAllArticlesForTesting().size());
    }

    @After
    public void tearDown() {
        articleDatabase.close();
        articleDatabase = null;
        articleDao = null;
    }

    public ArticleEntity createArticleEntity() {
        final ArticleEntity entity = new ArticleEntity();

        entity.setId("article_id");
        entity.setAuthor("dhiman dasgupta");
        entity.setDescription("this is description");
        entity.setPublishedAt("2017-01-13'T'11:59:10");
        entity.setSource("abc-news-au");
        entity.setTitle("this is title");
        entity.setUrl("https://www.whatever.image_link.jpg");
        entity.setUrlToImage("https://www.whatever.url_to_image_link.jpg");

        return entity;
    }

    public ArticleEntity createAnotherArticleEntity() {
        final ArticleEntity entity = new ArticleEntity();

        entity.setId("article_id_01");
        entity.setAuthor("dhiman dasgupta_01");
        entity.setDescription("this is description_01");
        entity.setPublishedAt("2017-01-13'T'11:59:10_01");
        entity.setSource("abc-news-au_01");
        entity.setTitle("this is title_01");
        entity.setUrl("https://www.whatever.image_link_01.jpg");
        entity.setUrlToImage("https://www.whatever.url_to_image_link_01.jpg");

        return entity;
    }

    public List<ArticleEntity> getArticleEntities() {
        final ArticleResponse articleResponse = JsonReader.getArticleResponseSuccess();
        final List<ArticleEntity> articleEntities = ArticleConverter.convertToArticleWithSourceList(articleResponse);
        return articleEntities;
    }
}
