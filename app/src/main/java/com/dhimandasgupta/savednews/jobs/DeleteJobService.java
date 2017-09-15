package com.dhimandasgupta.savednews.jobs;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Log;

import com.dhimandasgupta.savednews.SavedNewsApp;
import com.dhimandasgupta.savednews.converter.TimeConverter;
import com.dhimandasgupta.savednews.db.ArticleDatabase;
import com.dhimandasgupta.savednews.db.ArticleDatabaseCreator;
import com.dhimandasgupta.savednews.db.entity.ArticleEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhimandasgupta on 10/09/17.
 */

public class DeleteJobService extends JobService {
    public static final int DELETE_JOB_ID = 102;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        new DeleteJobService.DeleteAsync(this).execute(jobParameters);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    private static class DeleteAsync extends AsyncTask<JobParameters, Void, JobParameters> {
        private JobService jobService;

        public DeleteAsync(JobService service) {
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

            final ArticleDatabase database = ArticleDatabaseCreator.getInstance(SavedNewsApp.getInstance()).getDatabase();
            final List<ArticleEntity> entities = database.getArticleDao().loadAllArticlesToDelete();

            final long currentTime = System.currentTimeMillis();
            final long afterThirtyDayTime = currentTime + 10 * 60 * 60 * 1000;//30 * 24 * 60 * 60 * 1000;

            final List<ArticleEntity> entriesOlderThanThirtyDays = new ArrayList<>();
            for (int i = 0; i < entities.size(); i++) {
                final long publishedTime = TimeConverter.convertToMillis(entities.get(i).getPublishedAt());
                final long diff = publishedTime - afterThirtyDayTime;

                if (diff > 2592000000l) {
                    entriesOlderThanThirtyDays.add(entities.get(i));
                }
            }

            if (entriesOlderThanThirtyDays.size() > 0) {
                database.getArticleDao().deleterArticles(entities);
            }

            Log.d("Job", "Articles deleted");

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
