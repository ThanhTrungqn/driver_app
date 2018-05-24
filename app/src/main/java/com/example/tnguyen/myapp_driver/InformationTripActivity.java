package com.example.tnguyen.myapp_driver;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.baoyachi.stepview.VerticalStepView;
import com.example.tnguyen.myapp_driver.class_lib.NonScrollListView;

import org.qap.ctimelineview.TimelineRow;
import org.qap.ctimelineview.TimelineViewAdapter;

import java.util.ArrayList;

/**
 * Created by tnguyen on 5/24/2018.
 */

public class InformationTripActivity extends AppCompatActivity {

    VerticalStepView verticalStepView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_trip);

        // Create Timeline rows List
        ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();

        for (int i =0; i<3; i++)
        {
            TimelineRow myRow = new TimelineRow(i);
            myRow.setTitle("10:10");
            myRow.setDescription("Đà Nẵng");
            myRow.setBellowLineColor(Color.argb(255, 0, 0, 0));
            // To set row Below Line Size in dp (optional)
            myRow.setBellowLineSize(2);
            myRow.setImageSize(10);
            // To set background color of the row image (optional)
            myRow.setBackgroundColor(Color.argb(170, 0, 0, 255));
            // To set the Background Size of the row image in dp (optional)
            myRow.setBackgroundSize(15);
            // To set row Date text color (optional)
            myRow.setDateColor(Color.argb(255, 0, 0, 0));
            // To set row Title text color (optional)
            myRow.setTitleColor(Color.argb(255, 0, 0, 0));
            // To set row Description text color (optional)
            myRow.setDescriptionColor(Color.argb(255, 0, 0, 0));

            // Add the new row to the list
            timelineRowsList.add(myRow);
        }


        // Create the Timeline Adapter
        ArrayAdapter<TimelineRow> myAdapter = new TimelineViewAdapter(this, 0, timelineRowsList,
                //if true, list will be sorted by date
                false);


        // Get the ListView and Bind it with the Timeline Adapter

        NonScrollListView myListView = (NonScrollListView) findViewById(R.id.timeline_listView);
        myListView.setEnabled(false);
        myListView.setAdapter(myAdapter);
    }
}
