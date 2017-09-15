package com.dhimandasgupta.savednews.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.VisibleForTesting;

import com.dhimandasgupta.savednews.db.entity.ArticleEntity;

import java.util.List;

/**
 * Created by dhimandasgupta on 09/09/17.
 */

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    LiveData<List<ArticleEntity>> loadAllArticles();

    @VisibleForTesting
    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    List<ArticleEntity> loadAllArticlesForTesting();

    @Query("SELECT * FROM articles where source = :source  ORDER BY publishedAt DESC")
    LiveData<List<ArticleEntity>> loadArticlesBySource(String source);

    @VisibleForTesting
    @Query("SELECT * FROM articles where source = :source  ORDER BY publishedAt DESC")
    List<ArticleEntity> loadArticlesBySourceForTesting(String source);

    @Query("SELECT * FROM articles")
    List<ArticleEntity> loadAllArticlesToDelete();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticles(List<ArticleEntity> articleEntities);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateArticles(List<ArticleEntity> articleEntities);

    @Delete
    void deleterArticles(List<ArticleEntity> articleEntities);
}
