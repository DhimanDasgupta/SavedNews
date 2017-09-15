package com.dhimandasgupta.savednews.ui.utils;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.dhimandasgupta.savednews.db.entity.ArticleEntity;

import java.util.List;

/**
 * Created by dhimandasgupta on 09/09/17.
 */

public class ArticleDiffUtilCallback extends DiffUtil.Callback {
    final List<ArticleEntity> oldArticleEntities;
    final List<ArticleEntity> newArticleEntities;

    public ArticleDiffUtilCallback(final List<ArticleEntity> oldArticleEntities, final List<ArticleEntity> newArticleEntities) {
        this.oldArticleEntities = oldArticleEntities;
        this.newArticleEntities = newArticleEntities;
    }

    @Override
    public int getOldListSize() {
        return oldArticleEntities.size();
    }

    @Override
    public int getNewListSize() {
        return newArticleEntities.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldArticleEntities.get(oldItemPosition).getId() == newArticleEntities.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldArticleEntities.get(oldItemPosition).equals(newArticleEntities.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
