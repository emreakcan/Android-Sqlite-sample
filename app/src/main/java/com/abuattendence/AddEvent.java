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

public class AddEvent extends AppCompatActivity {
    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<HashMap<String, String>> kitap_liste;
    String kitap_adlari[];
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    int kitap_idler[];
    private Button save;
    private EditText eventname;
    private String attendance = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);


        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//preferences objesi
        editor = preferences.edit();

        eventname = (EditText) findViewById(R.id.eventname);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseEvents db = new DatabaseEvents(getApplicationContext());
                db.test1(eventname.getText().toString());//kitap ekledik
                db.close();
                Toast.makeText(getApplicationContext(), "EVENT ADDED", Toast.LENGTH_LONG).show();
                Intent i = new Intent(AddEvent.this, MainActivity.class);
                startActivity(i);

                editor.putString(eventname.getText().toString(),attendance);
                editor.commit();
            }
        });

    }

    public void onResume() {
        super.onResume();
        Database db = new Database(getApplicationContext());
        kitap_liste = db.students();
        if (kitap_liste.size() == 0) {
            Toast.makeText(getApplicationContext(), "Hen�z Kitap Eklenmemi�.\nYukar�daki + Butonundan Ekleyiniz", Toast.LENGTH_LONG).show();
        } else {
            kitap_adlari = new String[kitap_liste.size()]; // kitap adlar�n� tutucam�z string arrayi olusturduk.
            kitap_idler = new int[kitap_liste.size()]; // kitap id lerini tutucam�z string arrayi olusturduk.
            for (int i = 0; i < kitap_liste.size(); i++) {
                kitap_adlari[i] = kitap_liste.get(i).get("kitap_adi");

                kitap_idler[i] = Integer.parseInt(kitap_liste.get(i).get("id"));
            }
            lv = (ListView) findViewById(R.id.studentList);

            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.kitap_adi, kitap_adlari);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    lv.getChildAt(arg2).setBackgroundColor(Color.GREEN);
                    attendance = lv.getItemAtPosition(arg2).toString() + ";" + attendance;

                }
            });
        }
    }
}
