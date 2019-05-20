package com.example.joserosado.goaltrackerassignment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WeekListActivity extends AppCompatActivity {

    ArrayList <String> weekNameList;
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.week_log);
        ListView weekList = (ListView)findViewById(R.id.weekListView);
        weekNameList = new ArrayList<String>();
        getWeekDays();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,weekNameList);
        weekList.setAdapter(arrayAdapter);

    }


    void getWeekDays(){
        weekNameList.add("Sunday");
        weekNameList.add("Monday");
        weekNameList.add("Tuesday");
        weekNameList.add("Wednesday");
        weekNameList.add("Thursday");
        weekNameList.add("Friday");
        weekNameList.add("Saturday");

    }


}
