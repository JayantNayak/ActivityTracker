package com.hobby.jayant.activitytracker.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RelativeLayout;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by I329687 on 8/17/2017.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
        int ROTATE_ANIMATION_DURATION = 800;

        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(ROTATE_ANIMATION_DURATION);
        rotate.setRepeatCount(Animation.INFINITE);
        bmImage.startAnimation(rotate);
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        URL myfileurl =null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);

           /* myfileurl= new URL(urldisplay);
            HttpURLConnection conn= (HttpURLConnection)myfileurl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            int length = conn.getContentLength();
            int[] bitmapData =new int[length];
            byte[] bitmapData2 =new byte[length];
            InputStream is = conn.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();

            mIcon11 = BitmapFactory.decodeStream(is,null,options);
            */

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.clearAnimation();
        // set layout height and width to match parent
        bmImage.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        bmImage.setImageBitmap(result);
    }
}
