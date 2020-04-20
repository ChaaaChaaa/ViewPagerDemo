package com.example.viewpagerdemo.boxoffice;

import com.example.viewpagerdemo.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.viewpagerdemo.BuildConfig.BASEURL;


public class RestClient {
    public static Retrofit retrofit = null;
    private HttpLoggingInterceptor loggingInterceptor;
    public static BoxOfficeService buildHTTPClient() {

        //Network io logging을 위한 작업
        //App:Gradle에 추가된 라이브러리 확인하기
        // 버전은 신규 라이브러리 추가면 가능한한 최신버전 쓰기~
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.interceptors().add(logging);
/////


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit.create(BoxOfficeService.class);
    }
}