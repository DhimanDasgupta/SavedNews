package com.dhimandasgupta.savednews.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateUtils;

/**
 * Created by dhimandasgupta on 10/09/17.
 */

public class JobPref {
    private static final String PREF_NAME = "news_pref";

    private static final String LAST_UPDATED = "last_updated";

    public static void saveLastUpdatedTime(final Context context, long time) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        editor.putLong(LAST_UPDATED, time);

        editor.apply();
    }

    public  static String getLastUpdatedTime(Context context) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        final long lastUpdated = preferences.getLong(LAST_UPDATED, -1);

        if (lastUpdated < 0) {
            return "Last Updated : NA";
        } else {
            return String.valueOf(DateUtils.getRelativeTimeSpanString(lastUpdated, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));
        }
    }
}
