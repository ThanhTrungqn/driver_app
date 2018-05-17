package com.example.tnguyen.myapp_driver.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

import javax.security.auth.login.LoginException;

/**
 * Created by tnguyen on 5/17/2018.
 */

public class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    public DownloadImageAsyncTask(ImageView bmImage) {
        Log.e("check","start");
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap bmp = null;
        try {
            InputStream in = new URL(url).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bmp;
    }
    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}