package com.intigate.id;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;


import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;



import com.intigate.ratnav.emall_new.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Vector;

import utils.PrefUtils;


/**
 * Created by ratnav on 18-04-2015.
 */
public class SendMail extends Activity {
// hi i am KK
    Vector<Integer> v_id, v_id_msgType;
    Spinner spinner_cmpytype, spinner_msgtype;
    Button btn_submit;
    EditText edittext_subject, edittext_description;
    int cmp_id, MailTypeID;

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

        SoapObject obj = new SoapObject("http://tempuri.org/", "GetCompany");

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
//                getMailType();
//                setUpSpinner(method, response);
//            }
//
//            @Override
//            public void onRequestFail(int method, String message) {
//
//            }
//        };
//        new PostData_fragment(0, obj, "GetCompany", SendMail.this, Listener_service).execute();

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

        SoapObject obj = new SoapObject("http://tempuri.org/", "SendMail");

        JSONObject object = new JSONObject();
        try {

            object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext().getApplicationContext(), "SimNumber", ""));
            object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext().getApplicationContext(), "UDID", ""));
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
        PropertyInfo mPropertyInfo = new PropertyInfo();
        mPropertyInfo.setName("data");
        mPropertyInfo.setValue(object.toString());
        obj.addProperty(mPropertyInfo);
//        Listener_service Listener_service = new Listener_service() {
//            @Override
//            public void onRequestSuccess(int method, String response) {
//
//                SendMail.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        edittext_description.setText("");
//                        edittext_subject.setText("");
//
//
//                        LayoutInflater li = getLayoutInflater();
//                        View layout = li.inflate(R.layout.customtoast,
//                                (ViewGroup) findViewById(R.id.custom_toast_layout));
//                        TextView tv_msg = (TextView) layout.findViewById(R.id.custom_toast_message);
//                        tv_msg.setText("Mail has been sent. !");
//
//                        Toast toast = new Toast(getApplicationContext());
//                        toast.setDuration(Toast.LENGTH_LONG);
//                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.TOP, 0, 40);
//                        toast.setView(layout);
//                        toast.show();
//                        finish();
//                    }
//                });
//
//            }
//
//            @Override
//            public void onRequestFail(int method, String message) {
//                SendMail.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                LayoutInflater li = getLayoutInflater();
//                View layout = li.inflate(R.layout.customtoast,
//                        (ViewGroup) findViewById(R.id.custom_toast_layout));
//                TextView tv_msg = (TextView) layout.findViewById(R.id.custom_toast_message);
//                tv_msg.setText("Unable To send Mail. !");
//
//                Toast toast = new Toast(getApplicationContext());
//                toast.setDuration(Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.TOP, 0, 40);
//                toast.setView(layout);
//                toast.show();}});
//
//            }
//        };
//        new PostData_fragment(1, obj, "SendMail", SendMail.this, Listener_service).execute();

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
}


