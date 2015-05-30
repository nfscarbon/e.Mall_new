package com.intigate.offers;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.intigate.ratnav.emall_new.R;
import com.squareup.picasso.Picasso;

import java.util.Vector;




/**
 * Created by ratnav on 17-04-2015.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    Vector<String> img;

    public ImageAdapter(Context c, Vector<String> img) {
        mContext = c;

        this.img = img;

    }

    public ImageAdapter(Context c, int i) {
        mContext = c;

    }

    public int getCount() {

        return img.size();

    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            imageView.setPadding(10, 10, 10, 10);
        } else {
            imageView = (ImageView) convertView;
        }


        Log.d("@@@%%%", img.elementAt(position).toString());
        Picasso.with(mContext).load(img.elementAt(position)).placeholder(R.drawable.icon_main).centerCrop().resize(300, 300).into(imageView);


        return imageView;
    }


}