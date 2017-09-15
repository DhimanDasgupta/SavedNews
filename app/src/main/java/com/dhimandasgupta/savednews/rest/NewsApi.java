package com.dhimandasgupta.savednews.rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dhimandasgupta on 09/09/17.
 */

public class NewsApi {
    private static final String BASE_URL = "https://newsapi.org/v1/";
    public static final String NEWS_API_KEY = "16d6688d479a495e9e1c135aa037ec2d";
    //public static final String NEWS_API_KEY = "error_api_key_to_test_failure";

    private static Retrofit sRetrofit = null;

    private NewsApi() {

    }

    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            /**
             * Building the Logging Interceptor
             * */
            final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

            /**
             * Building the OkHttp Client for Network Layer
             * */
            final OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build();

            /**
             * Building the Retrofit object to making API calls
             * */
            sRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return sRetrofit;
    }
}
