package com.anbado.videoekisu.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

/**
 * @author by SeunOh on 15. 4. 25..
 */
public class SimpleOnInteractionListener {

    private Activity mActivity;

    public SimpleOnInteractionListener(Activity activity) {
        mActivity = activity;
    }

    public void startActivity(Intent intent, @Nullable Bundle options) {
        ActivityCompat.startActivity(mActivity, intent, options);
    }

}
