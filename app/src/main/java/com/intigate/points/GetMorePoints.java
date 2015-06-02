package com.intigate.points;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.intigate.emall.R;

/**
 * Created by krishank on 6/1/2015.
 */
public class GetMorePoints extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getmorepoint);
    }


    public void back(View v) {
        finish();
        overridePendingTransition(0, 0);
    }
}
