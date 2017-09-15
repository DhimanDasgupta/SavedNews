package com.dhimandasgupta.savednews.ui.activity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dhimandasgupta.savednews.R;
import com.dhimandasgupta.savednews.SavedNewsApp;
import com.dhimandasgupta.savednews.db.ArticleDatabase;
import com.dhimandasgupta.savednews.db.ArticleDatabaseCreator;
import com.dhimandasgupta.savednews.db.dao.ArticleDao;
import com.dhimandasgupta.savednews.db.entity.ArticleEntity;
import com.dhimandasgupta.savednews.jobs.DeleteJobService;
import com.dhimandasgupta.savednews.jobs.ImmediateJobService;
import com.dhimandasgupta.savednews.jobs.PeriodicJobService;
import com.dhimandasgupta.savednews.prefs.JobPref;
import com.dhimandasgupta.savednews.ui.adapter.ArticleAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String DAY_DREAM_EXTRA_FLAG = "from_day_dream";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private ArticleAdapter articleAdapter;

    public static Intent getIntentFromDayDream(final Context context, final ArticleEntity entity) {
        final Intent intent = new Intent(context, MainActivity.class);
        if (entity != null) {
            intent.putExtra(DAY_DREAM_EXTRA_FLAG, entity.getTitle());
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().hasExtra(DAY_DREAM_EXTRA_FLAG)) {
            final String title = getIntent().getStringExtra(DAY_DREAM_EXTRA_FLAG);
            if (title != null) {
                Toast.makeText(this, title, Toast.LENGTH_LONG).show();
            }
        }

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);

        articleAdapter = new ArticleAdapter();

        /*
        final RecyclerView.ItemAnimator animator = new ArticleItemAnimator();
        animator.setAddDuration(1000);
        animator.setChangeDuration(300);
        animator.setMoveDuration(300);
        animator.setRemoveDuration(1000);
        */

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        //recyclerView.setItemAnimator(animator);
        recyclerView.setAdapter(articleAdapter);

        setUpObservers();
        startArticleFetchJob();
        enqueuePeriodicArticleFetchJob();
        enqueueDeleteJob();
        showLastUpdatedTime();
    }

    private void showLastUpdatedTime() {
        Snackbar.make(recyclerView, JobPref.getLastUpdatedTime(SavedNewsApp.getInstance()), 1000).show();
    }

    private void setUpObservers() {
        final ArticleDatabase database = ArticleDatabaseCreator.getInstance(SavedNewsApp.getInstance()).getDatabase();
        final ArticleDao dao = database.getArticleDao();
        dao.loadAllArticles().observe(this, new Observer<List<ArticleEntity>>() {
            @Override
            public void onChanged(@Nullable List<ArticleEntity> articleEntities) {
                if (articleEntities != null && articleEntities.size() > 0) {
                    progressBar.setVisibility(View.GONE);
                }

                articleAdapter.addArticles(articleEntities);
            }
        });
    }

    private void startArticleFetchJob() {
        final PersistableBundle bundle = new PersistableBundle();
        bundle.putString("source", "abc-news-au");

        final JobInfo job = new JobInfo.Builder(ImmediateJobService.IMMEDIATE_JOB_ID,
                new ComponentName(SavedNewsApp.getInstance(), ImmediateJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setExtras(bundle)
                .setRequiresCharging(false)
                .setMinimumLatency(1)
                .setOverrideDeadline(1)
                .build();

        final JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        final List<JobInfo> pendingJobs = jobScheduler.getAllPendingJobs();
        for (int i = 0; i < pendingJobs.size(); i++) {
            if (pendingJobs.get(i).getId() == ImmediateJobService.IMMEDIATE_JOB_ID) {
                jobScheduler.cancel(ImmediateJobService.IMMEDIATE_JOB_ID);
            }
        }
        jobScheduler.schedule(job);
    }

    private void enqueuePeriodicArticleFetchJob() {
        final PersistableBundle bundle = new PersistableBundle();
        bundle.putString("source", "abc-news-au");

        final JobInfo job = new JobInfo.Builder(PeriodicJobService.PERIODIC_JOB_ID,
                new ComponentName(SavedNewsApp.getInstance(), ImmediateJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_METERED)
                .setExtras(bundle)
                .setRequiresCharging(false)
                .setMinimumLatency(1 * 60 * 60 * 1000) // 1 Hour
                .setOverrideDeadline(2 * 60 * 60 * 1000) // 2 Hours
                .setPersisted(true)
                .build();

        final JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        final List<JobInfo> pendingJobs = jobScheduler.getAllPendingJobs();
        for (int i = 0; i < pendingJobs.size(); i++) {
            if (pendingJobs.get(i).getId() == PeriodicJobService.PERIODIC_JOB_ID) {
                jobScheduler.cancel(PeriodicJobService.PERIODIC_JOB_ID);
            }
        }
        jobScheduler.schedule(job);
    }

    private void enqueueDeleteJob() {
        final JobInfo job = new JobInfo.Builder(DeleteJobService.DELETE_JOB_ID,
                new ComponentName(SavedNewsApp.getInstance(), DeleteJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                .setRequiresCharging(false)
                .setMinimumLatency(1) // 24 Hours
                .setOverrideDeadline(1) // 24 Hours
                //.setMinimumLatency(24 * 60 * 60 * 1000) // 24 Hours
                //.setOverrideDeadline(24 * 60 * 60 * 1000) // 24 Hours
                .setPersisted(true)
                .build();

        final JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        final List<JobInfo> pendingJobs = jobScheduler.getAllPendingJobs();
        for (int i = 0; i < pendingJobs.size(); i++) {
            if (pendingJobs.get(i).getId() == DeleteJobService.DELETE_JOB_ID) {
                jobScheduler.cancel(DeleteJobService.DELETE_JOB_ID);
            }
        }
        jobScheduler.schedule(job);
    }
}
