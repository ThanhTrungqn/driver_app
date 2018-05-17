package com.example.tnguyen.myapp_driver.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by tnguyen on 5/15/2018.
 */

public class NetworkAsyncTask extends AsyncTask<String, String , String> {
    String response="";
    ProgressDialog dialog;

    public NetworkAsyncTask(Activity activity) {
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected String doInBackground(String... params) {
        String data=params[0];
        Log.e ("data", String.valueOf(data));
        String text = "";
        BufferedReader reader=null;
        try {
            URL url = new URL("http://5.196.12.31/testphp.php");
            //URL url = new URL("https://api.androidhive.info/json/movies.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStreamWriter os1 =new OutputStreamWriter(conn.getOutputStream());
            os1.write(data);
            os1.flush();
            os1.close();

            int responseCode = conn.getResponseCode();
            conn.connect();
            Log.e ("response code", String.valueOf(responseCode));
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                Log.e("here6", "here6");
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    @Override
    protected void onPreExecute() {
        dialog.setMessage("Doing something, please wait.");
        dialog.show();
    }

    protected void onPostExecute(String success) {
        Log.e("onPostExecute: ", success );
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
