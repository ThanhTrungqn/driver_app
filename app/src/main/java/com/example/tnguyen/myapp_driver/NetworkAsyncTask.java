package com.example.tnguyen.myapp_driver;

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

public class NetworkAsyncTask extends AsyncTask<String, Void, String> {
    String response="";
    @Override
    protected String doInBackground(String... params) {
        String data=params[0];
        Log.e ("data", String.valueOf(data));
        String text = "";
        BufferedReader reader=null;
        try {
            URL url = new URL("https://www.google.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStreamWriter os1 =new OutputStreamWriter(conn.getOutputStream());
            os1.write(data);
            os1.flush();
            os1.close();

            //Uri.Builder builder = new Uri.Builder()
             //       .appendQueryParameter("name", "quangngai")
             //       .appendQueryParameter("sDate", String.valueOf("15"));
            //    .appendQueryParameter("eDate", String.valueOf(eDate));
            //String query = builder.build().getEncodedQuery();

            //Log.e("msg", query);

            //OutputStream os = conn.getOutputStream();

            //BufferedWriter writer = new BufferedWriter(
            //        new OutputStreamWriter(os, "UTF-8"));
            //writer.write(query);
            //writer.flush();
            //Log.e("msg", writer.toString());
            //writer.close();
            //os.close();
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
}
