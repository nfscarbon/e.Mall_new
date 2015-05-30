package com.intigate.id;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.intigate.ratnav.emall_new.MainActivity;
import com.intigate.ratnav.emall_new.R;
import com.intigate.setup.Login;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import listner.Listener_service;
import listner.PostData;
import utils.Fonts;
import utils.PrefUtils;

/**
 * Created by ratnav on 28-05-2015.
 */
public class MailBox extends ActionBarActivity implements Listener_service {
    ListView list_mail;
    Button btn_all, btn_unread, btn_read;
    ImageButton img_writemail;
    MailHandler[] myHandler_read, myHandler_unread, MyHandler_original = null;
    MailAdapter adapter;
    JSONObject jsonObject;
    ListView lv;
    int size_read = 0;
    int size_un_read = 0;
    Fonts mFonts;

    TextView tv_mails, tv_announcements;

    Announcements_adapter adapter_Announcements_adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.mail_box);
        mFonts = new Fonts(getApplicationContext());
        setUpFooter();
        init();
    }

    public void init() {
        tv_mails = (TextView) findViewById(R.id.tv_mails);
        tv_mails.setTypeface(mFonts.arial_bold());
        tv_announcements = (TextView) findViewById(R.id.tv_announcements);
        tv_announcements.setTypeface(mFonts.arial_bold());
        tv_announcements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnnouncements();
            }
        });
        btn_all = (Button) findViewById(R.id.btn_all);
        btn_all.setTypeface(mFonts.font_arial());
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_all_click();
                adapter = new MailAdapter(MailBox.this, MyHandler_original);

                lv.setAdapter(adapter);

            }
        });
        btn_unread = (Button) findViewById(R.id.btn_unread);
        btn_unread.setTypeface(mFonts.font_arial());
        btn_unread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_unread_click();
                adapter = new MailAdapter(MailBox.this, myHandler_unread);

                lv.setAdapter(adapter);

            }
        });
        btn_read = (Button) findViewById(R.id.btn_read);
        btn_read.setTypeface(mFonts.font_arial());
        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_read_click();
                adapter = new MailAdapter(MailBox.this, myHandler_read);

                lv.setAdapter(adapter);
            }
        });

        list_mail = (ListView) findViewById(R.id.list_mail);
        img_writemail = (ImageButton) findViewById(R.id.img_writemail);
        img_writemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_sendMial = new Intent(MailBox.this, SendMail.class);
                startActivity(i_sendMial);
                overridePendingTransition(0, 0);

            }
        });
        lv = (ListView) findViewById(R.id.list_mail);
        getMail();
    }

    public void back(View c) {

        finish();
    }


    public void getMail() {


        JSONObject object = new JSONObject();

        try {

            object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.SimNumber, ""));
            object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.UDID, ""));
            object.put("LoyltyID", new PrefUtils().getFromPrefs(getApplicationContext(), new PrefUtils().LoyaltyId, ""));

        } catch (Exception e) {
            e.printStackTrace();
        }

        new PostData(0, object.toString(), "GetMails", MailBox.this).execute();


    }

    @Override
    public void onRequestSuccess(int method, String response) {


        Log.e("Resposne@@@@", response);
        if (method == 10) {


            try {
                JSONArray arr = new JSONArray(response);
                Moder_Announcements myHandler[] = new Moder_Announcements[arr.length()];
                if (arr.length() > 0) {
                    for (int i = 0; i < arr.length(); i++) {
                        jsonObject = arr.getJSONObject(i);
                        Moder_Announcements handler = new Moder_Announcements();
                        handler.setLoyltyId(jsonObject.getInt("LoyltyId"));
                        handler.setNotificationId(jsonObject.getString("NotificationId"));
                        handler.setSubject(jsonObject.getString("Subject"));
                        handler.setBody(jsonObject.getString("Body"));
                        handler.setImage(jsonObject.getString("Image"));
                        handler.setNotificationTypeId(jsonObject.getInt("NotificationTypeId"));
                        handler.setCompanyName(jsonObject.getString("CompanyName"));
                        handler.setLogoURL(jsonObject.getString("LogoURL"));
                        handler.setIsVisible(false);
                        if (jsonObject.getInt("NotificationTypeId") == 1) {

                        } else if (jsonObject.getInt("NotificationTypeId") == 2) {

                        }


                        myHandler[i] = handler;
                    }


                    adapter_Announcements_adapter = new Announcements_adapter(MailBox.this, myHandler);

                    MailBox.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lv.setAdapter(adapter_Announcements_adapter);
                        }
                    });

                } else {
                    MailBox.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new listner.Toast(getApplicationContext(), "No Announcement For You Yet").show();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            try {
                JSONArray arr = new JSONArray(response);

                if (MyHandler_original == null) {
                    MyHandler_original = new MailHandler[arr.length()];
                }

                for (int i = 0; i < arr.length(); i++) {
                    jsonObject = arr.getJSONObject(i);
                    if (jsonObject.getBoolean("IsRead")) {
                        size_read++;
                    } else {
                        size_un_read++;
                    }
                }

                myHandler_read = new MailHandler[size_read];
                myHandler_unread = new MailHandler[size_un_read];
                int j = 0, k = 0;
                if (arr.length() > 0) {
                    for (int i = 0; i < arr.length(); i++) {
                        jsonObject = arr.getJSONObject(i);

                        MailHandler handler = new MailHandler();

                        handler.setRead(jsonObject.getBoolean("IsRead"));
                        handler.setUserName(jsonObject.getString("Name"));
                        handler.setMailTitle(jsonObject.getString("Subject"));
                        handler.setMailContent(jsonObject.getString("Body"));
                        handler.setImp(jsonObject.getBoolean("IsImportant"));
                        handler.setTime(getDate(jsonObject.getLong("SendDate"), "hh:mm a"));
                        handler.setConversationID(jsonObject.getInt("ConversationID"));
                        handler.setNoofMails(jsonObject.getInt("NoofMails"));
                        if (jsonObject.getString("LogoURL") != null) {
                            handler.setUserImageUrl(jsonObject.getString("LogoURL"));
                        } else {
                            handler.setFirstLetter("" + jsonObject.getString("Name").charAt(0));
                        }
                        Log.d("Handler", handler.toString());

                        if (jsonObject.getBoolean("IsRead")) {
                            myHandler_read[j] = handler;
                            ++j;
                        } else {
                            myHandler_unread[k] = handler;
                            ++k;
                        }

                        MyHandler_original[i] = handler;

                    }

                    adapter = new MailAdapter(MailBox.this, MyHandler_original);
                    MailBox.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lv.setAdapter(adapter);
                        }
                    });

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestFail(int method, String message) {
        MailBox.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new listner.Toast(getApplicationContext(), "Please check your N/w Connection").show();

            }
        });
    }

    @Override
    public void onStatus404() {
        MailBox.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new listner.Toast(getApplicationContext(), "You are a invalid user").show();
                new PrefUtils().saveToPrefs(getApplicationContext(), "IsLoggedIn", "0");
                Intent i = new Intent(MailBox.this, Login.class);
                startActivity(i);
                finish();
            }
        });

    }

    public static String getDate(long milliSeconds, String dateFormat) {

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


    public void button_all_click() {

        btn_all.setBackgroundColor(getResources().getColor(R.color.color_header));
        btn_unread.setBackgroundColor(getResources().getColor(R.color.color_white));
        btn_read.setBackgroundColor(getResources().getColor(R.color.color_white));

        btn_all.setTextColor(getResources().getColor(R.color.color_white));
        btn_unread.setTextColor(getResources().getColor(R.color.color_header_text));
        btn_read.setTextColor(getResources().getColor(R.color.color_header_text));


    }

    public void button_unread_click() {

        btn_all.setBackgroundColor(getResources().getColor(R.color.color_white));
        btn_unread.setBackgroundColor(getResources().getColor(R.color.color_header));
        btn_read.setBackgroundColor(getResources().getColor(R.color.color_white));

        btn_all.setTextColor(getResources().getColor(R.color.color_header_text));
        btn_unread.setTextColor(getResources().getColor(R.color.color_white));
        btn_read.setTextColor(getResources().getColor(R.color.color_header_text));

    }

    public void button_read_click() {

        btn_all.setBackgroundColor(getResources().getColor(R.color.color_white));
        btn_unread.setBackgroundColor(getResources().getColor(R.color.color_white));
        btn_read.setBackgroundColor(getResources().getColor(R.color.color_header));

        btn_all.setTextColor(getResources().getColor(R.color.color_header_text));
        btn_unread.setTextColor(getResources().getColor(R.color.color_header_text));
        btn_read.setTextColor(getResources().getColor(R.color.color_white));

    }


    public void getAnnouncements() {


        JSONObject object = new JSONObject();


        try {
            object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.SimNumber, ""));
            object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.UDID, ""));
            object.put("LoyltyID", new PrefUtils().getFromPrefs(getApplicationContext(), new PrefUtils().LoyaltyId, ""));

        } catch (Exception e) {
            e.printStackTrace();
        }


