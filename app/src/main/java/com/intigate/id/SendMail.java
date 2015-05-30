package com.intigate.id;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.intigate.ratnav.emall_new.R;
import com.intigate.setup.Login;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Vector;

import listner.Listener_service;
import listner.PostData;
import listner.Toast;
import utils.PrefUtils;


public class SendMail extends Activity implements Listener_service {
    // hi i am KK

    Vector<Integer> v_id, v_id_msgType;
    Spinner spinner_cmpytype, spinner_msgtype;
    Button btn_submit;
    EditText edittext_subject, edittext_description;
    int cmp_id, MailTypeID;

    // hi i am rd
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_id_sendmail);

        v_id = new Vector<>();
        v_id_msgType = new Vector<>();


        spinner_cmpytype = (Spinner) findViewById(R.id.spinner_cmpytype);

        spinner_cmpytype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Compnay iD", "" + position);
                cmp_id = v_id.get(position);
                Log.d("Compnay iD", "" + cmp_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_msgtype = (Spinner) findViewById(R.id.spinner_msgtype);
        spinner_msgtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                MailTypeID = v_id_msgType.get(position);
                Log.d("MailTypeID", "" + MailTypeID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_submit = (Button) findViewById(R.id.btn_submit);

        edittext_subject = (EditText)
                findViewById(R.id.edittext_subject);
        edittext_description = (EditText) findViewById(R.id.edittext_description);
        edittext_description.setPadding(20, 20, 20, 20);
        edittext_subject.setPadding(20, 20, 20, 20);
        getCom();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((edittext_description.getText().toString() == null && edittext_description.getText().toString().length() < 2)) {
                    edittext_description.setError("Please enter body");
                } else if ((edittext_subject.getText().toString() == null && edittext_subject.getText().toString().length() < 2)) {
                    edittext_subject.setError("Please enter subject");
                } else {
                    sendMial();
                }
            }
        });

    }


    public void getCom() {
//


        JSONObject object = new JSONObject();
        try {

            object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext().getApplicationContext(), PrefUtils.SimNumber, ""));
            object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext().getApplicationContext(), PrefUtils.UDID, ""));


        } catch (Exception e) {
        }
        new PostData(2, object.toString(), "GetCompany", SendMail.this);

    }

    public void getMailType() {

        SoapObject obj = new SoapObject("http://tempuri.org/", "GetMailType");

        JSONObject object = new JSONObject();
        try {

            object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext().getApplicationContext(), "SimNumber", ""));
            object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext().getApplicationContext(), "UDID", ""));


        } catch (Exception e) {
        }
        PropertyInfo mPropertyInfo = new PropertyInfo();
        mPropertyInfo.setName("data");
        mPropertyInfo.setValue(object.toString());
        obj.addProperty(mPropertyInfo);
//        Listener_service Listener_service = new Listener_service() {
//            @Override
//            public void onRequestSuccess(int method, String response) {
//
//                setUpSpinner(method, response);
//            }
//
//            @Override
//            public void onRequestFail(int method, String message) {
//
//            }
//        };
//        new PostData_fragment(1, obj, "GetMailType", SendMail.this, Listener_service).execute();

    }


    public void sendMial() {


        JSONObject object = new JSONObject();
        try {

            object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext().getApplicationContext(), PrefUtils.SimNumber, ""));
            object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext().getApplicationContext(), PrefUtils.UDID, ""));
            object.put("isNew", true);
            object.put("ConversationID", 0);
            object.put("LoyltyID", new PrefUtils().getFromPrefs(getApplicationContext().getApplicationContext(), new PrefUtils().LoyaltyId, ""));
            object.put("CompanyID", cmp_id);
            object.put("MailTypeID", MailTypeID);
            object.put("Subject", edittext_subject.getText().toString());
            object.put("Body", edittext_description.getText().toString());
            object.put("isImportant", false);


        } catch (Exception e) {
            e.printStackTrace();
        }

        new PostData(1, object.toString(), "SendMail ", SendMail.this).execute();


    }

    public void setUpSpinner(int method, String response) {
        switch (method) {

            case 0:
                try {
                    JSONArray array = new JSONArray(response);
                    String[] items = new String[array.length() - 1];
                    for (int i = 0; i < array.length(); i++) {
                        if (array.getJSONObject(i).getString("isValid").equals("true")) {

                        } else {
//                            new PrefUtils().saveToPrefs(getApplicationContext(), "IsLoggedIn", "0");
//                            Intent ii = new Intent(getApplicationContext(), intigate.itpl.emall.Error.class);
//                            startActivity(ii);
//                            SendMail.this.finish();


                            break;
                        }
                        if (i != 0) {
                            items[i - 1] = array.getJSONObject(i).getString("Name");
                            v_id.add(array.getJSONObject(i).getInt("Id"));
                        }
                    }

                    final ArrayAdapter<String> adapter_spinner_cmp = new ArrayAdapter<String>(SendMail.this, android.R.layout.simple_spinner_item, items);

                    SendMail.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            spinner_cmpytype.setAdapter(adapter_spinner_cmp);

                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case 1:
                try {
                    JSONArray array = new JSONArray(response);
                    String[] items = new String[array.length()];
                    for (int i = 0; i < array.length(); i++) {
                        if (array.getJSONObject(i).getString("isValid").equals("true")) {

                        } else {
//                            new PrefUtils().saveToPrefs(getApplicationContext(), "IsLoggedIn", "0");
//                            Intent ii = new Intent(getApplicationContext(), intigate.itpl.emall.Error.class);
//                            startActivity(ii);
//                            SendMail.this.finish();


                            break;
                        }
                        items[i] = array.getJSONObject(i).getString("TYPE");
                        v_id_msgType.add(array.getJSONObject(i).getInt("Id"));
                    }
                    final ArrayAdapter<String> adapter_spinner_cmp = new ArrayAdapter<String>(SendMail.this, android.R.layout.simple_spinner_item, items);

                    SendMail.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            spinner_msgtype.setAdapter(adapter_spinner_cmp);

                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    public void close(View v) {
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onRequestSuccess(int method, String response) {

        switch (method) {
            // Send Mail
            case 1:
                SendMail.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Toast(SendMail.this, "Mail has been sent!").show();
                        edittext_description.setText("");
                        edittext_subject.setText("");
                    }
                });

                break;
            //Get Company Name
            // Setup First Spinner
            case 2:
                getMailType();
                setUpSpinner(method, response);
                break;

        }
    }

    @Override
    public void onRequestFail(int method, String message) {
        SendMail.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Toast(getApplicationContext(), "Please check your N/w Connection").show();
            }
        });

    }

    @Override
    public void onStatus404() {

        SendMail.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Toast(getApplicationContext(), "You are a invalid user").show();
                new PrefUtils().saveToPrefs(getApplicationContext(), "IsLoggedIn", "0");
                Intent i = new Intent(SendMail.this, Login.class);
                startActivity(i);
                finish();
            }
        });
    }
}


