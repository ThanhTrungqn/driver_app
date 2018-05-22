package com.example.tnguyen.myapp_driver.tablayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.tnguyen.myapp_driver.R;
import com.example.tnguyen.myapp_driver.search_activity.ShowResultActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by tnguyen on 5/16/2018.
 */

public class TabFragmentSearch extends Fragment {
    private static int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static int RESULT_OK = 1;
    private static int RESULT_CANCELED = 0;
    Calendar dateTime = Calendar.getInstance();
    private Button btn_search;


    private int sDate,sMonth,sYear,sHour,sMinute;
    private TextView text_date_go,text_time_go;
    private EditText text_where_from,text_where_to;
    private GridLayout layout_date_go,layout_time_go;
    private LinearLayout layout_where_from,layout_where_to;
    private String currentCity;
    private String responsepost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_search, container, false);
        //khai bien
        btn_search = view.findViewById(R.id.search_button);

        //declare layout clickable
        layout_where_from = view.findViewById(R.id.search_city_from_layout);
        layout_where_to = view.findViewById(R.id.search_city_to_layout);
        layout_date_go = view.findViewById(R.id.search_date_layout);
        layout_time_go = view.findViewById(R.id.search_time_layout);

        text_where_from = view.findViewById(R.id.search_city_from_text);
        text_where_to = view.findViewById(R.id.search_city_to_text);
        text_date_go = view.findViewById(R.id.search_date_text);
        text_time_go = view.findViewById(R.id.search_time_text);

        //update chọn thành phố
        text_where_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaceAutocomplet();
            }
        });

        text_where_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaceAutocomplet();
            }
        });

        //Date picker
        layout_date_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDateGo();
            }
        });
        //Time picker
        layout_time_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTimeGo();
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
                //currentCity= getSelectedItem().toString();
                 //   data_post += URLEncoder.encode("city", "UTF-8")
                 //           + "=" + URLEncoder.encode(currentCity, "UTF-8");
                //} catch (UnsupportedEncodingException e) {
                //    e.printStackTrace();
                //}

                //goto new layer => show the resultat search
                Intent intent = new Intent(getActivity(),ShowResultActivity.class);
                intent.putExtra("STRING_I_NEED",data_post );
                getActivity().startActivity(intent);
                //goto new layer => show the resultat search
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            else
            {
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                double MyLat = place.getLatLng().latitude;
                double MyLong = place.getLatLng().longitude;
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
                    String stateName = addresses.get(0).getAdminArea();
                    text_where_from.setText(stateName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void updateDateGo() {
        //show dialog
        DatePickerDialog dateshow = new DatePickerDialog(this.getActivity(),
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, Sdate,
                dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH));
        //set minDate
        dateshow.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dateshow.show();
    }

    DatePickerDialog.OnDateSetListener Sdate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int date) {
            //listen the result
            sYear=year;
            sMonth=month;
            sDate=date;
            //show Time picker
            text_date_go.setText(sDate+"/"+sMonth+"/"+sYear);
            Log.i("show time1","show time1");
        }
    };

    private void updateTimeGo() {
        //showDialog
        new TimePickerDialog(this.getActivity(),
                Stime, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true).show();
    }

    TimePickerDialog.OnTimeSetListener Stime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            //dosomething
            sMinute=minute;
            sHour=hour;
            text_time_go.setText(sHour+":"+sMinute);
        }
    };

    private void PlaceAutocomplet() {
        Intent intent = null;
        try {
            intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
}
