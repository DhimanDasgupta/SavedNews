package com.dhimandasgupta.savednews.ui.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dhimandasgupta.savednews.R;
import com.dhimandasgupta.savednews.converter.TimeConverter;
import com.dhimandasgupta.savednews.db.entity.ArticleEntity;
import com.dhimandasgupta.savednews.ui.utils.ArticleDiffUtilCallback;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dhimandasgupta on 09/09/17.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    final List<ArticleEntity> entities = new ArrayList<>();

    public ArticleAdapter() {

    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.adapter_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.bind(entities.get(position));
    }

    @Override
    public int getItemCount() {
        return entities.size();
    }

    public void addArticles(List<ArticleEntity> newEntities) {
        final ArticleDiffUtilCallback callback = new ArticleDiffUtilCallback(entities, newEntities);
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        entities.clear();
        entities.addAll(newEntities);

        result.dispatchUpdatesTo(this);
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mDescription;
        private TextView publishedAt;

        public ArticleViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.text_view_title);
            mDescription = itemView.findViewById(R.id.text_view_description);
            publishedAt = itemView.findViewById(R.id.text_view_published);
        }

        public void bind(final ArticleEntity article) {
            mTitle.setText(article.getTitle());
            mDescription.setText(article.getDescription());
            publishedAt.setText(DateUtils.getRelativeTimeSpanString(TimeConverter.convertToMillis(article.getPublishedAt()), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));
        }
    }
}
