package com.intigate.ratnav.emall_new;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intigate.id.MyId;
import com.intigate.offers.Offers;
import com.intigate.points.Activity_my_points;

import utils.Fonts;


public class MainActivity extends ActionBarActivity {

    TextView tv_title;
    LinearLayout ll_myId, ll_offers, ll_myPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        setUpTitle("e.Mall Loyalty Program");

        setUpFooter();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void setUpTitle(String title) {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(title);
        tv_title.setTypeface(new Fonts(getApplicationContext()).font_arial());
        tv_title.setShadowLayer(15, 5, 5, 0xFF303030);

    }


    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }


    private void setUpFooter() {
        ll_myId = (LinearLayout) findViewById(R.id.ll_myProfile);
        ll_myId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_ll_myId = new Intent(MainActivity.this, MyId.class);
                startActivity(i_ll_myId);
            }
        });

        ll_offers = (LinearLayout) findViewById(R.id.ll_offers);
        ll_offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_ll_myId = new Intent(MainActivity.this, Offers.class);
                startActivity(i_ll_myId);
            }
        });

        ll_myPoints = (LinearLayout) findViewById(R.id.ll_myPoints);
        ll_myPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_ll_myId = new Intent(MainActivity.this, Activity_my_points.class);
                startActivity(i_ll_myId);
            }
        });
    }
}
