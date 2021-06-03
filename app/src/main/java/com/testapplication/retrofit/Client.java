package com.testapplication.retrofit;

import com.testapplication.retrofit.errorAdapters.RxErrorHandlingCallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.testapplication.preferenceHelper.PreferenceHelper.getTheJwtToken;

public class Client {

    private static int REQUEST_TIMEOUT = 60;

    public static final String BASE_LIVE_URL = "http://ticketing-app-abj.herokuapp.com/api/";

    private static Retrofit retrofit = null;

    private static OkHttpClient okHttpClient;

    public static Retrofit getClient() {
        if(okHttpClient == null){
            initOkHttp();
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_LIVE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .build();

        return retrofit;
    }

    private static void initOkHttp(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Accept", "*/*")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + getTheJwtToken());

            Request request = requestBuilder.build();

            return chain.proceed(request);
        });

        okHttpClient = httpClient.build();
    }

}
