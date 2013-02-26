package com.example.todos2;

import com.google.analytics.tracking.android.EasyTracker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


public class TaskNOID extends Activity implements View.OnClickListener
{   
	private Context context;
	public static final String ONE_TIME = "";
	private Intent alarm;
	private ListController list_details;
	public final static String EXTRA_MESSAGE = "com.example.todos2.CreateTaskActivity";
	@Override
	 public void onStart() 
	  {
		super.onStart();
		EasyTracker.getInstance().activityStart(this);  
	  }
	public void onCreate(Bundle savedInstanceState)
    {
	
		list_details=ListController.getInstance(this);
		list_details.boot();
		 AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		 list_details.setAlarmManager(am);
		//list_details.createTable();										// building the data base table
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_noid);
        Intent intent = getIntent();
        ArrayList<String>message = intent.getStringArrayListExtra(CreateTaskActivity.EXTRA_MESSAGE);
        context=this;
        //final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        ListView lv1 = (ListView) findViewById(R.id.list);
        lv1.setAdapter(new ItemListBaseAdapter(this,  list_details));
        lv1.setItemsCanFocus(false);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
        
        public void onItemClick(AdapterView<?> parent, View view, int position,long id)
		{				
			System.out.println("netali");

                
                String item ;//((TextView)view).getText().toString();
                
                Toast.makeText(getBaseContext(), "good", Toast.LENGTH_LONG).show();
		}});
	
         // SharedPreferences preferences = getSharedPreferences(TaskNOID.ONE_TIME, 0);
         //preferences.edit().remove("IsServiceRun").commit();
        
        SharedPreferences settings = getSharedPreferences(TaskNOID.ONE_TIME, 0);
        
     // ***********Acquire a reference to the system Location Manager
       
/*
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() 
        {
            public void onLocationChanged(Location location) 
            {
              // Called when a new location is found by the network location provider.
              //makeUseOfNewLocation(location);
            	String locationProvider = LocationManager.NETWORK_PROVIDER;
            	// Or use LocationManager.GPS_PROVIDER

            	Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            	//Toast.makeText(getBaseContext(), lastKnownLocation.toString(), Toast.LENGTH_LONG).show();
            
            }

            public void onStatusChanged(String provider, int status, Bundle extras)
            {
            	
            }

            public void onProviderEnabled(String provider)
            {
            	 Toast.makeText(getBaseContext(), "Location Service Is Up", Toast.LENGTH_SHORT).show();
            }

            public void onProviderDisabled(String provider) 
            {
            	 Toast.makeText(getBaseContext(), "Location Service Is Off ", Toast.LENGTH_SHORT).show();
            }
          };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        /*****/
      /*  boolean Servic_Up = settings.getBoolean("IsServiceRun", false);
        if(Servic_Up==false)
        {
         SetAlarmService();
        }
     */
   /*    if (message!=null)
       {
    	   ItemDetails temp= new ItemDetails();
    	   temp.setName(message.get(0));
    	   temp.setTopic(message.get(1));
    	   //temp.setDone(0);
    	   
    	   temp.setYear(Integer.valueOf(message.get(2)));
    	   temp.setMonth(Integer.valueOf(message.get(3)));
    	   temp.setDay(Integer.valueOf(message.get(4)));
    	   temp.setHour(Integer.valueOf(message.get(5)));
    	   temp.setMinute(Integer.valueOf(message.get(6)));
    	   list_details.addOrgan(temp);
    	   System.out.println(temp.toString());*/
     /*  }*/
    

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_task_noid, menu);
        return true;
    }
    public void addTask(View view) {
    	  // Do something in response to button
    	Intent intent = new Intent(this, CreateTaskActivity.class);
    	startActivity(intent);
    }

  /*
	private void SetAlarmService()
	{
        alarm = new Intent(this,ServiceFromBroadcast.class);
        PendingIntent pendingIntentService = PendingIntent.getBroadcast(this,12340,alarm, 0);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,24);
        cal.set(Calendar.MINUTE,00);
        cal.set(Calendar.SECOND, 0);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntentService);  
        SharedPreferences settings = getSharedPreferences(TaskNOID.ONE_TIME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("IsServiceRun",true);
        editor.commit();
        Toast.makeText(this, "service is up ", Toast.LENGTH_SHORT).show();
	}
			
	*/

	public void onClick(View arg0) {
		// TODO Auto-generated method stub

        String item ;//((TextView)view).getText().toString();
        
        Toast.makeText(getBaseContext(), "good", Toast.LENGTH_LONG).show();
	}
	public void onConfigurationChanged(Configuration newConfig) 
    {
        super.onConfigurationChanged(newConfig);

    }
	@Override
	 public void onStop()
	 {
		    super.onStop();
		    EasyTracker.getInstance().activityStop(this);  
	}
}
	

	


