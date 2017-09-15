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
    private static final long DURATION = 20 * 1000;
    private static final int DIM_HIGHEST = 0x30FFFFFF;
    private static final int DIM_MODERATE = 0x70FFFFFF;
    private static final int DIM_LOWEST = 0xA0FFFFFF;

    private TypeWriterTextView titleTextView;
    private View separatorView;
    private TypeWriterTextView descriptionTextView;

    private List<ArticleEntity> articleEntities = new ArrayList<>();

    private int index = 0;

    private ArticleLoadingAsyncTask articleLoadingAsyncTask;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            final ArticleEntity entity = getArticleEntity();
            if (entity != null) {
                titleTextView.setTag(entity);
                titleTextView.animateText(entity.getTitle());

                descriptionTextView.setTag(entity);
                descriptionTextView.animateText(entity.getDescription());
            }
            handler.postDelayed(runnable, DURATION);
        }
    };

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        setInteractive(true);   // Exit dream upon user touch?
        setFullscreen(true);    // Hide system UI?

        setContentView(R.layout.daydream_news); // Set the content view, just like you would with an Activity.
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();

        hideSystemUI();

        titleTextView = findViewById(R.id.dream_title_text_view);
        dimView(titleTextView, DIM_LOWEST);
        titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArticleEntity entity = (ArticleEntity) view.getTag();
                startActivity(getIntentFromDayDream(view.getContext(), entity));

                finish();
            }
        });

        separatorView = findViewById(R.id.separator_view);
        dimView(separatorView, DIM_HIGHEST);


        descriptionTextView = findViewById(R.id.dream_description_text_view);
        dimView(descriptionTextView, DIM_MODERATE);
        descriptionTextView.setCharacterDelay(16);
        descriptionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArticleEntity entity = (ArticleEntity) view.getTag();
                startActivity(getIntentFromDayDream(view.getContext(), entity));

                finish();
            }
        });

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

    // This dims a View with specified amount.
    public void dimView(final View clockView, final int dimAmount) {
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setColorFilter(new PorterDuffColorFilter(dimAmount, PorterDuff.Mode.MULTIPLY));
        clockView.setLayerType(View.LAYER_TYPE_HARDWARE, paint);
    }

    public void setNewList(@Nullable List<ArticleEntity> articleEntities) {
        this.articleEntities.clear();
        this.articleEntities.addAll(articleEntities);

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
