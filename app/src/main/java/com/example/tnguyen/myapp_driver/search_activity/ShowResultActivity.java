package com.example.tnguyen.myapp_driver.search_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tnguyen.myapp_driver.InformationTripActivity;
import com.example.tnguyen.myapp_driver.R;
import com.example.tnguyen.myapp_driver.task.NetworkAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by tnguyen on 5/16/2018.
 */

public class ShowResultActivity extends AppCompatActivity {

    static final String URL = "https://api.androidhive.info/music/music.xml";
    // XML node keys
    static final String KEY_LISTCAR = "listcar"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_THUMB_URL = "image";
    static final String KEY_PRICE = "price";

    ListView list;
    LazyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_search_result);
        Log.e("why","go here");

        ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("STRING_I_NEED");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }
        try {
            String responsepost = new NetworkAsyncTask(this).execute(newString).get();
            JSONObject obj = new JSONObject(responsepost);
            JSONArray list_movie =  obj.getJSONArray(KEY_LISTCAR);
            Log.e("onCreate: ", String.valueOf(obj.length()));
            for (int i=0 ; i< list_movie.length(); i++ )
            {
                JSONObject movie = list_movie.getJSONObject(i);
                HashMap<String , String> map = new HashMap<String , String>() ;
                map.put(KEY_NAME, movie.getString(KEY_NAME));
                map.put(KEY_THUMB_URL, movie.getString(KEY_THUMB_URL));
                map.put(KEY_PRICE, movie.getString(KEY_PRICE));
                songsList.add(map);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        list=(ListView)findViewById(R.id.list);

        // Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(this, songsList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Get the selected item text from ListView
                //String selectedItem = (String) adapterView.getItemAtPosition(i);
                Log.e("click", String.valueOf(i));
                // Display the selected item text on TextView
                Intent intent = new Intent(getApplicationContext(),InformationTripActivity.class);
                intent.putExtra("name","texst");
                startActivity(intent);
            }
        });

    }
}

