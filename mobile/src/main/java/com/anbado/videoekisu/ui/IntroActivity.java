package com.anbado.videoekisu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.anbado.videoekisu.R;
import com.anbado.videoekisu.app.BaseActivity;
import com.anbado.videoekisu.ui.main.MainActivity;


/**
 * @author by SeunOh on 15. 4. 25..
 */
public class IntroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
                finish();
            }
        }, 1000);
    }


    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
