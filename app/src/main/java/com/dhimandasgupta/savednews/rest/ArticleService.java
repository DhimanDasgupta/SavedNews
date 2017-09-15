package com.dhimandasgupta.savednews.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dhimandasgupta on 09/09/17.
 */

public interface ArticleService {
    /**
     * Calling the news API endpoint to get the list of news from a particular source
     * */
    @GET("articles?&apiKey=" + NewsApi.NEWS_API_KEY)
    Call<ArticleResponse> getArticlesBySource(@Query("source") String source);
}
