package com.example.tnguyen.myapp_driver;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tnguyen.myapp_driver.tablayout.TabFragmentLogin;
import com.example.tnguyen.myapp_driver.tablayout.TabFragmentSearch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView  = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.action_1:
                        //TabFragmentSearch tabSearch = new TabFragmentSearch();
                        selectedFragment = TabFragmentSearch.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conter, selectedFragment).commit();
                        Toast.makeText(MainActivity.this, "action 1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_2:
                        //TabFragmentLogin tabLogin = new TabFragmentLogin();
                        Toast.makeText(MainActivity.this, "action 1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_3:
                        Toast.makeText(MainActivity.this, "action 1", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}