package com.example.tnguyen.myapp_driver.search_activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tnguyen.myapp_driver.R;
import com.example.tnguyen.myapp_driver.task.DownloadImageAsyncTask;

/**
 * Created by tnguyen on 5/17/2018.
 */

public class LazyAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;

    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView name_driver = (TextView)vi.findViewById(R.id.name_driver); // name
        TextView price = (TextView)vi.findViewById(R.id.price); // artist price
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        // Setting all values in listview
        name_driver.setText(song.get(ShowResultActivity.KEY_NAME));
        price.setText(song.get(ShowResultActivity.KEY_PRICE));
        Log.e("abc","goo herreeeeeeeeeeeeee");
        new DownloadImageAsyncTask(thumb_image).execute(song.get(ShowResultActivity.KEY_THUMB_URL));
        //imageLoader.DisplayImage(song.get(ShowResultActivity.KEY_PRICE), thumb_image);
        return vi;
    }
}