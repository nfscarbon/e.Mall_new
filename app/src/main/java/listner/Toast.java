package listner;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intigate.ratnav.emall_new.R;

import utils.Fonts;

/**
 * Created by ratnav on 29-05-2015.
 */
public class Toast {
android.widget.Toast mToast;
//    public Toast (Activity activity, String msg) {
//        LayoutInflater li = activity.getLayoutInflater();
//        View layout = li.inflate(R.layout.customtoast,
//                (ViewGroup) activity.findViewById(R.id.custom_toast_layout));
//        TextView tv_msg = (TextView) layout.findViewById(R.id.custom_toast_message);
//        tv_msg.setText("No mails yet !");
//
//        android.widget.Toast toast = new android.widget.Toast(activity.getApplicationContext());
//        toast.setDuration(android.widget.Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.TOP, 0, 40);
//        toast.setView(layout);
//       mToast=toast;
//
//    }
    public void show(){
        mToast.show();
    }

    public Toast(Context activity, String msg) {
        LayoutInflater li = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate( R.layout.customtoast, null );

        TextView tv_msg = (TextView) view.findViewById(R.id.custom_toast_message);
        tv_msg.setText(msg);
        tv_msg.setTypeface(new Fonts(activity).font_regular());

        android.widget.Toast toast = new android.widget.Toast(activity);
        toast.setDuration(android.widget.Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.TOP, 0, 40);
        toast.setView(view);
       mToast=toast;

    }
}
