package com.intigate.points;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intigate.emall.R;
import com.squareup.picasso.Picasso;


/**
 * Created by ratnav on 26-05-2015.
 */
public class Adapter_transaction_cmp extends BaseAdapter {
    Transaction_cmp_handler[] result;
    Context context;
    private static LayoutInflater inflater = null;

    public Adapter_transaction_cmp(Activity mainActivity, Transaction_cmp_handler[] list) {

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

        TextView th_tv_points;


        ImageView th_iv;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.adaptr_transaction_cmp_history, null, true);
        holder.th_tv_points = (TextView) rowView.findViewById(R.id.tv_cmpName);


        holder.th_iv = (ImageView) rowView.findViewById(R.id.iv_cmp);


        Transaction_cmp_handler mailHandler = result[position];
        Picasso.with(context).load(mailHandler.getUrl()).into(holder.th_iv);

        holder.th_tv_points.setText("" + mailHandler.getPoints());


        return rowView;
    }

}
