package com.example.tnguyen.myapp_driver;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.ExecutionException;

/**
 * Created by tnguyen on 5/16/2018.
 */

public class ShowResult extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_fragment_login);

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
        //connect
        //show progresdialog
        try {
            String responsepost = new NetworkAsyncTask(this).execute(newString).get();
            Log.e( "onCreate: ", responsepost );
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

