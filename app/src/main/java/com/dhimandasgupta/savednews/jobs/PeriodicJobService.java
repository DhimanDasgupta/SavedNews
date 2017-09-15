package com.dhimandasgupta.savednews.jobs;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.util.Log;

import com.dhimandasgupta.savednews.SavedNewsApp;
import com.dhimandasgupta.savednews.converter.ArticleConverter;
import com.dhimandasgupta.savednews.db.ArticleDatabase;
import com.dhimandasgupta.savednews.db.ArticleDatabaseCreator;
import com.dhimandasgupta.savednews.db.entity.ArticleEntity;
import com.dhimandasgupta.savednews.prefs.JobPref;
import com.dhimandasgupta.savednews.rest.ArticleResponse;
import com.dhimandasgupta.savednews.rest.ArticleService;
import com.dhimandasgupta.savednews.rest.NewsApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by dhimandasgupta on 10/09/17.
 */

public class PeriodicJobService extends JobService {
    public static final int PERIODIC_JOB_ID = 101;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        new PeriodicJobService.ArticleAsyncTask(this).execute(jobParameters);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    private static class ArticleAsyncTask extends AsyncTask<JobParameters, Void, JobParameters> {
        private JobService jobService;

        public ArticleAsyncTask(JobService service) {
            jobService = service;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d("Job", "Immediate Job Started");
        }

        @Override
        protected JobParameters doInBackground(JobParameters... params) {
            final JobParameters parameters = params[0];

            try {
                if (parameters.getExtras() != null) {
                    final PersistableBundle bundle = parameters.getExtras();
                    final String source = bundle.getString("source");

                    final Retrofit retrofit = NewsApi.getRetrofit();
                    final ArticleService service = retrofit.create(ArticleService.class);

                    final Response<ArticleResponse> response = service.getArticlesBySource(source).execute();

                    final ArticleDatabaseCreator creator = ArticleDatabaseCreator.getInstance(SavedNewsApp.getInstance());
                    if (creator.getDatabase() == null) {
                        creator.createDbSync(SavedNewsApp.getInstance());
                    }

                    if (response.isSuccessful()) {
                        final List<ArticleEntity> entities = ArticleConverter.convertToArticleWithSourceList(response.body());

                        final ArticleDatabase database = ArticleDatabaseCreator.getInstance(SavedNewsApp.getInstance()).getDatabase();
                        database.getArticleDao().insertArticles(entities);
                    }

                    JobPref.saveLastUpdatedTime(SavedNewsApp.getInstance(), System.currentTimeMillis());

                    Log.d("Job", "Articles inserted");
                }
            } catch (IOException ioe) {

            }

            return parameters;
        }

        @Override
        protected void onPostExecute(JobParameters parameters) {
            super.onPostExecute(parameters);

            Log.d("Job", "Immediate Job Stopped");
            jobService.jobFinished(parameters, false);
        }
    }
}
