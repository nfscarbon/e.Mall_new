package com.intigate.id;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intigate.emall.R;
import com.intigate.setup.Login;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import listner.Listener_service;
import listner.PostData;
import listner.Toast;
import utils.Fonts;
import utils.PrefUtils;

/**
 * Created by ratnav on 29-05-2015.
 */
public class MailAdapter extends BaseAdapter implements Listener_service {
    MailHandler[] result;
    Activity context;
    private static LayoutInflater inflater = null;
    MailHandler mailHandler_;
    Holder holder;
    Fonts mFonts;
    boolean bool;

    MailAdapter(Activity mainActivity, MailHandler[] list) {

        this.result = list;
        mFonts = new Fonts(mainActivity);
        context = mainActivity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return result.length;
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View rowView = convertView;
        if (rowView == null) {

            rowView = inflater.inflate(R.layout.email_adapter, null, true);
            holder = new Holder(rowView);
            rowView.setTag(holder);

        } else {
            holder = (Holder) rowView.getTag();
        }


        mailHandler_ = this.result[position];
        if (mailHandler_ != null) {
            if (mailHandler_.isRead()) {
                holder.adapter_ll_mail.setBackgroundColor(Color.parseColor("#3500AFFF"));
            } else {
                holder.adapter_ll_mail.setBackgroundColor(Color.parseColor("#FFFFFF"));

            }
            holder.adapter_userName.setTypeface(mFonts.arial_bold());
            holder.adapter_userName.setText(mailHandler_.getUserName());
            holder.adapter_mail_title.setTypeface(mFonts.font_arial());
            holder.adapter_mail_title.setText(mailHandler_.getMailTitle());
            holder.adapter_tv_content.setTypeface(mFonts.font_arial());
            holder.adapter_tv_content.setText(mailHandler_.getMailContent());
            holder.adapter_time.setTypeface(mFonts.font_arial());
            holder.adapter_time.setText(mailHandler_.getTime());


            if (mailHandler_.getUserImageUrl().length() < 5) {

                holder.adapter_iv.setVisibility(View.GONE);
                holder.adapter_iv_tv.setVisibility(View.VISIBLE);
                holder.adapter_iv_tv.setText("" + mailHandler_.getUserName().charAt(0));


            } else {
                Picasso.with(context).load(mailHandler_.getUserImageUrl()).placeholder(R.drawable.icon_main).into(holder.adapter_iv);
                holder.adapter_iv.setVisibility(View.VISIBLE);
                holder.adapter_iv_tv.setVisibility(View.GONE);

            }

            holder.adapter_imp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (result[position].isImp()) {
                        result[position].setImp(false);

                        markImp(position, false);
                    } else {
                        result[position].setImp(true);
                        markImp(position, true);

                    }

                    notifyDataSetChanged();

                }
            });

            if (mailHandler_.isImp()) {
                holder.adapter_imp.setImageResource(R.drawable.yellow_star);

            } else {
                holder.adapter_imp.setImageResource(R.drawable.white_star);

            }

            if (mailHandler_.isRead()) {
                rowView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.adapter_mail_title.setTypeface(null, Typeface.NORMAL);
                holder.adapter_mail_title.setTextColor(Color.parseColor("#ff4c4c4c"));

            } else {
                rowView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.adapter_mail_title.setTypeface(null, Typeface.BOLD);
                holder.adapter_mail_title.setTextColor(Color.parseColor("#000000"));


            }

        }


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                result[position].setIsRead(true);

                notifyDataSetChanged();
                Intent inte_detail = new Intent(context, Details_mail.class);
                inte_detail.putExtra("id", result[position].getConversationID());
                inte_detail.putExtra("isImp", result[position].isImp());


                context.startActivity(inte_detail);
            }
        });


        return rowView;
    }

    @Override
    public void onRequestSuccess(int method, String response) {

        switch (method) {
            case 1:
                if (bool) {

                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            new Toast(context, "You marked this as important.").show();
                        }
                    });
                } else {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            new Toast(context, "You marked this as Unimportant.").show();
                        }
                    });
                }

                break;
        }

    }

    @Override
    public void onRequestFail(int method, String message) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new Toast(context, "Something went wrong . Check N.w connection.").show();
            }
        });

    }

    @Override
    public void onStatus404() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Toast(context, "You are a invalid user").show();
                new PrefUtils().saveToPrefs(context, "IsLoggedIn", "0");
                Intent i = new Intent(context, Login.class);
                context.startActivity(i);
                context.finish();
            }
        });
    }


    public class Holder {

        TextView adapter_iv_tv, adapter_userName, adapter_time, adapter_mail_title, adapter_tv_content;

        ImageView adapter_imp, adapter_iv;

        LinearLayout adapter_ll_mail;
        View view_status;

        Holder(View rowView) {
            adapter_iv_tv = (TextView) rowView.findViewById(R.id.adapter_iv_tv);
            adapter_userName = (TextView) rowView.findViewById(R.id.adapter_userName);
            adapter_time = (TextView) rowView.findViewById(R.id.adapter_time);
            adapter_mail_title = (TextView) rowView.findViewById(R.id.adapter_mail_title);
            adapter_tv_content = (TextView) rowView.findViewById(R.id.adapter_tv_content);
            adapter_imp = (ImageView) rowView.findViewById(R.id.adapter_imp);
            adapter_iv = (ImageView) rowView.findViewById(R.id.adapter_iv);
            adapter_ll_mail = (LinearLayout) rowView.findViewById(R.id.adapter_ll_mail);
            view_status = (View) rowView.findViewById(R.id.view_status);
        }
    }


    public void markImp(int reply, boolean type) {
//        MailOperations

        bool = type;
        JSONObject object = new JSONObject();
        try {

            object.put("SimNumber", new PrefUtils().getFromPrefs(context, PrefUtils.SimNumber, ""));
            object.put("UDID", new PrefUtils().getFromPrefs(context, PrefUtils.UDID, ""));
            object.put("isNew", false);
            object.put("ConversationID", result[reply].getConversationID());
            object.put("LoyltyID", new PrefUtils().getFromPrefs(context, new PrefUtils().LoyaltyId, ""));
            object.put("OperationID", 1);
            object.put("IsImportant", type);


        } catch (Exception e) {
            e.printStackTrace();
        }


        new PostData(1, object.toString(), "MailOperations", context).execute();


    }


}
