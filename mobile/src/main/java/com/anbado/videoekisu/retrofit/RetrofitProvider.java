package com.anbado.videoekisu.retrofit;

import com.anbado.videoekisu.BuildConfig;
import com.anbado.videoekisu.retrofit.converter.JSONObjectConverter;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * @author by SeunOh on 15. 4. 25..
 */
public final class RetrofitProvider {


    private static volatile RetrofitProvider sInstance;
    private RestAdapter mRestAdapter;

    private RetrofitProvider() {

    }

    public static RetrofitProvider getInstance() {
        if (sInstance == null) {
            sInstance = new RetrofitProvider();
        }

        return sInstance;
    }

    public static void initialize(String endpoint) {
        getInstance().mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setClient(new OkClient())
                .setConverter(new JSONObjectConverter())
                .setLogLevel(BuildConfig.DEBUG ?
                        RestAdapter.LogLevel.BASIC : RestAdapter.LogLevel.NONE)
                .build();
    }


    public static <T> T create(Class<T> service) {
        return getInstance().mRestAdapter.create(service);
    }


}
