package com.example.tnguyen.myapp_driver;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import static android.support.v4.widget.SearchViewCompat.getQuery;

public class MainActivity extends AppCompatActivity {

    Calendar dateTime = Calendar.getInstance();
    private Button btn_search;
    private TextView textStart, textEnd;
    private Spinner staticSpinner;
    private int sDate,sMonth,sYear,sHour,sMinute;
    private int eDate,eMonth,eYear,eHour,eMinute;
    private String currentCity;
    private String responsepost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //khai bien
        btn_search = (Button) findViewById(R.id.main_search_button);
        textStart = (TextView) findViewById(R.id.textStartTime);
        textEnd = (TextView) findViewById(R.id.textEndTime);

        //update chọn thành phố
        staticSpinner = (Spinner) findViewById(R.id.textCity);
        ArrayAdapter<CharSequence> staticAdapter =ArrayAdapter.createFromResource(this,
                R.array.City, android.R.layout.simple_spinner_dropdown_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner.setAdapter(staticAdapter);
        //Date picker
        textStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDateStart();
            }
        });
        //Time picker
        textEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDateEnd();
            }
        });
        //Button search
        btn_search.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onClick(View view) {
                //todo: Verify if filled all champs => active button, if not show message toast+ do nothing
                //prepare data
                String data_post= "";
                try {
                    currentCity= staticSpinner.getSelectedItem().toString();
                    data_post += URLEncoder.encode("city", "UTF-8")
                            + "=" + URLEncoder.encode(currentCity, "UTF-8");

                    data_post += "&" + URLEncoder.encode("sDate", "UTF-8") + "="
                            + URLEncoder.encode( String.valueOf(sDate), "UTF-8");
                    data_post += "&" + URLEncoder.encode("sMonth", "UTF-8") + "="
                            + URLEncoder.encode( String.valueOf(sMonth), "UTF-8");
                    data_post += "&" + URLEncoder.encode("sYear", "UTF-8") + "="
                            + URLEncoder.encode( String.valueOf(sYear), "UTF-8");

                    data_post += "&" + URLEncoder.encode("eDate", "UTF-8") + "="
                            + URLEncoder.encode( String.valueOf(eDate), "UTF-8");
                    data_post += "&" + URLEncoder.encode("eMonth", "UTF-8") + "="
                            + URLEncoder.encode( String.valueOf(eMonth), "UTF-8");
                    data_post += "&" + URLEncoder.encode("eYear", "UTF-8") + "="
                            + URLEncoder.encode( String.valueOf(eYear), "UTF-8");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                try {
                    responsepost = new NetworkAsyncTask().execute(data_post).get();
                    Log.i("response get",responsepost);
                    //todo: goto new layer => show the resultat search
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateDateStart() {
        Log.i("show date1","show date1");
        //show dialog
        DatePickerDialog dateshow = new DatePickerDialog(this,
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, Sdate,
                dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH));
        Log.i("show date2","show date2");
        //set minDate
        dateshow.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        Log.i("show date3","show date3");
        dateshow.show();
        new TimePickerDialog(MainActivity.this,
                Stime, dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE), true ).show();
    }

    DatePickerDialog.OnDateSetListener Sdate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int date) {
            //listen the result
            sYear=year;
            sMonth=month;
            sDate=date;
            //show Time picker
            Log.i("show time1","show time1");
        }
    };

    TimePickerDialog.OnTimeSetListener Stime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            //dosomething
            sMinute=minute;
            sHour=hour;
            textStart.setText(sDate+"/"+sMonth+"/"+sYear + " Time "+sHour+":"+sMinute);
        }
    };

    private void updateDateEnd() {
        DatePickerDialog dateshow = new DatePickerDialog(this,
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, Edate,
                dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH));
        dateshow.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dateshow.show();
    }

    DatePickerDialog.OnDateSetListener Edate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int date) {
            //do something
            eYear=year;
            eMonth=month;
            eDate=date;
            new TimePickerDialog(MainActivity.this,
                    Etime, dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE), true ).show();
        }
    };

    TimePickerDialog.OnTimeSetListener Etime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            //dosomething
            eMinute=minute;
            eHour=hour;
            textEnd.setText(eDate+"/"+eMonth+"/"+eYear + "  "+eHour+":"+eMinute);
        }
    };
}
