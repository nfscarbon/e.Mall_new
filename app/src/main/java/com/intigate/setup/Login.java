package com.intigate.setup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;
import com.intigate.emall.MainActivity;
import com.intigate.emall.R;

import org.json.JSONObject;

import listner.Listener_service;
import listner.PostData;
import listner.Toast;
import utils.Fonts;
import utils.PrefUtils;

/**
 * Created by ratnav on 29-05-2015.
 */
public class Login extends Activity implements Listener_service, View.OnClickListener {
    EditText ed_number;
    Button button_SignIn;
    TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GCMRegistrar.checkDevice(Login.this);
        GCMRegistrar.checkManifest(Login.this);
        GCMRegistrar.register(Login.this, "532525057957");

        if (new PrefUtils().getFromPrefs(getApplicationContext(), "IsLoggedIn", "0").equals("0")) {

        } else {
            Intent i = new Intent(Login.this, MainActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(0, 0);
        }

        setContentView(R.layout.login);

        setUpScreen();
    }

    private void setUpScreen() {
        ed_number = (EditText) findViewById(R.id.ed_number);
        button_SignIn = (Button) findViewById(R.id.button_SignIn);
        msg = (TextView) findViewById(R.id.msg);
        button_SignIn.setOnClickListener(this);

        Fonts mFonts = new Fonts(getApplicationContext());
        ed_number.setTypeface(mFonts.font_arial());
        button_SignIn.setTypeface(mFonts.font_arial());
        msg.setTypeface(mFonts.font_arial());
    }

    @Override
    public void onRequestSuccess(int method, String response) {
        try {
            JSONObject obj = new JSONObject(response.replace("[","").replace("]", ""));
            obj.getString("OTP");
            obj.getString("IsExist");
            new PrefUtils().saveToPrefs(getApplicationContext(), "OTP", obj.getString("OTP"));
            new PrefUtils().saveToPrefs(getApplicationContext(),"IsExist",obj.getString("IsExist"));
            Intent i = new Intent(Login.this, ConfirmPassword.class);
            startActivity(i);
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestFail(int method, String message) {
        Login.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new Toast(getApplicationContext(), "Please Check Your N/w Connection !").show();


            }
        });

    }

    @Override
    public void onStatus404() {

    }

    @Override
    public void onClick(View v) {

        if (ed_number.getText().toString() == null || ed_number.getText().toString().length() <= 9) {

            new Toast(getApplicationContext(), "Please Enter Valid Number").show();

        } else {

            new PrefUtils().saveToPrefs(getApplicationContext(), PrefUtils.user_MobileNo, ed_number.getText().toString());

            JSONObject object = new JSONObject();

            try {

                object.put("MobileNumber", ed_number.getText().toString());
                new PrefUtils().saveToPrefs(getApplicationContext(), "MobileNumberOfUser", ed_number.getText().toString());
                object.put("DeviceType", 1);

            } catch (Exception e) {
                e.printStackTrace();
            }

            new PostData(0, object.toString(), "Login", Login.this).execute();
        }

    }
}
