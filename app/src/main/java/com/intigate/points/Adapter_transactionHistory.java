package com.intigate.points;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intigate.emall.R;


/**
 * Created by ratnav on 18-04-2015.
 */
public class Adapter_transactionHistory extends BaseAdapter {
    TransactionHistory_Handler[] result;
    Context context;
    private static LayoutInflater inflater = null;

    public Adapter_transactionHistory(Activity mainActivity, TransactionHistory_Handler[] list) {

        result = list;
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

    public class Holder {

        TextView th_tv_points,
                th_tv_cmp,

        th_tv_date,
                th_tv_time;

        ImageView th_iv;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.adapter_transaction_history, null, true);
        holder.th_tv_points = (TextView) rowView.findViewById(R.id.th_tv_points);
        holder.th_tv_cmp = (TextView) rowView.findViewById(R.id.th_tv_cmp);
        holder.th_tv_date = (TextView) rowView.findViewById(R.id.th_tv_date);
        holder.th_tv_time = (TextView) rowView.findViewById(R.id.th_tv_time);

        holder.th_iv = (ImageView) rowView.findViewById(R.id.th_iv);


        TransactionHistory_Handler mailHandler = result[position];

        if (mailHandler != null) {
            holder.th_tv_points.setText("" + mailHandler.getPoints());
            holder.th_tv_cmp.setText(mailHandler.getCompany());
            holder.th_tv_date.setText(mailHandler.getDate());
            holder.th_tv_time.setText(mailHandler.getTime());
            if (mailHandler.getIsUp()) {
                holder.th_iv.setImageResource(R.drawable.arrow_top);
                holder.th_tv_points.setTextColor(Color.RED);
            } else {
                holder.th_iv.setImageResource(R.drawable.arrow_bottom);
                holder.th_tv_points.setTextColor(Color.BLACK);
            }
        }

        return rowView;
    }

}
