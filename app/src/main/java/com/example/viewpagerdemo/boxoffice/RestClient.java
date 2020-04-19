package com.example.viewpagerdemo.boxoffice;

import com.example.viewpagerdemo.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.viewpagerdemo.BuildConfig.BASEURL;


public class RestClient {
    public static Retrofit retrofit = null;

    public static BoxOfficeService buildHTTPClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(BoxOfficeService.class);
    }
}