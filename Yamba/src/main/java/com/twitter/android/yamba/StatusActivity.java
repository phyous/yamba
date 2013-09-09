package com.twitter.android.yamba;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class StatusActivity extends Activity {
    private final String TAG = StatusActivity.class.getName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        if(BuildConfig.DEBUG) {Log.d(TAG, "Created!");}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.status, menu);
        return true;
    }
}