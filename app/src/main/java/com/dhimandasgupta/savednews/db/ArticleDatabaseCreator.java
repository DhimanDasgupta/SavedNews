package com.dhimandasgupta.savednews.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.dhimandasgupta.savednews.db.ArticleDatabase.DATABASE_NAME;

/**
 * Created by dhimandasgupta on 09/09/17.
 */

public class ArticleDatabaseCreator {
    private static ArticleDatabaseCreator sInstance;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    private ArticleDatabase mDb;

    private final AtomicBoolean mInitializing = new AtomicBoolean(true);

    // For Singleton instantiation
    private static final Object LOCK = new Object();

    public synchronized static ArticleDatabaseCreator getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new ArticleDatabaseCreator();
                }
            }
        }
        return sInstance;
    }

    /** Used to observe when the database initialization is done */
    public LiveData<Boolean> isDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    @Nullable
    public ArticleDatabase getDatabase() {
        return mDb;
    }

    /**
     * Creates or returns a previously-created database.
     * <p>
     * Although this uses an AsyncTask which currently uses a serial executor, it's thread-safe.
     */
    public void createDb(Context context) {

        Log.d("DatabaseCreator", "Creating DB from " + Thread.currentThread().getName());

        if (!mInitializing.compareAndSet(true, false)) {
            return; // Already initializing
        }

        mIsDatabaseCreated.setValue(false);// Trigger an update to show a loading screen.
        new AsyncTask<Context, Void, Void>() {

            @Override
            protected Void doInBackground(Context... params) {
                Context context = params[0].getApplicationContext();

                // Build the database!
                ArticleDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                        ArticleDatabase.class, DATABASE_NAME).build();
                mDb = db;
                return null;
            }

            @Override
            protected void onPostExecute(Void ignored) {
                // Now on the main thread, notify observers that the db is created and ready.
                mIsDatabaseCreated.setValue(true);
            }
        }.execute(context.getApplicationContext());
    }

    public void createDbSync(Context context) {
        // Build the database!
        ArticleDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                ArticleDatabase.class, DATABASE_NAME).build();
        mDb = db;
    }
}
