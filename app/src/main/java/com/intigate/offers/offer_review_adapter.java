package com.intigate.offers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.intigate.emall.R;

import utils.Fonts;

/**
 * Created by ratnav on 17-04-2015.
 */
public class offer_review_adapter extends ArrayAdapter<String> {
    Activity context;
    Handler_user_review[] mHandler_user_review;
    LayoutInflater inflater;
    ViewHolder holder = null;
    Fonts mFonts;

    public offer_review_adapter(Activity context, Handler_user_review[] mHandler_user_review) {
        super(context, R.layout.adapter_user_review);
        // TODO Auto-generated constructor stub
        this.mHandler_user_review = mHandler_user_review;

        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFonts = new Fonts(context.getApplicationContext());
    }

    @Override
    public int getCount() {
        return mHandler_user_review.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View rowview = convertView;

        if (rowview == null) {

            rowview = inflater.inflate(R.layout.adapter_user_review, null, true);
            holder = new ViewHolder(rowview);
            rowview.setTag(holder);

        } else {
            holder = (ViewHolder) rowview.getTag();
        }

        try {
            holder.r_tv_username.setText("by " + mHandler_user_review[position].getUser_name());
            holder.r_tv_username.setTypeface(mFonts.font_bold());
            holder.tv_date.setText(mHandler_user_review[position].getTime());
            holder.tv_date.setTypeface(mFonts.font_regular());
            holder.comment.setText(mHandler_user_review[position].getComment());
            holder.comment.setTypeface(mFonts.font_regular());
            holder.ratingBar.setRating(mHandler_user_review[position].getRating());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rowview;

    }

    public class ViewHolder {
        public TextView r_tv_username, tv_date, comment;

        public RatingBar ratingBar;

        public ViewHolder(View v) {
            r_tv_username = (TextView) v.findViewById(R.id.r_tv_username);
            tv_date = (TextView) v.findViewById(R.id.tv_date);
            comment = (TextView) v.findViewById(R.id.comment);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);


        }
    }

}