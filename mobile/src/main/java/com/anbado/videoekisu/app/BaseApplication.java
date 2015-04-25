package com.anbado.videoekisu.app;

import android.app.Application;

import com.anbado.videoekisu.Config;
import com.anbado.videoekisu.retrofit.RetrofitProvider;

/**
 * @author by SeunOh on 15. 4. 25..
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitProvider.initialize(Config.END_POINT);
    }
}
