package com.abuattendence;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Attendance extends AppCompatActivity {

    TextView eventnametext;
    ListView listview;

    ArrayAdapter<String> adapter;

    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Intent intent = getIntent();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//preferences objesi
        editor = preferences.edit();


        eventnametext = (TextView)findViewById(R.id.eventname);
        listview = (ListView)findViewById(R.id.attendancelist);
        String eventname = intent.getStringExtra("eventname");
        eventnametext.setText("Attendance for" + eventname);

        String students = preferences.getString(eventname,"failure");

        String[] separated = students.split(";");



        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.kitap_adi, separated);
        listview.setAdapter(adapter);

    }
}
