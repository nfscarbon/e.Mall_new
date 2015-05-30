package utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.intigate.ratnav.emall_new.R;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class GCMIntentService extends GCMBaseIntentService {
    Bitmap myBitmap;
    URL url;
    String mMsg = "";



    private static final String TAG = "GCMIntentService";


    Context context;

    public GCMIntentService() {

        super("532525057957");
//        super("408946504158");

    }

    private static int num = 0;

    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.d(TAG, "Device registered: regId = " + registrationId);
        new PrefUtils().saveToPrefs(context, PrefUtils.registrationId, registrationId);
    }


    /**
     * Method called on device un registred
     */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.e(TAG, "Device unregistered");

    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        Log.i(TAG, intent.getStringExtra("message"));
        this.context = context;


        new Asy().execute(intent.getStringExtra("message"));
    }


    @Override
    protected void onError(Context arg0, String arg1) {
    }

    class Asy extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try {
                InputStream in;

                url = new URL(new JSONObject(params[0]).getString("Image"));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(in);

                mMsg = params[0];
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            Random random = new Random();
            int m = random.nextInt(9999 - 1000) + 1000;
            try {
                if (Build.VERSION.SDK_INT < 16) {
                    Notification notif = new Notification.Builder(context)
                            .setContentTitle(new JSONObject(mMsg).getString("Subject"))
                            .setContentText(new JSONObject(mMsg).getString("Body"))
                            .setSmallIcon(R.drawable.icon_main)

                            .setStyle(new Notification.BigPictureStyle()
                                    .bigPicture(myBitmap)

                                    .setBigContentTitle(new JSONObject(mMsg).getString("CompanyName")))
                            .getNotification();
                    notificationManager.notify(m, notif);

                } else {
                    Notification notif = new Notification.Builder(context)
                            .setContentTitle(new JSONObject(mMsg).getString("Subject"))
                            .setContentText(new JSONObject(mMsg).getString("Body"))

                            .setStyle(new Notification.BigPictureStyle()
                                    .bigPicture(myBitmap)
                                    .setBigContentTitle(new JSONObject(mMsg).getString("CompanyName")))
                            .build();
                    notificationManager.notify(m, notif);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}