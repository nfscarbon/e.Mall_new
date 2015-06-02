package com.intigate.id;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intigate.emall.MainActivity;
import com.intigate.emall.R;
import com.intigate.setup.Login;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import listner.Listener_service;
import listner.PostData;
import listner.Toast;
import utils.CircleTransform;
import utils.Fonts;
import utils.PrefUtils;

/**
 * Created by ratnav on 28-05-2015.
 */
public class MyProfile extends ActionBarActivity implements Listener_service {
    TextView tv_title;
    ImageView signup_iv_profile;
    EditText my_id_ed_name, my_id_ed_number, my_id_ed_email_id;
    TextView user_llid;
    Fonts mFonts;
    Button b_update_profile;
    String path = "";
    Uri mImageCaptureUri = null;
    private Bitmap bitmap = null;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 3;
    int MEDIA_TYPE_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(0, 0);
        setContentView(R.layout.my_id_profile);

        mFonts = new Fonts(getApplicationContext());
        getSupportActionBar().hide();

        setUpTitle("My Profile");

        setUpFooter();

        setUpUserDetails();

    }



    private void setUpTitle(String title) {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(title);
        tv_title.setTypeface(mFonts.font_arial());
        tv_title.setShadowLayer(15, 5, 5, 0xFF303030);

    }

    private void setUpUserDetails() {

        signup_iv_profile = (ImageView) findViewById(R.id.signup_iv_profile);
        if (new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.user_UserPhoto, "").equals("")) {

        } else {
            Picasso.with(getApplicationContext()).load(new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.user_UserPhoto, "")).placeholder(R.drawable.default_pic).transform(new CircleTransform()).into(signup_iv_profile);
        }


        user_llid = (TextView) findViewById(R.id.user_llid);
        user_llid.setTypeface(mFonts.font_arial());

        my_id_ed_name = (EditText) findViewById(R.id.my_id_ed_name);
        my_id_ed_name.setTypeface(mFonts.font_arial());

        my_id_ed_number = (EditText) findViewById(R.id.my_id_ed_number);
        my_id_ed_number.setTypeface(mFonts.font_arial());

        my_id_ed_email_id = (EditText) findViewById(R.id.my_id_ed_email_id);
        my_id_ed_email_id.setTypeface(mFonts.font_arial());

        TextView tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setTypeface(mFonts.font_arial());
        TextView tv2 = (TextView) findViewById(R.id.tv2);
        tv2.setTypeface(mFonts.font_arial());
        TextView tv3 = (TextView) findViewById(R.id.tv3);
        tv3.setTypeface(mFonts.font_arial());
        TextView tv4 = (TextView) findViewById(R.id.tv4);
        tv4.setTypeface(mFonts.font_arial());

        b_update_profile = (Button) findViewById(R.id.b_update_profile);
        b_update_profile.setTypeface(mFonts.font_arial());
        signup_iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage1();

            }
        });

        b_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
        user_llid.setText(new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.LoyaltyId, ""));
        my_id_ed_name.setText(new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.user_Name, ""));
        my_id_ed_number.setText(new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.user_MobileNo, ""));
        my_id_ed_email_id.setText(new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.user_Email, ""));
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    public void back(View c) {
        Intent i_ll_myProfile = new Intent(MyProfile.this, MainActivity.class);
        startActivity(i_ll_myProfile);
        finish();
        finish();
    }

    private void selectImage1() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(
                MyProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    // create Intent to take a picture and return control to the
                    // calling application
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    mImageCaptureUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create

                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            mImageCaptureUri);
                    intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    // start the image capture Intent
                    startActivityForResult(intent, PICK_FROM_CAMERA);

                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select"),
                            PICK_FROM_FILE);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.


        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "CoinApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".png");
        } else {
            return null;
        }

        return mediaFile;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {


            if (resultCode != RESULT_OK)
                return;
            switch (requestCode) {
                case PICK_FROM_CAMERA:

                    path = mImageCaptureUri.getPath();

                    bitmap = getExifImageBitmap(path,
                            mImageCaptureUri);
                    signup_iv_profile
                            .setImageBitmap(bitmap);

                    break;

                case PICK_FROM_FILE:
                    if (data != null) {


                        mImageCaptureUri = data.getData();

                        path = mImageCaptureUri.getPath();

                        bitmap = getExifImageBitmap(path,
                                mImageCaptureUri);
                        signup_iv_profile.setImageBitmap(bitmap);


                    } else {

                    }

                    break;


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private Bitmap getExifImageBitmap(String selectedPath, Uri selectedImageUri) {
        Bitmap bitmap = null;
        try {
            bitmap = getThumbnail(selectedImageUri);


            bitmap = new CircleTransform().transform(bitmap);

            Matrix matrix = new Matrix();
            matrix.postRotate(getExifOrientation(selectedPath));

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
            long time = System.currentTimeMillis();

            selectedPath = getFilesDir().getAbsolutePath() + File.separator
                    + time + ".png";
            path = selectedPath;
            creatImage(bitmap, selectedPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public Bitmap getThumbnail(Uri uri) throws FileNotFoundException,
            IOException {
        InputStream input = getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;// optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1)
                || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight
                : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > 800) ? (originalSize / 800) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true;// optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
        input = getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }


    private int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0)
            return 1;
        else
            return k;
    }

    public static String creatImage(Bitmap btmp, String path1) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            btmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);


            // you can create a new file name "test.jpg" in sdcard folder.
            File f = new File(path1);
            f.createNewFile();
            // write the bytes in file
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());

            // remember close de FileOutput
            fo.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return path1;
    }

    public int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {

            Log.e("filepath", "*******" + filepath);
            exif = new ExifInterface(filepath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognise a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

            }
        }

        return degree;
    }

    public void updateProfile() {


        JSONObject object = new JSONObject();


        try {
            if (bitmap != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                try {
                    object.put("ProfilePic", Base64.encodeToString(imageBytes, Base64.DEFAULT));
                    object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext(), new PrefUtils().SimNumber, ""));
                    object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext(), new PrefUtils().UDID, ""));
                    object.put("LoyltyId", new PrefUtils().getFromPrefs(getApplicationContext(), new PrefUtils().LoyaltyId, ""));
                    object.put("Name", my_id_ed_name.getText().toString());
                    object.put("MobileNumber", my_id_ed_number.getText().toString());
                    object.put("EmailId", my_id_ed_email_id.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new PostData(0, object.toString(), "SetSubscriberProfile", MyProfile.this).execute();
            } else {

                Log.d("Bitmap is null", "Filling value");
                signup_iv_profile.isDrawingCacheEnabled();
                signup_iv_profile.buildDrawingCache();
                bitmap = signup_iv_profile.getDrawingCache();

                if (bitmap != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    try {
                        object.put("ProfilePic", Base64.encodeToString(imageBytes, Base64.DEFAULT));

                        object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext(), new PrefUtils().SimNumber, ""));
                        object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext(), new PrefUtils().UDID, ""));
                        object.put("LoyltyId", new PrefUtils().getFromPrefs(getApplicationContext(), new PrefUtils().LoyaltyId, ""));
                        object.put("Name", my_id_ed_name.getText().toString());
                        object.put("MobileNumber", my_id_ed_number.getText().toString());
                        object.put("EmailId", my_id_ed_email_id.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    new PostData(0, object.toString(), "SetSubscriberProfile", MyProfile.this).execute();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onRequestSuccess(int method, String response) {

        if(method==11){
           final JSONObject object = new JSONObject();

            try {

                object.put("LoyltyID", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.LoyaltyId, ""));

                object.put("SimNumber", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.SimNumber, ""));


                object.put("UDID", new PrefUtils().getFromPrefs(getApplicationContext(), PrefUtils.UDID, ""));
                MyProfile.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new PostData(123, object.toString(), "GetSubscriberProfile ", MyProfile.this).execute();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if(method==123){
            JSONObject object = new JSONObject();

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



            } catch (Exception e) {
                e.printStackTrace();
            }

            MyProfile.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Intent i=new Intent(MyProfile.this,MyProfile.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(0,0);
                }
            });
        }else{
        MyProfile.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new Toast(getApplicationContext(), "Your profile has been updated.").show();
                getUserProfile();

            }
        });}


    }

    @Override
    public void onRequestFail(int method, String message) {
        MyProfile.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Toast(getApplicationContext(), "Please check your N/w Connection").show();

            }
        });

    }

    @Override
    public void onStatus404() {
        MyProfile.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Toast(getApplicationContext(), "You are a invalid user").show();
                new PrefUtils().saveToPrefs(getApplicationContext(), "IsLoggedIn", "0");
                Intent i = new Intent(MyProfile.this, Login.class);
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

            new PostData(11, object.toString(), "GetSubscriberProfile ", MyProfile.this).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setUpFooter() {
        LinearLayout ll_myProfile = (LinearLayout) findViewById(R.id.ll_myid);
        ll_myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_ll_myProfile = new Intent(MyProfile.this, MyId.class);
                startActivity(i_ll_myProfile);
                finish();
            }
        });

        LinearLayout ll_mail = (LinearLayout) findViewById(R.id.ll_mail);
        ll_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_ll_myProfile = new Intent(MyProfile.this, MailBox.class);
                startActivity(i_ll_myProfile);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i_ll_myProfile = new Intent(MyProfile.this, MainActivity.class);
        startActivity(i_ll_myProfile);
        finish();
    }
}