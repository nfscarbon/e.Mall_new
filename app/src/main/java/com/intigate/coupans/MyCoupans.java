package com.intigate.coupans;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.intigate.offers.ImageAdapter;
import com.intigate.emall.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;

import listner.Listener_service;
import listner.PostData;
import listner.Toast;
import utils.PrefUtils;

/**
 * Created by krishank on 6/1/2015.
 */
public class MyCoupans extends Activity implements View.OnClickListener, DialogInterface.OnCancelListener, Listener_service {
    Context context;
    GridView grid_mycoupan;
    Button available_mycoupans, redeemmycoupan;
    ImageAdapter Adapter_all = null;
    ImageAdapter Adapter_redeemed = null;
    Vector<String> v_all,v_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycoupans);
        v_all = new Vector<String>();
        v_id = new Vector<String>();
        context = this;
        grid_mycoupan = (GridView) findViewById(R.id.grid_coupan);
        available_mycoupans = (Button) findViewById(R.id.available_mycoupans);
        redeemmycoupan = (Button) findViewById(R.id.redeemmycoupan);
        available_mycoupans.setOnClickListener(this);
        redeemmycoupan.setOnClickListener(this);
        if (Adapter_all != null) {

            grid_mycoupan.setAdapter(Adapter_all);
            grid_mycoupan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(MyCoupans.this, Redeem_coupan.class);
                    i.putExtra("url", v_all.elementAt(position));
                    i.putExtra("id",v_id.elementAt(position));
                    startActivity(i);
                }
            });
        } else {
            getCoupons(0);
        }
    }

    public void back(View v) {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onRequestSuccess(int method, String response) {
        switch (method) {
            case 0:


                try {

                    JSONArray arr = new JSONArray(response);
                    JSONObject obj;

                    for (int i = 0; i < arr.length(); i++) {
                        obj = arr.getJSONObject(i);
                        v_all.add(obj.getString("ImagePath"));
                        v_id.add(obj.getString("GenerateCouponId"));
                    }

                    Adapter_all = new ImageAdapter(getApplicationContext(), v_all);
                    MyCoupans.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            grid_mycoupan.setAdapter(Adapter_all);
                            grid_mycoupan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent i = new Intent(MyCoupans.this, Redeem_coupan.class);
                                    i.putExtra("url", v_all.elementAt(position));
                                    i.putExtra("id",v_id.elementAt(position));
                                    startActivity(i);
                                }
                            });
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;


            case 1:

                try {

                    JSONArray arr = new JSONArray(response);
                    JSONObject obj;
                    if (arr == null || arr.length() <= 0) {
                        MyCoupans.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new Toast(getApplicationContext(), "No Redeemed Coupons Available").show();
                            }
                        });

                        if (Adapter_all != null) {
                            MyCoupans.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    grid_mycoupan.setAdapter(Adapter_all);
                                }
                            });
                        } else {
                            getCoupons(0);
                        }
                    } else {
                        for (int i = 0; i < arr.length(); i++) {
                            obj = arr.getJSONObject(i);
                            v_all.add(obj.getString("ImagePath"));
                        }

                        Adapter_redeemed = new ImageAdapter(getApplicationContext(), v_all);
                        MyCoupans.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                grid_mycoupan.setAdapter(Adapter_redeemed);
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }


    }

    @Override
    public void onRequestFail(int method, String message) {

    }

    @Override
    public void onStatus404() {

    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.available_mycoupans:
                if (Adapter_all != null) {

                    grid_mycoupan.setAdapter(Adapter_all);
                    grid_mycoupan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(MyCoupans.this, Redeem_coupan.class);
                            i.putExtra("url", v_all.elementAt(position));
                            i.putExtra("id",v_id.elementAt(position));
                            startActivity(i);
                        }
                    });

                } else {
                    getCoupons(0);
                }

                break;

            case R.id.redeemmycoupan:

                    getCoupons(1);


                break;
        }

    }


    public void getCoupons(int IsAvailable) {


        JSONObject object = new JSONObject();
        try {

            object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.SimNumber, ""));
            object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.UDID, ""));
            object.put("LoyltyID", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.LoyaltyId, ""));
            object.put("IsAvailable", IsAvailable);
            object.put("IndexNumber", 0);


        } catch (Exception e) {

            e.printStackTrace();
        }

        new PostData(IsAvailable, object.toString(), "GetMyCoupons", MyCoupans.this).execute();
    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}
