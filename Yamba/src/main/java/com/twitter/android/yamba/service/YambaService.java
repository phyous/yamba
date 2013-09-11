package com.twitter.android.yamba.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.twitter.android.yamba.BuildConfig;
import com.twitter.android.yamba.R;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

/**
 * Created by pyoussef on 9/11/13.
 */
public class YambaService extends IntentService {
    public static final String TAG = "SVC";

    private static final String PARAM_STATUS = "YambaService.STAUTS";
    private static volatile YambaClient client;
    static {
         client = new YambaClient("student", "password");
    }

    public static void post(Context ctxt, String status) {
        Intent i = new Intent(ctxt, YambaService.class);
        i.putExtra(PARAM_STATUS, status);
        ctxt.startService(i);
    }

    public YambaService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String postStatus = intent.getStringExtra(PARAM_STATUS);
        if (BuildConfig.DEBUG) {Log.d(TAG, "posting: " + postStatus);}

        try {
            client.postStatus(postStatus);
        } catch (YambaClientException e) {
            e.printStackTrace();
        }

    }
}
