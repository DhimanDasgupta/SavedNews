package com.dhimandasgupta.savednews.creator;

import com.dhimandasgupta.savednews.model.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhimandasgupta on 09/09/17.
 */

public class NewsCreator {
    public static News getANews() {
        return new News("ABC News (AU)", "abc-news-au");
    }

    public static List<News> getNews() {
        final List<News> newses = new ArrayList<>();

        newses.add(new News("ABC News (AU)", "abc-news-au"));
        newses.add(new News("Al Jazeera English", "al-jazeera-english"));
        newses.add(new News("Ars Technica", "ars-technica"));
        newses.add(new News("Associated Press", "associated-press"));
        newses.add(new News("BBC News", "bbc-news"));
        newses.add(new News("BBC Sport", "bbc-sport"));
        newses.add(new News("Bild", "bild"));
        newses.add(new News("Bloomberg", "bloomberg"));
        newses.add(new News("Breitbart News", "breitbart-news"));
        newses.add(new News("Business Insider", "business-insider"));
        newses.add(new News("Buzzfeed", "buzzfeed"));
        newses.add(new News("CNBC", "cnbc"));

        return newses;
    }
}
