package com.intigate.id;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intigate.ratnav.emall_new.R;
import com.squareup.picasso.Picasso;



/**
 * Created by ratnav on 19-05-2015.
 */
public class Announcements_adapter extends BaseAdapter {
    Moder_Announcements[] result;
    Context context;
    private static LayoutInflater inflater = null;
    Moder_Announcements mailHandler_;
    Holder holder;
    int counter = 0;
    String[] color = {"#FA8606", "#7BC947", "#1CB569", "#7431C4"};

    Announcements_adapter(Activity mainActivity, Moder_Announcements[] list) {

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
    public View getView(final int position, View convertView, ViewGroup parent) {


        View rowView = convertView;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.adapter_announcements, null, true);
            holder = new Holder(rowView);
            rowView.setTag(holder);
        } else {
            holder = (Holder) rowView.getTag();
        }


        mailHandler_ = this.result[position];

        if (mailHandler_ != null) {


            Picasso.with(context).load(mailHandler_.getImage()).into(holder.iv_main);
            holder.tv_cmp.setText(mailHandler_.getCompanyName());
            holder.tv_Subject.setText(mailHandler_.getSubject());
            holder.tv_body.setText(mailHandler_.getBody());

           Picasso.with(context).load(mailHandler_.getLogoURL()).into(holder.iv_cmp);
//            if(mailHandler_.isVisible){
//                holder.ll_row_details.setVisibility(View.VISIBLE);
//            }else{
//                holder.ll_row_details.setVisibility(View.GONE);
//            }
        }


//        rowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (mailHandler_.isVisible) {
//                    mailHandler_.setIsVisible(false);
////                    holder.ll_row_details.setVisibility(View.GONE);
//                } else {
//                    mailHandler_.setIsVisible(true);
////                    holder.ll_row_details.setVisibility(View.VISIBLE);
//                }
//                notifyDataSetChanged();
//            }
//        });


        return rowView;
    }


    public class Holder {

        LinearLayout ll_row_details;
        ImageView iv_main,iv_cmp;
        TextView tv_cmp, tv_Subject, tv_body;

        Holder(View rowView) {
            ll_row_details = (LinearLayout) rowView.findViewById(R.id.ll_row_details);
            iv_main = (ImageView) rowView.findViewById(R.id.iv_main);
            tv_cmp = (TextView) rowView.findViewById(R.id.tv_cmp);
            tv_Subject = (TextView) rowView.findViewById(R.id.tv_Subject);
            tv_body = (TextView) rowView.findViewById(R.id.tv_body);
            iv_cmp=(ImageView)rowView.findViewById(R.id.iv_cmp);
        }
    }


}
