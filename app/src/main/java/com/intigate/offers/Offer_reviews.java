package com.intigate.offers;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.intigate.emall.R;

import org.json.JSONArray;
import org.json.JSONObject;

import listner.Listener_service;
import listner.PostData;
import utils.Fonts;
import utils.PrefUtils;
import utils.ProgressHUD;

/**
 * Created by ratnav on 21-05-2015.
 */
public class Offer_reviews extends Activity implements DialogInterface.OnCancelListener , Listener_service {
    ProgressHUD dialog;
    Fonts mFonts;
    TextView tv_title, tv_msg;
    RatingBar ratingBar;
    int totalComments = 0;
    String data;
    EditText ed_comments;
    ListView lv_reviews;
    Button button_sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_offer_reviews);
        ed_comments = (EditText) findViewById(R.id.ed_comments);
        button_sub=(Button)findViewById(R.id.button_sub);
        mFonts = new Fonts(getApplicationContext());
        ed_comments.setTypeface(mFonts.font_regular());
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(getIntent().getExtras().getString("title"));
        tv_title.setTypeface(mFonts.font_bold());


        ratingBar = (RatingBar) findViewById(R.id.ratingBar);


        tv_msg = (TextView) findViewById(R.id.tv_msg);

        lv_reviews = (ListView) findViewById(R.id.lv_reviews);
        getOfferDetail();

        button_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_comments.getText() != null && ed_comments.getText().toString().length() > 3) {
                    setComment(ed_comments.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Please Write Review", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void getOfferDetail() {

        dialog = ProgressHUD.show(Offer_reviews.this, "Processing...", true, true, this);
        dialog.show();



        JSONObject object = new JSONObject();
        try {

            object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext().getApplicationContext(), PrefUtils.SimNumber, ""));
            object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext().getApplicationContext(), PrefUtils.UDID, ""));
            object.put("LoyltyID", new PrefUtils().getFromPrefs(getApplicationContext().getApplicationContext(), new PrefUtils().LoyaltyId, ""));
            object.put("OfferId", "" + getIntent().getExtras().getInt("id"));


        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(getApplicationContext(), "Some Error accured while hit service....!", Toast.LENGTH_LONG).show();
        }

//        Listener_service Listener_service = new Listener_service() {
//            @Override
//            public void onRequestSuccess(int method, String response) {
//                Log.d("Detais", response);
//                data = response;
//                try {
//
//
//                    dialog.dismiss();
//                    Offer_reviews.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            updateValues();
//                        }
//                    });
//
//
//                } catch (Exception e) {
//                    dialog.dismiss();
//                    e.printStackTrace();
//                }
//
//                if (dialog.isShowing()) {
//                    dialog.dismiss();
//                }
//
//
//            }
//
//            @Override
//            public void onRequestFail(int method, String message) {
//                dialog.dismiss();
//
//                Offer_reviews.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "Unable to get details of offer...!", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//
//            }
//        };
        new PostData(0, object.toString(), "GetOfferComments", Offer_reviews.this).execute();

    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }

    public void updateValues() {

        JSONArray arr = null;
        try {
            arr = new JSONArray(data);
        } catch (Exception e) {
            e.printStackTrace();
            arr = null;
        }

        if (arr == null || arr.length() == 0) {
            tv_msg.setVisibility(View.VISIBLE);
            lv_reviews.setVisibility(View.GONE);
        } else {
            tv_msg.setVisibility(View.GONE);
            lv_reviews.setVisibility(View.VISIBLE);
            Handler_user_review[] handler;
            handler = new Handler_user_review[arr.length()];

            for (int i = 0; i < arr.length(); i++) {


                try {
                    Handler_user_review review = new Handler_user_review(arr.getJSONObject(i).getString("Name"),
                            arr.getJSONObject(i).getString("Comment"),
                            arr.getJSONObject(i).getString("Time"),
                            arr.getJSONObject(i).getString("UserPhoto"),
                            Float.parseFloat(arr.getJSONObject(i).getString("Rating")));
                    handler[i] = review;


                } catch (Exception e) {
                    e.printStackTrace();
                }
                offer_review_adapter moffer_review_adapter = new offer_review_adapter(Offer_reviews.this, handler);
                lv_reviews.setAdapter(moffer_review_adapter);

            }
        }
    }

    public void back(View v) {
        finish();
    }


    public void setComment(String comment) {

//        SoapObject obj = new SoapObject("http://tempuri.org/", "SetOffersReviews");
//
        JSONObject object = new JSONObject();
        try {

            object.put("SimNumber", new PrefUtils().getFromPrefs(Offer_reviews.this.getApplicationContext(), "SimNumber", ""));
            object.put("UDID", new PrefUtils().getFromPrefs(Offer_reviews.this.getApplicationContext(), "UDID", ""));
            object.put("LoyltyID", new PrefUtils().getFromPrefs(Offer_reviews.this.getApplicationContext(), new PrefUtils().LoyaltyId, ""));
            object.put("OfferId", getIntent().getExtras().getInt("id"));
            object.put("ReviewStatusId", 2);
            object.put("LikeStatus", false);
            object.put("Comments", comment);
            object.put("ShareStatusId", "0");
            object.put("ShareLinkUrl", "");


        } catch (Exception e) {
        }
//        PropertyInfo mPropertyInfo = new PropertyInfo();
//        mPropertyInfo.setName("data");
//        mPropertyInfo.setValue(object.toString());
//        obj.addProperty(mPropertyInfo);
//        Listener_service Listener_service = new Listener_service() {
//            @Override
//            public void onRequestSuccess(int method, String response) {
//                Log.d("Response from set like", response);
//
//                Offer_reviews.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "Comment Posted ", Toast.LENGTH_LONG).show();
//                        UpdateGui();
//                    }
//                });
//
//            }
//
//            @Override
//            public void onRequestFail(int method, String message) {
//                Offer_reviews.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "Some Error... ", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//            }
//        };
        new PostData(1, object.toString(), "SetOffersReviews", Offer_reviews.this).execute();
    }

    public void UpdateGui(){
        ed_comments.setText("");
    }

    @Override
    public void onRequestSuccess(int method, final String response) {
        switch (method){
            case 0:
                Log.d("Detais", response);
                try {


                    dialog.dismiss();
                    Offer_reviews.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            data=response;
                            updateValues();
                        }
                    });


                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }

                break;

            case 1:
                Offer_reviews.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new listner.Toast(getApplicationContext(), "Review has been submitted").show();
                        UpdateGui();
                    }
                });


        }
    }

    @Override
    public void onRequestFail(int method, String message) {

    }

    @Override
    public void onStatus404() {

    }
}
