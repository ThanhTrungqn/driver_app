package com.example.tnguyen.myapp_driver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tnguyen on 5/16/2018.
 */

public class TabFragmentLogin extends Fragment {

    private static final String TAG = "SignupActivity";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_login, container, false);


        return view;

    }
}