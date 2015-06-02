package com.intigate.offers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.intigate.emall.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import listner.Listener_service;
import listner.PostData;
import utils.PrefUtils;

/**
 * Created by krishank on 5/30/2015.
 */
public class Offers_Detail extends Activity implements Listener_service, Dialog.OnCancelListener {
    Context context;
    String url = "";
    RatingBar ratingBar;
    JSONObject j_obj;
    ImageView imageView_offerdetails;
    TextView text_total_like;
    ImageView share, comment;
    Button btn_comment;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_details);

        comment=(ImageView)findViewById(R.id.comment);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToComment();
            }
        });
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        context = this;
        imageView_offerdetails = (ImageView) findViewById(R.id.imageView_offerdetails);
        editText = (EditText) findViewById(R.id.editText);
        text_total_like = (TextView) findViewById(R.id.text_total_like);
        text_total_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToComment();
            }
        });
        share = (ImageView) findViewById(R.id.share);
   /*     share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject test");
                i.putExtra(android.content.Intent.EXTRA_TEXT, "Welcpme to e.Mall");
                startActivity(Intent.createChooser(i, "Share via"));

            }
        });*/


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Check out these products..");
                i.putExtra(Intent.EXTRA_TEXT, url);
                startActivityForResult(Intent.createChooser(i, "Share URL"), 22);
            }
        });

        btn_comment = (Button) findViewById(R.id.btn_comment);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString() != null && editText.getText().toString().length() > 5) {

                    setComment(editText.getText().toString());

                }
            }
        });
        getOfferDetail();
    }

    public void getOfferDetail() {
//


        JSONObject object = new JSONObject();
        try {

            object.put("SimNumber", new PrefUtils().getFromPrefs(context.getApplicationContext(), PrefUtils.SimNumber, ""));
            object.put("UDID", new PrefUtils().getFromPrefs(context.getApplicationContext(), PrefUtils.UDID, ""));
            object.put("LoyltyID", new PrefUtils().getFromPrefs(context.getApplicationContext(), new PrefUtils().LoyaltyId, ""));
            object.put("OfferId", "" + getIntent().getExtras().getInt("id"));


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Some Error accured while hit service....!", Toast.LENGTH_LONG).show();
        }

     /*   Listener_service Listener_service = new Listener_service() {
            @Override
            public void onRequestSuccess(int method, String response) {
                Log.d("Detais", response);
                String data = response.replace("[", "").replace("]", "");
                try {
                    j_obj = new JSONObject(data);
                    if (!j_obj.getBoolean("isValid")) {
                        dialog.dismiss();
                        new PrefUtils().saveToPrefs(context, "IsLoggedIn", "0");
                        Intent ii = new Intent(context, Error.class);
                        startActivity(ii);
                        finish();

                    } else {
                        dialog.dismiss();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateValues();
                            }
                        });
                    }

                } catch (Exception e) {

                    dialog.dismiss();
                    e.printStackTrace();
                }

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }


            }

            @Override
            public void onRequestFail(int method, String message) {
                dialog.dismiss();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Unable to get details of offer...!", Toast.LENGTH_LONG).show();
                    }
                });


            }
        };*/
        new PostData(0, object.toString(), "GetOffersDetails", Offers_Detail.this).execute();

    }

    @Override
    public void onRequestSuccess(int method, String response) {
        switch (method) {
            case 0:
                String data = response.replace("[", "").replace("]", "");
                try {
                    j_obj = new JSONObject(data);
                    updateValues();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 10:
                try {
                    //setUpSpinner(method, response);
                    editText.setText("");
                    Toast.makeText(getApplication(), "Comment Posted ", Toast.LENGTH_LONG).show();
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


    private void updateValues() {
        try {

            Log.e("j_obj", j_obj.toString());
            Offers_Detail.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {
                        url = j_obj.getString("ImagePath");
                        text_total_like.setText("(" + j_obj.getString("NoofComments") + ")");
                        ratingBar.setRating(Float.parseFloat(j_obj.getString("Rating")));
                        Picasso.with(getApplicationContext()).load(j_obj.getString("ImagePath")).into(imageView_offerdetails);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
//            tv_title.setText(j_obj.getString("Subject"));
//            tv_title.setTypeface(mFonts.font_regular());
//            tv_des.setText(j_obj.getString("Body"));
//            tv_des.setTypeface(mFonts.font_regular());
//            button_comment.setText("Comments (" + j_obj.getString("NoofComments") + ")");
//            url = j_obj.getString("FacebookURL");
//            likeView.setObjectIdAndType("https://www.facebook.com/HumTereBinAbRahNahiSakteBySheelnRaghvandra", LikeView.ObjectType.PAGE);

//            if (j_obj.getBoolean("Like")) {
//                isLIke = true;
//                iv_heart.setImageResource(R.drawable.heart);
//            } else {
//                iv_heart.setImageResource(R.drawable.heart_hover);
//                isLIke = false;
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCancel(DialogInterface dialog) {

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22) {
            Toast.makeText(getApplicationContext(), "" + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    public void setComment(String comment) {

        SoapObject obj = new SoapObject("http://tempuri.org/", "SetOffersReviews");

        JSONObject object = new JSONObject();
        try {

            object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext(), "SimNumber", ""));
            object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext(), "UDID", ""));
            object.put("LoyltyID", new PrefUtils().getFromPrefs(getApplicationContext(), new PrefUtils().LoyaltyId, ""));
            object.put("OfferId", getIntent().getExtras().getInt("id"));
            object.put("ReviewStatusId", 2);
            object.put("LikeStatus", false);
            object.put("Comments", comment);
            object.put("ShareStatusId", "0");
            object.put("ShareLinkUrl", "");


        } catch (Exception e) {
        }
        PropertyInfo mPropertyInfo = new PropertyInfo();
        mPropertyInfo.setName("data");
        mPropertyInfo.setValue(object.toString());
        obj.addProperty(mPropertyInfo);
       /* Listener_service Listener_service = new Listener_service() {
            @Override
            public void onRequestSuccess(int method, String response) {
                Log.d("Response from set like", response);
                // setUpSpinner(method, response);
                ed.setText("");
                Toast.makeText(getApplicationContext(), "Comment Posted ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRequestFail(int method, String message) {
                Toast.makeText(getApplication(), "Some Error... ", Toast.LENGTH_LONG).show();
            }
        };*/
        new PostData(10, object.toString(), "SetOffersReviews", Offers_Detail.this
        ).execute();

    }


    public void moveToComment() {

        Intent i = new Intent(Offers_Detail.this, Offer_reviews.class);

        try {
            i.putExtra("id", getIntent().getExtras().getInt("id"));
            i.putExtra("rating", j_obj.getInt("Rating"));
            i.putExtra("title", j_obj.getString("Subject"));

            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
