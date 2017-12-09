package com.oldmen.testexainsoft;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by MVP on 09.12.2017.
 */

public class RetrofitClient {

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
    }

    public static ApiService getApiService(Context context) {
        return getRetrofitInstance().create(ApiService.class);
    }



}
