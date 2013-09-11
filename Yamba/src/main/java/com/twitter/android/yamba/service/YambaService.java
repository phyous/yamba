package com.twitter.android.yamba.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

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
    private static final int OP_POST_COMPLETE = -1;

    // A handler to send messages back to the UI thread.
    private Handler mHandler;

    private static YambaClient sClient;
    private static YambaClient getClient() {
        if (sClient == null) {
            synchronized (YambaService.class) {
                if (sClient == null) {
                    sClient = new YambaClient("student", "password");
                }
            }
        }
        return sClient;
    }

    public static void post(Context ctxt, String status) {
        Intent i = new Intent(ctxt, YambaService.class);
        i.putExtra(PARAM_STATUS, status);
        ctxt.startService(i);
    }

    public YambaService() {
        super(TAG);

        mHandler = new PostHandler(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int message = R.string.submit_failed_msg;
        try {
            String postStatus = intent.getStringExtra(PARAM_STATUS);
            if (BuildConfig.DEBUG) {Log.d(TAG, "posting: " + postStatus);}
            getClient().postStatus(postStatus);
            message = R.string.submit_success_msg;
        } catch (YambaClientException e) {
            e.printStackTrace();
        }

        Message.obtain(mHandler, OP_POST_COMPLETE, message, 0).sendToTarget();
    }

    protected void onPostComplete(int message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private static class PostHandler extends Handler {

        private YambaService mService;

        public PostHandler(YambaService service) {
            mService = service;
        }

        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case OP_POST_COMPLETE:
                    mService.onPostComplete(msg.arg1);
            }
        }
    }
}
