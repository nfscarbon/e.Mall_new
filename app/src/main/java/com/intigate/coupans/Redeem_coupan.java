package com.intigate.coupans;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.intigate.emall.R;
import com.squareup.picasso.Picasso;

import java.util.EnumMap;
import java.util.Map;

import utils.Contents;
import utils.QRCodeEncoder;

/**
 * Created by krishank on 6/2/2015.
 */
public class Redeem_coupan extends Activity {
    ImageView iv_coupoan_img, iv_qrCode, iv_barCode;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    int[] color = {Color.WHITE, Color.parseColor("#c0c0c0")};
    float[] position = {0, 1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redeem_coupan);

        iv_coupoan_img = (ImageView) findViewById(R.id.imageView);
        iv_qrCode = (ImageView) findViewById(R.id.imageView1);
        iv_barCode = (ImageView) findViewById(R.id.imageView2);


        try {
            Picasso.with(getApplicationContext()).load(getIntent().getExtras().getString("url")).placeholder(R.drawable.icon_main).into(iv_coupoan_img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 2;
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(""+getIntent().getExtras().getString("id"),
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();

            iv_qrCode.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }

//Bar Code
        try {

            iv_barCode.setImageBitmap((encodeAsBitmap(getIntent().getExtras().getString("id"), BarcodeFormat.CODE_39, width - (width / 3),
                    width / 5)));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
