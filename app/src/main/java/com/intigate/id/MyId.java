package com.intigate.id;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.intigate.ratnav.emall_new.R;
import com.intigate.setup.Login;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import listner.Listener_service;
import listner.PostData;
import listner.Toast;
import utils.Contents;
import utils.Fonts;
import utils.PrefUtils;
import utils.QRCodeEncoder;

/**
 * Created by ratnav on 28-05-2015.
 */
public class MyId extends ActionBarActivity implements Listener_service {
    TextView tv_title;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    ImageView iv_qrcode, iv_barcode, iv_user;
    utils.VerticalTextView tv_id;
    TextView tv_id_unumber;
    Random rand = new Random();
    utils.VerticalTextView tv_userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        //Rd
        setContentView(R.layout.activity_my_id);
        getSupportActionBar().hide();
        setUpFooter();
        /*
        Get User Profile
         */
        if (new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.user_Name, "").equals("")) {
            getUserProfile();
        }
        tv_userName = (utils.VerticalTextView) findViewById(R.id.textView);
        tv_userName.setTypeface(new Fonts(getApplicationContext()).font_arial());
        tv_userName.setText(new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.user_Name, ""));
        getUserProfile();
        iv_user = (ImageView) findViewById(R.id.imageView2);
        setUpTitle("My ID");
        try {

            Picasso.with(getApplicationContext()).load(new PrefUtils().getFromPrefs(getApplicationContext(), new PrefUtils().user_UserPhoto, "")).into(iv_user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_id_unumber = (TextView) findViewById(R.id.textView2);
        int[] color = {Color.WHITE, Color.parseColor("#c0c0c0")};
        float[] position = {0, 1};
        Shader.TileMode tile_mode = Shader.TileMode.REPEAT;
        LinearGradient lin_grad = new LinearGradient(0, 0, 0, 35, color, position, tile_mode);
        Shader shader_gradient = lin_grad;
        tv_id_unumber.getPaint().setShader(shader_gradient);
        String ll_number = new PrefUtils().getFromPrefs(getApplicationContext(), new PrefUtils().LoyaltyId, "");
        String ll_number_1 = ll_number.substring(0, 4) + " ";
        ll_number_1 = ll_number_1 + rand.nextInt(9) + rand.nextInt(9) + rand.nextInt(9) + rand.nextInt(9) + " " + rand.nextInt(9) + rand.nextInt(9) + rand.nextInt(9) + rand.nextInt(9) + " " + rand.nextInt(9) + rand.nextInt(9) + rand.nextInt(9) + ll_number.substring(5, 6);
        tv_id_unumber.setText(ll_number_1);
        Fonts mFonts = new Fonts(MyId.this);
        tv_id_unumber.setTypeface(mFonts.font_card());
        tv_id = (utils.VerticalTextView) findViewById(R.id.textView);
        tv_id.setTypeface(mFonts.font_arial());
        tv_id.setText(new PrefUtils().getFromPrefs(getApplicationContext(), new PrefUtils().user_Name, ""));


        iv_barcode = (ImageView) findViewById(R.id.iv_bar);
        iv_qrcode = (ImageView) findViewById(R.id.iv_qr);


        try {
            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;
            ;
            Matrix matrix = new Matrix();

            matrix.postRotate(90);
            Bitmap b = (encodeAsBitmap(new PrefUtils().getFromPrefs(getApplicationContext(), new PrefUtils().LoyaltyId, "123456"), BarcodeFormat.CODE_39, width - (width / 3),
                    width / 5));


            Bitmap rotatedBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
            iv_barcode.setImageBitmap(rotatedBitmap);


            QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(new PrefUtils().getFromPrefs(getApplicationContext(), new PrefUtils().LoyaltyId, "123456"),
                    null,
                    Contents.Type.TEXT,
                    BarcodeFormat.QR_CODE.toString(),
                    smallerDimension);
            try {
                Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();

                iv_qrcode.setImageBitmap(bitmap);

            } catch (WriterException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
        }

    }

    private void setUpFooter() {
        LinearLayout ll_myProfile = (LinearLayout) findViewById(R.id.ll_myProfile);
        ll_myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_ll_myProfile = new Intent(MyId.this, MyProfile.class);
                startActivity(i_ll_myProfile);
                finish();
            }
        });

        LinearLayout ll_mail = (LinearLayout) findViewById(R.id.ll_mail);
        ll_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_ll_myProfile = new Intent(MyId.this, MailBox.class);
                startActivity(i_ll_myProfile);
                finish();
            }
        });

    }


    private void setUpTitle(String title) {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(title);
        tv_title.setTypeface(new Fonts(getApplicationContext()).font_arial());
        tv_title.setShadowLayer(15, 5, 5, 0xFF303030);

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }


    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width,
                          int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
            hints.put(EncodeHintType.MARGIN, -2);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width,
                    img_height, hints);
        } catch (IllegalArgumentException iae) {

            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }


    public void back(View c) {

        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onRequestSuccess(int method, String response) {

        try {

            JSONObject obj = new JSONObject(response.replace("[", "").replace("]", ""));

            String Name = obj.getString("Name");
            String MobileNo = obj.getString("MobileNo");
            String Email = obj.getString("Email");
            String UserPhoto = obj.getString("UserPhoto");

            new PrefUtils().saveToPrefs(getApplicationContext(), PrefUtils.user_Name, Name);
            new PrefUtils().saveToPrefs(getApplicationContext(), PrefUtils.user_MobileNo, MobileNo);
            new PrefUtils().saveToPrefs(getApplicationContext(), PrefUtils.user_Email, Email);
            new PrefUtils().saveToPrefs(getApplicationContext(), PrefUtils.user_UserPhoto, UserPhoto);

            MyId.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    tv_userName.setText(new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.user_Name, ""));

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestFail(int method, String message) {
        MyId.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new Toast(getApplicationContext(), "Please Check Your N/w Connection !").show();


            }
        });
    }

    @Override
    public void onStatus404() {

        MyId.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Toast(getApplicationContext(), "You are a invalid user").show();
                new PrefUtils().saveToPrefs(getApplicationContext(), "IsLoggedIn", "0");
                Intent i = new Intent(MyId.this, Login.class);
                startActivity(i);
                finish();
            }
        });


    }


    public void getUserProfile() {
        JSONObject object = new JSONObject();

        try {

            object.put("LoyltyID", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.LoyaltyId, ""));

            object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.SimNumber, ""));


            object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.UDID, ""));

            new PostData(0, object.toString(), "GetSubscriberProfile ", MyId.this).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

