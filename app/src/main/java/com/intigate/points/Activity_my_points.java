package com.intigate.points;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.intigate.ratnav.emall_new.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;


import listner.Listener_service;
import listner.PostData;
import utils.PrefUtils;
import utils.ProgressHUD;

/**
 * Created by ratnav on 26-05-2015.
 */
public class Activity_my_points extends ActionBarActivity implements View.OnClickListener, DialogInterface.OnCancelListener, Listener_service {
    TextView title,tv_totla_points_top;
    Button my_points_b_transaction_history, my_points_b_total_by_cmp;
    ProgressHUD dialog;
    ListView my_points_container;
    TransactionHistory_Handler[] result;
    TransactionHistory_Handler handler;

    Transaction_cmp_handler[] result_c;
    Transaction_cmp_handler handler_c;

    TextView tv_totla_points;
    Adapter_transactionHistory Adapter_transactionHistory = null;
    Adapter_transaction_cmp Adapter_transaction_cmp = null;
    int totalPoints = 0;
    LinearLayout ll_redeem, ll_total_header, ll_total,ll_tabll_top_total_points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_my_points);
        getSupportActionBar().hide();
        ll_redeem = (LinearLayout) findViewById(R.id.ll_redeem);
        ll_total_header = (LinearLayout) findViewById(R.id.ll_total_header);
        ll_total = (LinearLayout) findViewById(R.id.ll_total);
        ll_tabll_top_total_points=(LinearLayout)findViewById(R.id.ll_tabll_top_total_points);
        tv_totla_points_top=(TextView)findViewById(R.id.tv_totla_points_top);
        ll_redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i_ll_redeem = new Intent(Activity_my_points.this, Activity_redeem_points.class);
