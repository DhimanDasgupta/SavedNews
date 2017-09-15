package com.dhimandasgupta.savednews.ui.dream;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.service.dreams.DreamService;
import android.support.annotation.Nullable;
import android.view.View;

import com.dhimandasgupta.savednews.R;
import com.dhimandasgupta.savednews.SavedNewsApp;
import com.dhimandasgupta.savednews.db.ArticleDatabaseCreator;
import com.dhimandasgupta.savednews.db.entity.ArticleEntity;
import com.dhimandasgupta.savednews.ui.view.TypeWriterTextView;

import java.util.ArrayList;
import java.util.List;

import static com.dhimandasgupta.savednews.ui.activity.MainActivity.getIntentFromDayDream;

/**
 * This class is a sample implementation of a DreamService. When activated, a
 * TextView will repeatedly, move from the left to the right of screen, at a
 * random y-value.
 * <p/>
 * Daydreams are only available on devices running API v17+.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class NewsDaydreamService extends DreamService {
    private TypeWriterTextView textView;

    private List<ArticleEntity> articleEntities = new ArrayList<>();

    private int index = 0;

    private ArticleLoadingAsyncTask articleLoadingAsyncTask;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            final ArticleEntity entity = getArticleEntity();
            if (entity != null) {
                textView.setTag(entity);
                textView.animateText(entity.getTitle());
            }
            handler.postDelayed(runnable, 10 * 1000);
        }
    };

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        // Exit dream upon user touch?
        setInteractive(true);

        // Hide system UI?
        setFullscreen(true);

        // Set the content view, just like you would with an Activity.
        setContentView(R.layout.daydream_news);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();

        hideSystemUI();

        textView = findViewById(R.id.dream_text_view);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArticleEntity entity = (ArticleEntity) view.getTag();
                startActivity(getIntentFromDayDream(view.getContext(), entity));

                finish();
            }
        });

        dimView(true, textView);

        articleLoadingAsyncTask = new ArticleLoadingAsyncTask(this);
        articleLoadingAsyncTask.execute();
    }

    @Override
    public void onDreamingStopped() {
        super.onDreamingStopped();

        handler.removeCallbacks(runnable);
        articleLoadingAsyncTask.cancel(true);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    // This snippet hides the system bars.
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        if (getWindow() != null && getWindow().getDecorView() != null) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    public void dimView(boolean dim, View clockView) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setColorFilter(new PorterDuffColorFilter(
                (dim ? 0x60FFFFFF : 0xC0FFFFFF),
                PorterDuff.Mode.MULTIPLY));
        clockView.setLayerType(View.LAYER_TYPE_HARDWARE, paint);
    }

    public void setNewList(@Nullable List<ArticleEntity> articleEntities) {
        this.articleEntities = articleEntities;

        handler.post(runnable);
    }

    @Nullable
    private ArticleEntity getArticleEntity() {
        if (articleEntities.size() == 0) {
            return null;
        } else if (index >= articleEntities.size() - 1) {
            index = 0;
        } else {
            index++;
        }

        return articleEntities.get(index);
    }

    private static class ArticleLoadingAsyncTask extends AsyncTask<Void, Void, List<ArticleEntity>> {
        private NewsDaydreamService newsDaydreamService;

        public ArticleLoadingAsyncTask(NewsDaydreamService newsDaydreamService) {
            this.newsDaydreamService = newsDaydreamService;
        }

        @Override
        protected List<ArticleEntity> doInBackground(Void... voids) {
            final ArticleDatabaseCreator creator = ArticleDatabaseCreator.getInstance(SavedNewsApp.getInstance());
            creator.createDbSync(SavedNewsApp.getInstance());
            return creator.getDatabase().getArticleDao().loadAllArticlesForTesting();
        }

        @Override
        protected void onPostExecute(List<ArticleEntity> articleEntities) {
            super.onPostExecute(articleEntities);

            newsDaydreamService.setNewList(articleEntities);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
