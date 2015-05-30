package com.intigate.offers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.intigate.ratnav.emall_new.R;
import com.squareup.picasso.Picasso;

import java.util.Vector;

public class Img_Adapter extends BaseAdapter {

    Vector<String> img;
    LayoutInflater inflater;
    Context context;


    public Img_Adapter(Context context, Vector<String> img) {
        this.img = img;
        this.context = context;
        inflater = LayoutInflater.from(this.context);        // only context can also be used
    }

    @Override
    public int getCount() {
        return img.size();
    }

    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.offer_adpter, null);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }




        Picasso.with(context).load(img.elementAt(position)).placeholder(R.drawable.icon_main).into(mViewHolder.ivIcon);
        return convertView;
    }


    private class MyViewHolder {
        ImageView ivIcon;

        MyViewHolder(View convertView) {

            ivIcon = (ImageView) convertView.findViewById(R.id.img_grid);
        }


    }

}