package com.anbado.videoekisu.retrofit.service;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * @author by SeunOh on 15. 4. 25..
 */
public interface EkisuServices {


    @GET("/ekisus/")
    void getList(
            @Query("page") Integer page,
            Callback<JSONObject> callback);
}
