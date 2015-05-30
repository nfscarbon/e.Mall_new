package com.intigate.setup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;
import com.intigate.ratnav.emall_new.MainActivity;
import com.intigate.ratnav.emall_new.R;

import org.json.JSONObject;

import listner.Listener_service;
import listner.PostData;
import listner.Toast;
import utils.Fonts;
import utils.PrefUtils;

/**
 * Created by ratnav on 29-05-2015.
 */
public class ConfirmPassword extends Activity implements View.OnClickListener, Listener_service {

    private EditText ed_otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        new PrefUtils().saveToPrefs(getApplicationContext(), PrefUtils.registrationId, GCMRegistrar.getRegistrationId(getApplicationContext()));

        setContentView(R.layout.confirmpassword);

        setUpScreen();

    }

    private void setUpScreen() {

        Fonts mFonts = new Fonts(getApplicationContext());

        TextView msg = (TextView) findViewById(R.id.msg);
        msg.setTypeface(mFonts.font_arial());

        ed_otp = (EditText) findViewById(R.id.ed_otp);
        ed_otp.setTypeface(mFonts.font_regular());

        Button b_ok = (Button) findViewById(R.id.b_ok);
        b_ok.setOnClickListener(this);
        b_ok.setTypeface(mFonts.font_regular());

        Button b_resend_otp = (Button) findViewById(R.id.b_resend_otp);
        b_resend_otp.setTypeface(mFonts.font_regular());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.b_ok:
                if (ed_otp.getText().toString() == null || ed_otp.getText().toString().length() < 4) {

                    new Toast(getApplicationContext(), "Please Enter Otp !").show();

                } else {

                    TelephonyManager m = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    new PrefUtils().saveToPrefs(getApplicationContext(), PrefUtils.SimNumber, m.getSimSerialNumber());
                    new PrefUtils().saveToPrefs(getApplicationContext(), PrefUtils.UDID, m.getDeviceId());


                    JSONObject object = new JSONObject();

                    try {

                        object.put("MobileNumber", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.user_MobileNo, ""));
                        object.put("Otp", ed_otp.getText().toString());
                        object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.SimNumber, ""));

                        object.put("MobileNotificationID", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.registrationId, ""));
                        object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.UDID, ""));

                        new PostData(0, object.toString(), "OTPConfirmation", ConfirmPassword.this).execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;

            case R.id.b_resend_otp:

                break;

        }
    }

    @Override
    public void onRequestSuccess(int method, String response) {

        ConfirmPassword.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new Toast(ConfirmPassword.this, "Welcome to e.Mall !").show();

            }
        });

        try {
            JSONObject obj = new JSONObject(response.replace("[", "").replace("]", ""));


            new PrefUtils().saveToPrefs(getApplicationContext(), new PrefUtils().LoyaltyId, obj.getString("LoyltyId"));

            new PrefUtils().saveToPrefs(getApplicationContext(), "IsLoggedIn", "1");

            Intent i = new Intent(ConfirmPassword.this, MainActivity.class);
            startActivity(i);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestFail(int method, String message) {
        ConfirmPassword.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new Toast(getApplicationContext(), "Please Check Your N/w Connection !").show();


            }
        });
    }

    @Override
    public void onStatus404() {
        ConfirmPassword.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new Toast(ConfirmPassword.this, "OTP is wrong !").show();

            }
        });


    }
}
