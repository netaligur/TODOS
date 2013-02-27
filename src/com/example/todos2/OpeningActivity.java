/*
 * @OpeningActivity        1.0 2013/02/27	
 *
 * Copyright 2013 Netali & Nadav, Inc. Neatli Gur & Nadav Taoz All Rights Reserved.
 * 
 * This software is the proprietary information of Netali and Nadav- Shenkar College of Engineering and Design
 */
package com.example.todos2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.ViewGroup;

/**
 * this class holds the main application
 * it's makes connection between the GUI and
 * the XML by summoning their constructor
 */

public class OpeningActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //LayoutInflater inflater = (LayoutInflater)getSystemService(this.LAYOUT_INFLATER_SERVICE);
        //ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.activity_opening, null);
       // setContentView(vg);
        //setContentView(R.layout.activity_opening);
       
        Intent intent = new Intent(this, TaskNOID.class);
      
       
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_opening, menu);
        return true;
    }
}

