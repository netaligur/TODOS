/*
 * @CreateTaskActivity        1.0 2013/02/27	
 *
 * Copyright 2013 Netali & Nadav, Inc. Neatli Gur & Nadav Taoz All Rights Reserved.
 * 
 * This software is the proprietary information of Netali and Nadav- Shenkar College of Engineering and Design
 */

package com.example.todos2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import android.support.v4.app.FragmentActivity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import co.il.shenkar.tasknoid.R;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;

/**
 * This class is an activity which handles with adding a new
 * or when the user is editing a task from the main activity.
 * handles with tasks:topic,description,setting alarm on Location
 * or time. (using dialogs - pick date,time and after preesing
 * ok when entring a location opening a dialog also).
 */

public class CreateTaskActivity extends FragmentActivity  implements OnClickListener, OnCheckedChangeListener 
{
private Tracker 					myTracker								;	// Tracker from analytics
private GoogleAnalytics 			myGoogleAnal							;	// Managar google analytics	
private Button						editLocation							;	// Button "change" for edit location after set
private TextView 					timeLabel								;	// Represents the time the user picked
private TextView 					choosenLocation							;	// Represents the location the user picked
private CheckBox 					pickTime								;	// Checkbox for pick time
private Button 						create									;	// Button "save" to approve the task creation
private Context 					context									;	// Context
private Button 						okLocation								;	// Button "OK" to check if the location is valid
private EditText 					editText								;	// Description field
private EditText 					topicText								;	// Topic field
private EditText 					locationLabel							;	// Label "Location"
private CheckBox 					locationBox								;	// check box for enter Location or disable
private int 						hour									;	// Hour
private int 						minute									;	// Minute
private int 						year									;	// Year
private int 						month									;	// Month
private int 						day										;	// Day
private AlarmManager 				aManager								;	// Alarm manager
private boolean 					flagAlarm=false							;	// Flag,on when time alarm is set
private boolean 					fromEdit=false							;	// Flag,on when the activity was invoked from edit task
private boolean 					locationFlag=false						;	// Flag,on when location alarm is set
private String 						address									;	// The address
private Address 					addresschoosen							;	// Label of the address after it was chosen
private List<Address> 				listAddress								;	// The list singleton
private Geocoder 					geo										;	// Instance of the geo-coder
private LocationManager 			locationManager							;	// Location Manager
private Calendar 	   				myCalendar = Calendar.getInstance()		;	// Calandar - will receiver the time that was set
private Calendar 	  				rightnow   = Calendar.getInstance()		;	// Calandar - will receiver the time right now
public final static String 	   EXTRA_MESSAGE = "com.example.todos2.TaskNOID";
public final static String EXTRA_MESSAGE2 = "com.example.todos2.CreateTaskActivity";
	/* For the google analytics */
	public void onStart() 
	{
		super.onStart();
		EasyTracker.getInstance().activityStart(this);  
	}
    @Override
/**
* On Create - initializing all important variables, setting fields, checking if was invoked from
* edit task.
*/    
   public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);       
        setContentView(R.layout.activity_create_task);										// Sets the xml - create task activity
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);// Gets the Location manager
        context=this;																		// Setting the context
        geo=new Geocoder(this,Locale.getDefault());											// Gets the geoCoder
        /* initialize the elements in the XML - UI*/
        timeLabel=(TextView)findViewById(R.id.show_date1);
        timeLabel.setText("No Date Was Entered");
        timeLabel.setTextColor(Color.RED);
        locationLabel=(EditText)findViewById(R.id.edit_location);
        choosenLocation=(TextView)findViewById(R.id.show_choosen_location);
        choosenLocation.setText("No Location Was Entered");
        choosenLocation.setTextColor(Color.RED);
        pickTime= (CheckBox)findViewById(R.id.box_pick_time1);
        pickTime.setOnCheckedChangeListener(this);
        create= (Button)findViewById(R.id.button_create);
    	create.setOnClickListener(this);
    	okLocation= (Button)findViewById(R.id.okLocation);
    	okLocation.setOnClickListener(this);
    	editLocation= (Button)findViewById(R.id.editLocation);
    	editLocation.setOnClickListener(this);
    	locationBox = (CheckBox)findViewById(R.id.checkBoxLocation);
    	locationBox.setOnCheckedChangeListener(this);
    	aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    	editText=(EditText)findViewById(R.id.edit_message);
    	topicText=(EditText)findViewById(R.id.edit_topic);
    	Intent intent = getIntent();
    	ArrayList<String>message = intent.getStringArrayListExtra(CreateTaskActivity.EXTRA_MESSAGE2);
        myGoogleAnal = GoogleAnalytics.getInstance(this);
        /* Starts the tracker using the google singleton.*/
        myTracker = myGoogleAnal.getTracker(getString(R.string.google_analytic_id));	 		// Placeholder tracking ID from Strings XML 
    	/* For checking if the activity was invoked from edit task in the main */
    	if (message!=null)
        {
    		   /* Setting the fields with the details of the task */
    		   editText.setText(message.get(0));
    		   topicText.setText(message.get(1));      	   
    		   year=(Integer.valueOf(message.get(2)));
        	   month=(Integer.valueOf(message.get(3)));
        	   day=(Integer.valueOf(message.get(4)));
        	   hour=(Integer.valueOf(message.get(5)));
        	   minute=(Integer.valueOf(message.get(6)));
        	   /* Check if time alarm was set - setting the fields*/
        	   if (day!=0 && month!=0 && year!=0)
        	   { 
        		   		fromEdit=true; pickTime.setChecked(true);
        		   		timeLabel.setText( + day + "/" + month + "/"+ year + ",At  " + hour + ":" + minute);
    			        timeLabel.setTextColor(Color.BLUE);
        	   }
        	   /* Check if location alarm was set - setting the fields*/
        	   if(! (message.get(7).equals("No Location Was Entered")))
        	   {
	        	   choosenLocation.setText(message.get(7));
	        	   choosenLocation.setTextColor(Color.BLUE);
	        	   locationBox.setChecked(true);
	        	   locationLabel.setVisibility(4);
	        	   locationLabel.setText(message.get(7));
	        	   editLocation.setVisibility(0);
	        	   choosenLocation.setVisibility(0);
	        	   okLocation.setVisibility(4);
	           /* For correcting locations that starts with null */
        	   if(message.get(7).startsWith("null,")==true)
        	   {
        		   String temp = message.get(7).substring(5);
        		   choosenLocation.setText(temp);
        		   locationLabel.setText(temp);
        	   }
        	   /* Initializing a click on ok button to force the user to approve the location */
        	   okLocation.performClick();        	   
          } 
       }     	
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /* A function that checking if the time that selected is before the current time */
    public boolean testTime()
    {		
		 if(myCalendar.after(rightnow))
		 {
			 return true;
		 }
		 	 return false;
	}
    /*button clicks*/
	public void onClick(View v) 
	{
		/* Pressing on "OK" for location */
		if (v.getId()==R.id.okLocation)
		{
			myTracker.sendEvent("ui_action", "button_press", "add_location_pressed", null);
			address=locationLabel.getText().toString();						// Set the field
			try 
			{
				listAddress=geo.getFromLocationName(address,5);				
			} 
			/* Will return an error if the geo coder has error */
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				Toast.makeText(context,"Problem Had Occured,Check Your Intenet Connection",Toast.LENGTH_SHORT).show();
				e.printStackTrace();
				address="No Location Was Entered";
				return;
			}
			/* Will return an error if the geo coder hasn't found any results */
			if(listAddress.isEmpty())
            {
				Toast.makeText(context,"Sorry, Couldn't Find The Location",Toast.LENGTH_SHORT).show();
				address="No Location Was Entered";
            }
			/* Else - opening the dialog box for choosing the address from list - up to 5 results*/
        	else
        	{
        		chooseAddress(listAddress);
        	}
		}
		/* Change location button was pressed - setting the UI elements accordingly*/
		if (v.getId()==R.id.editLocation)
		{
			myTracker.sendEvent("ui_action", "button_press", "change_location_press", null);
			locationLabel.setVisibility(0);
			okLocation.setVisibility(0);  							// 0=Visible and 4 not 
			choosenLocation.setVisibility(4);
			editLocation.setVisibility(4);
			locationFlag=false;			
		}
		/* "Save" button was pressed - making the preparations for saving the task in the list*/
		if (v.getId()==R.id.button_create)
		{		
		myTracker.sendEvent("ui_action", "button_press", "save_task_pressed", null);	
		Intent intent = new Intent(this,TaskNOID.class);
		String result_text=editText.getText().toString();			// Getting the description from the field
		String topic_text=topicText.getText().toString();			// Getting the topic from the field
		/* Setting a string array with the details of the task */
		ArrayList <String>  temp= new ArrayList<String>();
		temp.add(0, result_text);
		temp.add(1, topic_text);
		temp.add(2,String.valueOf(year));
		temp.add(3,String.valueOf(month));
		temp.add(4,String.valueOf(day));
		temp.add(5,String.valueOf(hour));
		temp.add(6,String.valueOf(minute));		
		ItemDetails item= new ItemDetails();
   	    item.setName(result_text);
   	    item.setTopic(topic_text);
   	    item.setYear(year);
   	    item.setMonth(month);
   	    item.setDay(day);
   	    item.setHour(hour);
   	    item.setMinute(minute);
   	    item.setAddress(address);
		ListController list_details=ListController.getInstance(this);
		list_details.addOrgan(item);
		intent.putStringArrayListExtra(EXTRA_MESSAGE,temp);	
		/* Alarm is set condition */
		if (flagAlarm==true)
		{
			setTimeAlarm(item.getId());	
			flagAlarm=false;			
		}
		/* Location is set condition */
		if (locationFlag==true)
		{
			setLocationAlarm(item.getId());			
			locationFlag=false;
		}
		startActivity(intent);
		}
	}
	/* This function is resposible for setting the time alarm and only if the flag alarm is on */
	private void setTimeAlarm(int id) 
	{
		/* Initialize pending intent for the alarm */
		Intent intent = new Intent(this, ReminderBroadCastReceiver.class);
		String result_text=topicText.getText().toString();			  
		intent.putExtra(EXTRA_MESSAGE, result_text);	  
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent,PendingIntent.FLAG_ONE_SHOT);
		myCalendar.set(year,month-1,day,hour,minute);					// Setting the calendar with the time that was picked for testing
		/* Testing if the time is valid - if not , will not fire the notification */
		if(testTime()==true )
		{
			Calendar cal = Calendar.getInstance();
			/* Another validness checking , if the user presses cancel on the pickers dialog */
			if (!((year==0 && month==0 && day==0)||(year==0 && month==0 && day==0&&hour==0&& minute==0)))
			{
				cal.set(year, month-1, day, hour, minute);
				aManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(), pendingIntent);
				Toast toast = Toast.makeText(this, "will be remember", Toast.LENGTH_SHORT);
				toast.show();
			}
		}	
		/* Message for the user when the time is invalid */
		else
		{
			Toast toast = Toast.makeText(this, "Time for Alarm had Passed,no Alarm will be set", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	/* This function is dealing with setting the location alarm with the manager*/
	void setLocationAlarm(int id)
	{
		Intent intent = new Intent(this, ReminderBroadCastReceiver.class);
		String result_text=topicText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, result_text);		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_ONE_SHOT);
		addresschoosen.getLatitude();
		locationManager.addProximityAlert(addresschoosen.getLatitude(), addresschoosen.getLongitude(),500,-1,pendingIntent );
	}	
	/* For Checkboxes */
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) 
	{
		if((arg1)&&(arg0==pickTime))										// If picktime box is set
		{
			/* If the task was not from edit from main - will activate the pickers dialogs - one after the other */
			if (fromEdit==false)		
			{
				myTracker.sendEvent("ui_action", "check_box_press", "pick_time_checked", null);
				DatePickerFragment dPick= new DatePickerFragment();
				dPick.show(getSupportFragmentManager(), "Select the Date");	// Date picker dialog		
				TimePickerFragment tPick =new TimePickerFragment();
				tPick.show(getSupportFragmentManager(), "Select the Time");	// Time picker dialog					
			}
			/* Sets the flags */
			fromEdit=false;
			flagAlarm=true;
		}
		else if(arg0==pickTime)												// If picktime box is Unset - zerorize the variables
		{   
			flagAlarm=false;
			timeLabel.setText("no date entered");
		    timeLabel.setTextColor(Color.RED);
		    year=0;
		    month=0;
		    day=0;
		    hour=0;
		    minute=0;
		 }
		 if (arg0==locationBox && arg1==true)								// If location box is on - changes the UI accordingly
		 {
			myTracker.sendEvent("ui_action", "check_box_press", "add_location_preesed", null); 
			locationLabel.setVisibility(0);
			okLocation.setVisibility(0);  									// 0=Visible and 4 not 
			choosenLocation.setVisibility(4);
		}
		if (arg0==locationBox && arg1==false)								// If location box is off - changes the UI accordingly
		{
			locationLabel.setVisibility(4);
			okLocation.setVisibility(4);
			editLocation.setVisibility(4);
			choosenLocation.setVisibility(0);
			choosenLocation.setText("No Location Was Entered");
			address="No Location Was Entered";
			choosenLocation.setTextColor(Color.RED);
			locationFlag=false;
		}
	}
	/* Time picker dialog inner class */
	public  class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener,TimePickerDialog.OnCancelListener
	{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);		
			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
			DateFormat.is24HourFormat(getActivity()));
		}
		public void onTimeSet(TimePicker view, int hourOfDay, int minute1)
		{
			hour=hourOfDay;
			minute=minute1;
		}
	}
	/* Date picker dialog inner class */
	public  class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener,DatePickerDialog.OnCancelListener
	{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}			
		public void onDateSet(DatePicker view, int year1, int month1, int day1) 
		{
			year=year1;
			month=month1+1;
			day=day1;
			timeLabel.setText( + day + "/" + month + "/"+ year + ",At  " + hour + ":" + minute);
			timeLabel.setTextColor(Color.BLUE);
		}
	}
	/* This function is dealing with checking whether the address entered by the user is valid and opening a dialog box to the user */
	public void chooseAddress( final List<Address> listAddress)
	{						 
		final List<String> listItems = new ArrayList<String>();					// List of String to hold the address titels	
		/* Loop for all of the address results - translating them to String*/ 
		for (int i=0; i<listAddress.size(); i++)
		{
			Address address = listAddress.get(i);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(address.getAddressLine(1)).append(", ").append(address.getAddressLine(0)); 
			listItems.add(stringBuilder.toString());
		}		 
		CharSequence[] Items = listItems.toArray(new CharSequence[listItems.size()]);
		/* The Dialog box to show the results of the locations for the user to pick the disered location */
		AlertDialog IntervalAlarmChoice=new AlertDialog.Builder(this).setItems(Items, new DialogInterface.OnClickListener()
		{
			/* When chosing a location from the list */
			public void onClick(DialogInterface dialog, int position)
			{
				int place = position--;			 
				choosenLocation.setText(listItems.get(place));
				choosenLocation.setTextColor(Color.BLUE);
				choosenLocation.setVisibility(0);
				locationLabel.setVisibility(4);
				locationLabel.setText(listItems.get(place));
				address=listItems.get(place);
				okLocation.setVisibility(4);
				editLocation.setVisibility(0);
				locationFlag=true;			  
				addresschoosen= listAddress.get(place);
				dialog.dismiss();
			}
			})
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
			{
				/* When click on cancell */
				public void onClick(DialogInterface dialog, int id) 
				{ 
					address="No Location Was Entered";
					choosenLocation.setText("No Location Was Entered");
					locationLabel.setText("No Location Was Entered");
					locationBox.setChecked(false);
				}
			}).create();
			  IntervalAlarmChoice.show();   
		 }
	/* For google analytics */
	public void onStop()
	{
		super.onStop();
		EasyTracker.getInstance().activityStop(this);  
	}
}

	
