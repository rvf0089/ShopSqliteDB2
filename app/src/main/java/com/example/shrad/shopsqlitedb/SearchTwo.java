package com.example.shrad.shopsqlitedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SearchTwo extends AppCompatActivity {

    private TextView nametv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_two);
        Intent i = getIntent();
        Information read = (Information) i.getSerializableExtra("information");
//        nametv = (TextView) findViewById(R.id.dispaly);
//        nametv.setText(String.format(read.getName()+" "+read.getAddresss()+" "+read.getPhonenumber()));
    }
}
