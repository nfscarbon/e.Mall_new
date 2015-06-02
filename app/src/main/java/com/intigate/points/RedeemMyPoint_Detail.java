package com.intigate.points;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.intigate.emall.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import listner.Listener_service;
import listner.PostData;
import listner.Toast;
import utils.PrefUtils;

/**
 * Created by krishank on 6/1/2015.
 */
public class RedeemMyPoint_Detail extends Activity implements Listener_service {
    Context context;
    String url = "";

    JSONObject j_obj;
    ImageView imageView_offerdetails;
    TextView total_points_redeem;


    Button b_generate_coupons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redeemmypoint_detai);
        context = this;
        imageView_offerdetails = (ImageView) findViewById(R.id.imageView_offerdetails);
        total_points_redeem = (TextView) findViewById(R.id.total_points_redeem);
        b_generate_coupons = (Button) findViewById(R.id.b_generate_coupons);
        total_points_redeem.setText("" + getIntent().getExtras().getInt("idtotal"));
        Picasso.with(context).load(getIntent().getExtras().getString("img")).placeholder(R.drawable.icon_main).into(imageView_offerdetails);


        b_generate_coupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                generateCoupon();

            }
        });
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

        Log.d("generate coupon responser ",response);
        RedeemMyPoint_Detail.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Toast(getApplicationContext(), "Your Coupon has been generated").show();
            }
        });

    }

    @Override
    public void onRequestFail(int method, String message) {

    }

    @Override
    public void onStatus404() {

    }


    public void generateCoupon() {


        JSONObject object = new JSONObject();
        try {

            object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.SimNumber, ""));
            object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.UDID, ""));
            object.put("LoyltyID", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.LoyaltyId, ""));
            object.put("CouponID", getIntent().getExtras().get("id"));
            object.put("CompanyID", getIntent().getExtras().get("CompanyId"));


        } catch (Exception e) {

            e.printStackTrace();
        }

        new PostData(0, object.toString(), "GenerateCoupons", RedeemMyPoint_Detail.this).execute();
    }
}
