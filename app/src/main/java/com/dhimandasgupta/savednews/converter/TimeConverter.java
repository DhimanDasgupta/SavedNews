package com.dhimandasgupta.savednews.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by dhimandasgupta on 10/09/17.
 */

public class TimeConverter {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static long convertToMillis(final String time) {
        String formattedTime = time;

        if (time.endsWith("z") || time.endsWith("Z")) {
            formattedTime = formattedTime.substring(0, formattedTime.length() - 1);
        }
        try {
            return DATE_FORMAT.parse(formattedTime).getTime();
        } catch (ParseException e) {
            return System.currentTimeMillis();
        }
    }
}
