package com.dhimandasgupta.savednews.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.dhimandasgupta.savednews.db.dao.ArticleDao;
import com.dhimandasgupta.savednews.db.entity.ArticleEntity;

/**
 * Created by dhimandasgupta on 09/09/17.
 */

@Database(entities = {ArticleEntity.class}, version = 1, exportSchema = false)
public abstract class ArticleDatabase extends RoomDatabase {
    static final String DATABASE_NAME = "article-db";

    public abstract ArticleDao getArticleDao();

    @VisibleForTesting
    public static ArticleDatabase createForTesting(Context context) {
        final RoomDatabase.Builder<ArticleDatabase> builder = Room.inMemoryDatabaseBuilder(context, ArticleDatabase.class);

        return builder.build();
    }
}
