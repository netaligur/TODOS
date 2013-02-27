/*
 * @TaskNOID      1.0 2013/02/27	
 *
 * Copyright 2013 Netali & Nadav, Inc. Neatli Gur & Nadav Taoz All Rights Reserved.
 * 
 * This software is the proprietary information of Netali and Nadav- Shenkar College of Engineering and Design
 */

package com.example.todos2;

import com.google.analytics.tracking.android.EasyTracker;
import java.util.ArrayList;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * this class is the first activity,
 * the opening activity that fires when
 * first running the application.
 * it's managing the first boot steps of
 * the DB,list view and important variables.
 * connecting between the activiy_task_noid
 * xml with the list view with it's adapter.
 */

public class TaskNOID extends Activity implements View.OnClickListener
{   
	public static final String		ONE_TIME = ""												;
	private ListController 			list_details												;	// The single instance of the list
	public final static String	    EXTRA_MESSAGE = "com.example.todos2.CreateTaskActivity"		;	
	
/**
* For the Google Analytics 
*/	
	
	@Override
	public void onStart() 
	{
		super.onStart();
		EasyTracker.getInstance().activityStart(this);  
	}
/**
* Initializing all important managers and variables, connection to the xml. 
*/		
	public void onCreate(Bundle savedInstanceState)
    {	
		list_details=ListController.getInstance(this);											// Initializing the list
		list_details.boot();																	// Initializing tasks from the DB
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);				// Calling Alarm Manager
		LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		list_details.setAlarmManager(am);														// Connects the list to the alarm manager
		list_details.setLocationManager(lm);													// Connects the list to the location manager
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_noid);											// Initializing the main activity xml
        ListView lv1 = (ListView) findViewById(R.id.list);										// Initializing the listview xml
        lv1.setAdapter(new ItemListBaseAdapter(this,  list_details));							// Setting the listview with the adapter
        lv1.setItemsCanFocus(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_task_noid, menu);
        return true;
    }
    public void addTask(View view) {
    	/*Start the create task activity in response to the add button */
    	Intent intent = new Intent(this, CreateTaskActivity.class);
    	startActivity(intent);
    }
    
	public void onClick(View arg0) {}
	
	public void onConfigurationChanged(Configuration newConfig) 
    {
        super.onConfigurationChanged(newConfig);

    }
/**
* For the Google Analytics 
*/		
	@Override
	public void onStop()
	{
		super.onStop();
		EasyTracker.getInstance().activityStop(this);  
	}
}
	

	