//        Listener_service Listener_service = new Listener_service() {
//            @Override
//            public void onRequestSuccess(int method, String response) {
//
//                Log.e("Resposne@@@@", response);
//
//
//                try {
//                    JSONArray arr = new JSONArray(response);
//                    Moder_Announcements myHandler[] = new Moder_Announcements[arr.length()];
//                    if (arr.length() > 0) {
//                        for (int i = 0; i < arr.length(); i++) {
//                            jsonObject = arr.getJSONObject(i);
//                            Moder_Announcements handler = new Moder_Announcements();
//                            handler.setLoyltyId(jsonObject.getInt("LoyltyId"));
//                            handler.setNotificationId(jsonObject.getString("NotificationId"));
//                            handler.setSubject(jsonObject.getString("Subject"));
//                            handler.setBody(jsonObject.getString("Body"));
//                            handler.setImage(jsonObject.getString("Image"));
//                            handler.setNotificationTypeId(jsonObject.getInt("NotificationTypeId"));
//                            handler.setCompanyName(jsonObject.getString("CompanyName"));
//                            handler.setLogoURL(jsonObject.getString("LogoURL"));
//                            handler.setIsVisible(false);
//                            if (jsonObject.getInt("NotificationTypeId") == 1) {
//
//                            } else if (jsonObject.getInt("NotificationTypeId") == 2) {
//
//                            } else {
//
//                            }
//
//
//                            myHandler[i] = handler;
//                        }
//
//
//                        adapter_Announcements_adapter = new Announcements_adapter(getActivity(), myHandler);
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                lv.setAdapter(adapter_Announcements_adapter);
//                            }
//                        });
//
//                    } else {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                LayoutInflater li = getActivity().getLayoutInflater();
//                                //Getting the View object as defined in the customtoast.xml file
//                                View layout = li.inflate(R.layout.customtoast,
//                                        (ViewGroup) getActivity().findViewById(R.id.custom_toast_layout));
//                                TextView tv_msg = (TextView) layout.findViewById(R.id.custom_toast_message);
//                                tv_msg.setText("No Announcements For You Yet !");
//
//                                Toast toast = new Toast(getActivity().getApplicationContext());
//                                toast.setDuration(Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.TOP, 0, 40);
//                                toast.setView(layout);//setting the view of custom toast layout
//                                toast.show();
//                            }
//                        });
//                    }
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//            @Override
//            public void onRequestFail(int method, String message) {
//
//            }
//        };
        new PostData(10, object.toString(), "GetNotifications", MailBox.this).execute();


    }


    private void setUpFooter() {
        LinearLayout ll_myProfile = (LinearLayout) findViewById(R.id.ll_myid);
        ll_myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_ll_myProfile = new Intent(MailBox.this, MyId.class);
                startActivity(i_ll_myProfile);
                finish();
            }
        });

        LinearLayout ll_mail = (LinearLayout) findViewById(R.id.ll_myProfile);
        ll_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_ll_myProfile = new Intent(MailBox.this, MyProfile.class);
                startActivity(i_ll_myProfile);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i_ll_myProfile = new Intent(MailBox.this, MainActivity.class);
        startActivity(i_ll_myProfile);
        finish();
    }
}
