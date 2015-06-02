package com.intigate.id;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intigate.emall.R;
import com.squareup.picasso.Picasso;



/**
 * Created by ratnav on 18-05-2015.
 */
public class Mail_details_adapter extends BaseAdapter {
    Model_mail_details[] result;
    Context context;
    private static LayoutInflater inflater = null;
    Model_mail_details mailHandler_;
    Holder holder;
    int counter = 0;
    String[] color = {"#FA8606", "#7BC947", "#1CB569", "#7431C4"};

    Mail_details_adapter(Activity mainActivity, Model_mail_details[] list) {

        this.result = list;
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
    public View getView(final  int position, View convertView, ViewGroup parent) {


        View rowView = convertView;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.mail_details_adapter, null, true);
            holder = new Holder(rowView);
            rowView.setTag(holder);
        } else {
            holder = (Holder) rowView.getTag();
        }


        mailHandler_ = this.result[position];

        if (mailHandler_ != null) {


            holder.adapter_userName.setText(mailHandler_.getName());
            holder.adapter_mail_title.setText(mailHandler_.getSubject());
            holder.adapter_tv_content.setText(mailHandler_.getBody());

            if(mailHandler_.getSendBy()==1){

                holder.adapter_time.setText(mailHandler_.getSendDate());
                if (mailHandler_.getUserPhoto().length() < 5) {

                    holder.adapter_iv.setVisibility(View.GONE);
                    holder.adapter_iv_tv.setVisibility(View.VISIBLE);
                    holder.adapter_iv_tv.setText("" + mailHandler_.getName().charAt(0));
                    holder.adapter_iv_tv.setBackgroundColor(Color.parseColor("" + color[counter]));
                    ++counter;
                    if (counter == 4) {
                        counter = 0;
                    }

                } else {
                    Picasso.with(context).load(mailHandler_.getUserPhoto()).into(holder.adapter_iv);
                    holder.adapter_iv.setVisibility(View.VISIBLE);
                    holder.adapter_iv_tv.setVisibility(View.GONE);

                }
            }else {

                holder.adapter_time.setText(mailHandler_.getReplyDate());

                if (mailHandler_.getLogoURL().length() < 5) {

                    holder.adapter_iv.setVisibility(View.GONE);
                    holder.adapter_iv_tv.setVisibility(View.INVISIBLE);
                    holder.adapter_iv_tv.setText("" + mailHandler_.getName().charAt(0));
                    holder.adapter_iv_tv.setBackgroundColor(Color.parseColor("" + color[counter]));
                    ++counter;
                    if (counter == 4) {
                        counter = 0;
                    }

                } else {

                    Picasso.with(context).load(mailHandler_.getLogoURL()).into(holder.adapter_iv);
                    holder.adapter_iv.setVisibility(View.VISIBLE);
                    holder.adapter_iv_tv.setVisibility(View.GONE);

                }

            }


          //  Log.e("Image", mailHandler_.getUserImageUrl());







        }


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        return rowView;
    }


    public class Holder {

        TextView adapter_iv_tv, adapter_userName, adapter_time, adapter_mail_title, adapter_tv_content;

        ImageView adapter_imp, adapter_iv;

        LinearLayout adapter_ll_mail;


        Holder(View rowView) {
            adapter_iv_tv = (TextView) rowView.findViewById(R.id.adapter_iv_tv);
            adapter_userName = (TextView) rowView.findViewById(R.id.adapter_userName);
            adapter_time = (TextView) rowView.findViewById(R.id.adapter_time);
            adapter_mail_title = (TextView) rowView.findViewById(R.id.adapter_mail_title);
            adapter_tv_content = (TextView) rowView.findViewById(R.id.adapter_tv_content);
            adapter_imp = (ImageView) rowView.findViewById(R.id.adapter_imp);
            adapter_iv = (ImageView) rowView.findViewById(R.id.adapter_iv);
            adapter_ll_mail = (LinearLayout) rowView.findViewById(R.id.adapter_ll_mail);

        }
    }



}
