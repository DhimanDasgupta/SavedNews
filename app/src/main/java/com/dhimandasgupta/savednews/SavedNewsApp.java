package com.dhimandasgupta.savednews;

import android.app.Application;

/**
 * Created by dhimandasgupta on 09/09/17.
 */

public class SavedNewsApp extends Application {
    private static SavedNewsApp instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static SavedNewsApp getInstance() {
        return instance;
    }
}
