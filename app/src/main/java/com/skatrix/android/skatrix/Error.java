package com.skatrix.android.skatrix;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;




    public class Error  implements Thread.UncaughtExceptionHandler {

        Activity activity;

        public Error(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void uncaughtException(Thread thread, final Throwable ex) {

            Intent intent = new Intent(activity, ControlsActivity.class);

            intent.putExtra(activity.getString(R.string.nav_header_desc), ex);
            Log.d("ERROR", "---------" + ex.getMessage());
            Log.d("ERROR", "--------" + ex.getCause());
            Log.d("ERROR", "--------" + Arrays.toString(ex.getStackTrace()));
            activity.startActivity(intent);

            activity.finish();

            System.exit(0);
        }

    }
