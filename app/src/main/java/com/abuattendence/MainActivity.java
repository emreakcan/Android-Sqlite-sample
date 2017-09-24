package com.abuattendence;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;

    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<HashMap<String, String>> kitap_liste;
    String kitap_adlari[];
    int kitap_idler[];

    private EditText   studentname;
    private Button  add, addstudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        addstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddEvent.class);
                startActivity(i);
            }
        });

    }

    public void onResume() {   //neden onResume metodu kulland���m� ders i�inde anlatt�m.
        super.onResume();
        eventList();
    }

    private void toList() {
        Database db = new Database(getApplicationContext()); // Db ba�lant�s� olu�turuyoruz. �lk seferde database olu�turulur.
        kitap_liste = db.students();//kitap listesini al�yoruz
        if (kitap_liste.size() == 0) {//kitap listesi bo�sa
            Toast.makeText(getApplicationContext(), "Student not found", Toast.LENGTH_LONG).show();
        } else {
            kitap_adlari = new String[kitap_liste.size()]; // kitap adlar�n� tutucam�z string arrayi olusturduk.
            kitap_idler = new int[kitap_liste.size()]; // kitap id lerini tutucam�z string arrayi olusturduk.
            for (int i = 0; i < kitap_liste.size(); i++) {
                kitap_adlari[i] = kitap_liste.get(i).get("kitap_adi");
                kitap_idler[i] = Integer.parseInt(kitap_liste.get(i).get("id"));
            }
            lv = (ListView) findViewById(R.id.studentlist);

            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.kitap_adi, kitap_adlari);
            lv.setAdapter(adapter);
        }
    }

    private void eventList() {
        DatabaseEvents db = new DatabaseEvents(getApplicationContext());
        kitap_liste = db.test();
        if (kitap_liste.size() == 0) {
            Toast.makeText(getApplicationContext(), "EVENT NOT FOUND", Toast.LENGTH_LONG).show();
        } else {
            kitap_adlari = new String[kitap_liste.size()];
            kitap_idler = new int[kitap_liste.size()];
            for (int i = 0; i < kitap_liste.size(); i++) {
                kitap_adlari[i] = kitap_liste.get(i).get("kitap_adi");
                kitap_idler[i] = Integer.parseInt(kitap_liste.get(i).get("id"));
            }

            lv = (ListView) findViewById(R.id.studentlist);

            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.kitap_adi, kitap_adlari);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {

                    Intent i = new Intent(MainActivity.this, Attendance.class);
                    i.putExtra("eventname",lv.getItemAtPosition(arg2).toString());
                    startActivity(i);

                }
            });
        }
    }

    public void init() {


        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//preferences objesi
        editor = preferences.edit();

        add = (Button) findViewById(R.id.add);

        addstudent = (Button) findViewById(R.id.addstudent);
        studentname = (EditText) findViewById(R.id.studentname);

    }


    private void addStudent() {
        Database db = new Database(getApplicationContext());
        db.addStudent(studentname.getText().toString());//kitap ekledik
        db.close();
        Toast.makeText(getApplicationContext(), "STUDENT ADDED", Toast.LENGTH_LONG).show();

    }
}
