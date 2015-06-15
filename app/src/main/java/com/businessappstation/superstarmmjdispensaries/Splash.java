package com.businessappstation.superstarmmjdispensaries;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;


public class Splash extends ActionBarActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainActivity = new Intent(Splash.this, MainActivity.class);
                Splash.this.startActivity(mainActivity);
                Splash.this.finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }


}
