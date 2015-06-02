package com.intigate.id;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.intigate.emall.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import listner.Listener_service;
import listner.PostData;
import utils.PrefUtils;

/**
 * Created by ratnav on 18-05-2015.
 */


public class Details_mail extends ActionBarActivity implements Listener_service {

    ListView lv_reply;
    Model_mail_details mModel_mail_details[];
    TextView tv_main;

    ImageView iv_star, tv_main_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_details);
        getSupportActionBar().hide();
        lv_reply = (ListView) findViewById(R.id.lv_reply);
        tv_main = (TextView) findViewById(R.id.tv_main);
        tv_main_main = (ImageView) findViewById(R.id.tv_main_main);
        iv_star = (ImageView) findViewById(R.id.iv_star);
        try {
            if (getIntent().getExtras().getBoolean("isImp")) {
                iv_star.setBackgroundResource(R.drawable.yellow_star);
            } else {
                iv_star.setBackgroundResource(R.drawable.white_star);
            }
        } catch (Exception e) {
            e.printStackTrace();
            iv_star.setBackgroundResource(R.drawable.white_star);
        }

        GetMailsDetails();
    }


    public void GetMailsDetails() {

//        GetMailsDetails

        JSONObject object = new JSONObject();


        try {

            object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext().getApplicationContext(), PrefUtils.SimNumber, ""));
            object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext().getApplicationContext(), PrefUtils.UDID, ""));
            object.put("LoyltyID", new PrefUtils().getFromPrefs(getApplicationContext().getApplicationContext(), new PrefUtils().LoyaltyId, ""));
            object.put("ConversationID", getIntent().getExtras().getInt("id"));

        } catch (Exception e) {
            e.printStackTrace();
        }


        new PostData(0, object.toString(), "GetMailsDetails", Details_mail.this).execute();

    }


    public static String getDate(String milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(milliSeconds));
        return formatter.format(calendar.getTime());
    }

    public void reply(View v) {

        showCustomDialog();
    }


    public void sendMail(String reply) {


        JSONObject object = new JSONObject();
        try {

            object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.SimNumber, ""));
            object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.UDID, ""));
            object.put("isNew", false);
            object.put("ConversationID", mModel_mail_details[0].getConversationID());
            object.put("LoyltyID", new PrefUtils().getFromPrefs(getApplicationContext(), new PrefUtils().LoyaltyId, ""));
            object.put("CompanyID", mModel_mail_details[0].getCompanyId());
            object.put("MailTypeID", mModel_mail_details[0].getMailTypeId());
            object.put("Subject", mModel_mail_details[0].getSubject());
            object.put("Body", reply);
            object.put("isImportant", false);


        } catch (Exception e) {
            e.printStackTrace();
        }


//        Listener_service Listener_service = new Listener_service() {
//            @Override
//            public void onRequestSuccess(int method, String response) {
//
//                Details_mail.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "Mail has been send .", Toast.LENGTH_LONG).show();
//                        Intent i = new Intent(Details_mail.this, Details_mail.class);
//                        i.putExtra("isImp", getIntent().getExtras().getBoolean("isImp"));
//                        i.putExtra("id", getIntent().getExtras().getInt("id"));
//                        startActivity(i);
//
//                        finish();
//                        overridePendingTransition(0, 0);
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void onRequestFail(int method, String message) {
//                Details_mail.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "Unable To send Mail .", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//            }
//        };
        new PostData(1, object.toString(), "SendMail", Details_mail.this).execute();

    }

    private void showCustomDialog() {

        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.reply_dialog);//carregando o layout do dialog do xml
        dialog.setCancelable(false);
        dialog.setTitle("Re: " + mModel_mail_details[0].getSubject());

        final Button ok = (Button) dialog.findViewById(R.id.bt_ok);//se atentem ao dialog.

        final Button cancelar = (Button) dialog.findViewById(R.id.bt_cancel);

        final EditText editText = (EditText) dialog.findViewById(R.id.inputText);

        ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
                sendMail(editText.getText().toString());
            }

        });
        cancelar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();

            }

        });

        dialog.show();
    }


    public void back(View v) {
        finish();
    }

    @Override
    public void onRequestSuccess(int method, String response) {
        switch (method) {


            case 0:


                try {
                    JSONArray arr = new JSONArray(response);
                    JSONObject obj;
                    if (arr != null && arr.length() > 0) {
                        mModel_mail_details = new Model_mail_details[arr.length()];

                        for (int i = 0; i < arr.length(); i++) {

                            obj = arr.getJSONObject(i);
                            Model_mail_details model_mail_details_ = new Model_mail_details();

                            model_mail_details_.setIsValid(obj.getBoolean("isValid"));
                            model_mail_details_.setName(obj.getString("Name"));
                            model_mail_details_.setConversationID(obj.getInt("ConversationID"));
                            model_mail_details_.setSubject(obj.getString("Subject"));
                            model_mail_details_.setBody(obj.getString("Body"));
                            model_mail_details_.setSendDate(getDate(obj.getString("SendDate"), "hh:mm a"));
                            model_mail_details_.setReplyDate(getDate(obj.getString("ReplyDate"), "hh:mm a"));
                            model_mail_details_.setSendBy(obj.getInt("SendBy"));
                            model_mail_details_.setLogoURL(obj.getString("LogoURL"));
                            model_mail_details_.setUserPhoto(obj.getString("UserPhoto"));
                            model_mail_details_.setMailTypeId(obj.getInt("MailTypeId"));
                            model_mail_details_.setCompanyId(obj.getInt("CompanyId"));


                            mModel_mail_details[i] = model_mail_details_;


                        }


                    }

                } catch (Exception e) {
                    Log.e("JSON ARRAY EXCEPTION", "EXCEPTiOn");
                }

                Details_mail.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_main.setText(mModel_mail_details[0].getSubject());
                        Picasso.with(getApplicationContext()).load(mModel_mail_details[0].getLogoURL()).placeholder(R.drawable.icon_main).into(tv_main_main);
                        Log.e("mModel_mail_details", mModel_mail_details.toString());
                        Mail_details_adapter mMail_details_adapter = new Mail_details_adapter(Details_mail.this, mModel_mail_details);
                        lv_reply.setAdapter(mMail_details_adapter);
                        lv_reply.setSelection(mMail_details_adapter.getCount() - 1);
                    }
                });
                break;


            case 1:
                Details_mail.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new listner.Toast(getApplicationContext(), "Reply has been done!").show();

                        Intent i = new Intent(Details_mail.this, Details_mail.class);
                        i.putExtra("isImp", getIntent().getExtras().getBoolean("isImp"));
                        i.putExtra("id", getIntent().getExtras().getInt("id"));
                        startActivity(i);

                        finish();
                        overridePendingTransition(0, 0);
                    }
                });
                break;
        }
    }

    @Override
    public void onRequestFail(int method, String message) {

        switch (method) {
            case 1:

                Details_mail.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new listner.Toast(getApplicationContext(), "Unable to send reply.!").show();
                    }
                });
                break;
        }
    }

    @Override
    public void onStatus404() {

    }
}
