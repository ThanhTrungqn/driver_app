package com.example.tnguyen.myapp_driver.tablayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.tnguyen.myapp_driver.R;
import com.example.tnguyen.myapp_driver.search_activity.ShowResultActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

/**
 * Created by tnguyen on 5/16/2018.
 */

public class TabFragmentSearch extends Fragment {

    Calendar dateTime = Calendar.getInstance();
    private Button btn_search;
    private TextView textStart, textEnd;
    private Spinner staticSpinner;
    private int sDate,sMonth,sYear,sHour,sMinute;
    private int eDate,eMonth,eYear,eHour,eMinute;
    private String currentCity;
    private String responsepost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_search, container, false);
        //khai bien
        btn_search = view.findViewById(R.id.main_search_button);
        textStart = view.findViewById(R.id.textStartTime);
        textEnd = view.findViewById(R.id.textEndTime);

        //update chọn thành phố
        staticSpinner = view.findViewById(R.id.textCity);
        ArrayAdapter<CharSequence> staticAdapter =ArrayAdapter.createFromResource(this.getActivity(),
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


                //goto new layer => show the resultat search
                Intent intent = new Intent(getActivity(),ShowResultActivity.class);
                intent.putExtra("STRING_I_NEED",data_post );
                getActivity().startActivity(intent);
                //goto new layer => show the resultat search
            }
        });

        return view;
    }

    private void updateDateStart() {
        Log.i("show date1","show date1");
        //show dialog
        DatePickerDialog dateshow = new DatePickerDialog(this.getActivity(),
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, Sdate,
                dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH));
        Log.i("show date2","show date2");
        //set minDate
        dateshow.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        Log.i("show date3","show date3");
        dateshow.show();
        new TimePickerDialog(this.getActivity(),
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
        DatePickerDialog dateshow = new DatePickerDialog(this.getActivity(),
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, Edate,
                dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH));
        dateshow.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dateshow.show();
        new TimePickerDialog(this.getActivity(),
                Etime, dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE), true ).show();
    }

    DatePickerDialog.OnDateSetListener Edate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int date) {
            //do something
            eYear=year;
            eMonth=month;
            eDate=date;
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
