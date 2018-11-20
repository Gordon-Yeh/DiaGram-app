package com.example.ewd.diagram.model.remote.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton class that returns a Retrofit instance
 */
public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://diagram.eastus.cloudapp.azure.com";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