//                startActivity(i_ll_redeem);
//                overridePendingTransition(0, 0);
            }
        });
        setUpHeader();
        setUpTab();

        my_points_b_transaction_history_click();
    }

    private void setUpTab() {
        my_points_b_transaction_history = (Button) findViewById(R.id.my_points_b_transaction_history);
        my_points_b_transaction_history.setOnClickListener(this);
        my_points_b_total_by_cmp = (Button) findViewById(R.id.my_points_b_total_by_cmp);
        my_points_b_total_by_cmp.setOnClickListener(this);
        //Set Tab Text
        my_points_b_transaction_history.setText("Transaction History");
        my_points_b_total_by_cmp.setText("By Company");

        my_points_container = (ListView) findViewById(R.id.my_points_container);
        tv_totla_points = (TextView) findViewById(R.id.tv_totla_points);
    }


    public void setUpHeader() {
        title = (TextView) findViewById(R.id._title);
//        Typeface tf = Typeface.createFromAsset(this.getAssets(),
//                "fonts/arial.ttf");
//        title.setTypeface(tf);
//        title.setShadowLayer(15, 5, 5, 0xFF303030);
    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.my_points_b_transaction_history:
                my_points_b_transaction_history_click();

                break;

            case R.id.my_points_b_total_by_cmp:

                my_points_b_total_by_cmp_click();

                break;

            case R.id.ll_redeem:

//                ((Container) getActivity()).setTitle("Redeem My Points");
//                ll_tab.setVisibility(View.GONE);
//
//                ll_total.setVisibility(View.GONE);

                break;

            case R.id.ll_coupons:

//                ll_total.setVisibility(View.GONE);
//                ll_tab.setVisibility(View.GONE);
//                iv_redeem.setImageResource(R.drawable.redeem);
//                tv_redeem.setTextColor(Color.parseColor("#000000"));
//                iv_coupons.setImageResource(R.drawable.getmore_points_h);
//                tv_coupons.setTextColor(Color.parseColor("#00b0f0"));
                break;
        }


    }

    public void my_points_b_transaction_history_click() {
        ll_total.setVisibility(View.VISIBLE);
        ll_total_header.setVisibility(View.VISIBLE);
        ll_tabll_top_total_points.setVisibility(View.GONE);
        my_points_b_transaction_history.setTextColor(Color.parseColor("#FFFFFF"));
        my_points_b_transaction_history.setBackgroundColor(Color.parseColor("#00b0f0"));
        my_points_b_total_by_cmp.setTextColor(Color.parseColor("#00b0f0"));
        my_points_b_total_by_cmp.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        ll_total.setVisibility(View.VISIBLE);
        if (Adapter_transactionHistory == null) {
            getMyPoints(1);
        } else {

            my_points_container.setAdapter(Adapter_transactionHistory);
        }
    }

    public void my_points_b_total_by_cmp_click() {

        ll_total.setVisibility(View.GONE);
        ll_total_header.setVisibility(View.GONE);
        ll_tabll_top_total_points.setVisibility(View.VISIBLE);
        my_points_b_total_by_cmp.setTextColor(Color.parseColor("#FFFFFF"));
        my_points_b_total_by_cmp.setBackgroundColor(Color.parseColor("#00b0f0"));
        my_points_b_transaction_history.setTextColor(Color.parseColor("#00b0f0"));
        my_points_b_transaction_history.setBackgroundColor(Color.parseColor("#FFFFFF"));

        if (Adapter_transaction_cmp == null) {
            getMyPoints(2);
        } else {

            my_points_container.setAdapter(Adapter_transaction_cmp);
        }


    }

    public void getMyPoints(final int CaseID) {


        JSONObject object = new JSONObject();
        try {

            object.put("SimNumber", new PrefUtils().getFromPrefs(Activity_my_points.this, PrefUtils.SimNumber, ""));
            object.put("UDID", new PrefUtils().getFromPrefs(Activity_my_points.this, PrefUtils.UDID, ""));
            object.put("CaseID", CaseID);
            object.put("IndexNumber", 0);
            object.put("LoyltyID", new PrefUtils().getFromPrefs(Activity_my_points.this, new PrefUtils().LoyaltyId, ""));


        } catch (Exception e) {
        }


//        Listener_service Listener_service = new Listener_service() {
//            @Override
//            public void onRequestSuccess(int method, String response) {
//                Log.e("setUpGrid", response);
//                totalPoints = 0;
//                try {
//                    JSONArray arr = new JSONArray(response);
//                    if (CaseID == 1) {
//                        result = new TransactionHistory_Handler[arr.length()];
//                        for (int i = 0; i < arr.length(); i++) {
//                            handler = new TransactionHistory_Handler();
//                            handler.setCompany(arr.getJSONObject(i).getString("CompanyName"));
//                            handler.setTime(arr.getJSONObject(i).getString("Time"));
//                            handler.setDate(arr.getJSONObject(i).getString("Date"));
//                            handler.setPoints(arr.getJSONObject(i).getString("Points"));
//                            totalPoints += arr.getJSONObject(i).getInt("Points");
//                            if (arr.getJSONObject(i).getInt("OperationsTypeId") == 1) {
//                                handler.setIsUp(false);
//                            } else {
//                                handler.setIsUp(true);
//                            }
//
//
//                            result[i] = handler;
//
//                        }
//                        setUpList();
//                    } else {
//                        result_c = new Transaction_cmp_handler[arr.length()];
//                        for (int i = 0; i < arr.length(); i++) {
//                            handler_c = new Transaction_cmp_handler();
//                            handler_c.setPoints(arr.getJSONObject(i).getInt("Points"));
//                            handler_c.setUrl(arr.getJSONObject(i).getString("LogoURL"));
//
//
//                            result_c[i] = handler_c;
//
//                            totalPoints += arr.getJSONObject(i).getInt("Points");
//                        }
//                        setUpListCmp();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                Activity_my_points.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tv_totla_points.setText("" + totalPoints);
//                    }
//                });
//                dialog.dismiss();
//            }
//
//            @Override
//            public void onRequestFail(int method, String message) {
//
//            }
//        };
        new PostData(0, object.toString(), "GetPoints", Activity_my_points.this).execute();
    }

    private void setUpList() {
        Activity_my_points.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Adapter_transactionHistory = new Adapter_transactionHistory(Activity_my_points.this, result);
                my_points_container.setAdapter(Adapter_transactionHistory);
            }
        });


    }

    private void setUpListCmp() {
        Activity_my_points.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Adapter_transaction_cmp = new Adapter_transaction_cmp(Activity_my_points.this, result_c);
                my_points_container.setAdapter(Adapter_transaction_cmp);
            }
        });
    }


    @Override
    public void onCancel(DialogInterface dialog) {

    }

    public void back(View v) {
        finish();
    }

    @Override
    public void onRequestSuccess(int method, String response) {
        JSONArray arr = null;
        switch (method) {
            case 0:

                totalPoints = 0;
                try {
                    arr = new JSONArray(response);
                    if (arr.length() < 10) {
                        result = new TransactionHistory_Handler[10];
                    } else {
                        result = new TransactionHistory_Handler[arr.length()];
                    }
                    for (int i = 0; i < arr.length(); i++) {

                        handler = new TransactionHistory_Handler();
                        handler.setCompany(arr.getJSONObject(i).getString("CompanyName"));
                        handler.setTime(arr.getJSONObject(i).getString("Time"));
                        handler.setDate(arr.getJSONObject(i).getString("Date"));
                        handler.setPoints(arr.getJSONObject(i).getString("Points"));
                        totalPoints += arr.getJSONObject(i).getInt("Points");
                        Activity_my_points.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_totla_points_top.setText(""+totalPoints);
                            }
                        });
                        if (arr.getJSONObject(i).getInt("OperationsTypeId") == 1) {
                            handler.setIsUp(false);
                        } else {
                            handler.setIsUp(true);
                        }


                        result[i] = handler;

                    }
                    setUpList();
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        result_c = new Transaction_cmp_handler[arr.length()];
                        for (int i = 0; i < arr.length(); i++) {
                            handler_c = new Transaction_cmp_handler();
                            handler_c.setPoints(arr.getJSONObject(i).getInt("Points"));
                            handler_c.setUrl(arr.getJSONObject(i).getString("LogoURL"));


                            result_c[i] = handler_c;

                            totalPoints += arr.getJSONObject(i).getInt("Points");
                        }
                        setUpListCmp();
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
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
}
