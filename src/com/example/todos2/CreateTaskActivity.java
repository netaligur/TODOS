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
import com.google.analytics.tracking.android.EasyTracker;

/**
 * This class is an activity which handles with adding a new
 * or when the user is editing a task from the main activity.
 * handles with tasks:topic,description,setting alarm on Location
 * or time. (using dialogs - pick date,time and after preesing
 * ok when entring a location openining a dialog also).
 */

public class CreateTaskActivity extends FragmentActivity  implements OnClickListener, OnCheckedChangeListener 
{
private Button	editLocation;
private TextView timeLabel;
private TextView choosenLocation;
private CheckBox pickTime;
private Button create;
private Context context;
private Button okLocation;
private EditText editText;
private EditText topicText;
private EditText locationLabel;
//private CheckBox checkBox;
private CheckBox locationBox;
private int hour;
private int minute;
private int year;
private int month;
private int day;
private AlarmManager aManager;
private boolean flagAlarm=false;
private boolean fromEdit=false;
private boolean locationFlag=false;
//private boolean repeatFlag=false;
private String address;
private Address addresschoosen;
private List<Address> listAddress;
private Geocoder geo;
private LocationManager locationManager;
private Calendar 	   myCalendar = Calendar.getInstance();
private Calendar 	   rightnow   = Calendar.getInstance();



	 public final static String EXTRA_MESSAGE = "com.example.todos2.TaskNOID";
	 public final static String EXTRA_MESSAGE2 = "com.example.todos2.CreateTaskActivity";
	 public void onStart() 
	  {
		super.onStart();
		EasyTracker.getInstance().activityStart(this);  
	  }
    @Override
   public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_create_task);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        context=this;
        geo=new Geocoder(this,Locale.getDefault());
        timeLabel=(TextView)findViewById(R.id.show_date1);
        timeLabel.setText("No Date Was Entered");
        timeLabel.setTextColor(Color.RED);
        locationLabel=(EditText)findViewById(R.id.edit_location);
        //locationLabel.setText("Enter Location");
        //locationLabel.setTextColor(Color.RED);
        choosenLocation=(TextView)findViewById(R.id.show_choosen_location);
        choosenLocation.setText("No Location Was Entered");
        choosenLocation.setTextColor(Color.RED);
        //locationLabel.setAdapter(adapter);
        pickTime= (CheckBox)findViewById(R.id.box_pick_time1);
        pickTime.setOnCheckedChangeListener(this);
        create= (Button)findViewById(R.id.button_create);
    	create.setOnClickListener(this);
    	
    	
    	okLocation= (Button)findViewById(R.id.okLocation);
    	okLocation.setOnClickListener(this);
    	editLocation= (Button)findViewById(R.id.editLocation);
    	editLocation.setOnClickListener(this);
    	//timePicker=(TimePicker)findViewById (R.id.timePicker);
    	//datePicker = (DatePicker)findViewById(R.id.datePicker);
    	//checkBox = (CheckBox)findViewById(R.id.checkBox);
    	//checkBox.setOnCheckedChangeListener(this);
    	locationBox = (CheckBox)findViewById(R.id.checkBoxLocation);
    	locationBox.setOnCheckedChangeListener(this);
    	//timePicker.setIs24HourView(true);
    	aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    	editText=(EditText)findViewById(R.id.edit_message);
    	topicText=(EditText)findViewById(R.id.edit_topic);
    	//topicText.setPaintFlags(topicText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
       /* Typeface font = Typeface.createFromAsset(this.getAssets(), "Bleeding_Cowboys.ttf‬"); 
        create.setTypeface(font);
        random.setTypeface(font);*/
    	 Intent intent = getIntent();
    	  ArrayList<String>message = intent.getStringArrayListExtra(CreateTaskActivity.EXTRA_MESSAGE2);
    	   if (message!=null)
           {
        	   
    		   editText.setText(message.get(0));
    		   topicText.setText(message.get(1));      	   
    		   year=(Integer.valueOf(message.get(2)));
        	   month=(Integer.valueOf(message.get(3)));
        	   day=(Integer.valueOf(message.get(4)));
        	   hour=(Integer.valueOf(message.get(5)));
        	   minute=(Integer.valueOf(message.get(6)));
        	   if (day!=0 && month!=0 && year!=0)
        	   { 
        		   		fromEdit=true; pickTime.setChecked(true);
        		   		timeLabel.setText( + day + "/" + month + "/"+ year + ",At  " + hour + ":" + minute);
    			        timeLabel.setTextColor(Color.BLUE);
        		}
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
        	   /**nnnnnnaaadddaaavvv***/
        	   if(message.get(7).startsWith("null,")==true)
        	   {
        		   String temp = message.get(7).substring(5);
        		   choosenLocation.setText(temp);
        		   locationLabel.setText(temp);
        	   }
        	   okLocation.performClick();
        	   
          } 
           }  
    	
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean testTime()
    {		
		 if(myCalendar.after(rightnow))
		 {
			 return true;
		 }
		 	 return false;
	}
/*button chlicks*/
	public void onClick(View v) 
	{
		
		/*if (v.getId()==R.id.button_random)
		{
		
			URL url;
			try 
			{
				
				url = new URL("http://mobile1-tasks-dispatcher.herokuapp.com/task/random");
				new GetFromWebTask().execute(url);
				
			} 
				catch (MalformedURLException e)
				 {
					e.printStackTrace();
				}
			}*/
		if (v.getId()==R.id.okLocation)
		{
			address=locationLabel.getText().toString();
			try {
				listAddress=geo.getFromLocationName(address,5);
				
				
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				 Toast.makeText(context,"Problem Had Occured,Check Your Intenet Connection",Toast.LENGTH_SHORT).show();
					e.printStackTrace();
					address="No Location Was Entered";
					return;
			}
			 if(listAddress.isEmpty())
             {
				 Toast.makeText(context,"Sorry, Couldn't Find The Location",Toast.LENGTH_SHORT).show();
				 address="No Location Was Entered";
             }
        	 else{
        		 
        		 chooseAddress(listAddress);

        	 }
		
			
		}
		if (v.getId()==R.id.editLocation)
		{
			locationLabel.setVisibility(0);
			okLocation.setVisibility(0);  /* 0=visable and 4 not */
			choosenLocation.setVisibility(4);
			editLocation.setVisibility(4);
			//address=(String) locationLabel.getText();
			locationFlag=false;
			
		}
		if (v.getId()==R.id.button_create)
		{
		
		Intent intent = new Intent(this,TaskNOID.class);
		//editText=(EditText)findViewById(R.id.edit_message);
		String result_text=editText.getText().toString();
		//topicText=(EditText)findViewById(R.id.edit_topic);
		String topic_text=topicText.getText().toString();
		ArrayList <String>  temp= new ArrayList<String>();
	/* *****************check box in the future******(repeated tasks!!!!)*************************/	
		/*if(checkBox.isChecked())
		{
			hour = timePicker.getCurrentHour();
			minute=timePicker.getCurrentMinute();
			year = datePicker.getYear();
			month = datePicker.getMonth()+1;
			day = datePicker.getDayOfMonth();		
		
			setTimeAlarm();	
		}*/
		
		
		
		
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
   	   //temp.setDone(0);
   	   
   	   item.setYear(year);
   	  item.setMonth(month);
   	   item.setDay(day);
   	   item.setHour(hour);
   	   item.setMinute(minute);
   	   item.setAddress(address);
		ListController list_details=ListController.getInstance(this);
		list_details.addOrgan(item);
		intent.putStringArrayListExtra(EXTRA_MESSAGE,temp);	
		if (flagAlarm==true)
		{
			setTimeAlarm(item.getId());	
			flagAlarm=false;
			
		}
		if (locationFlag==true)
		{
			setLocationAlarm(item.getId());
			
			locationFlag=false;
		}
		startActivity(intent);
		}
	}
		private void setTimeAlarm(int id) 
		{
			  Intent intent = new Intent(this, ReminderBroadCastReceiver.class);
			  String result_text=topicText.getText().toString();
			  
			  intent.putExtra(EXTRA_MESSAGE, result_text);
			  
			  PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent,PendingIntent.FLAG_ONE_SHOT);
			  myCalendar.set(year,month-1,day,hour,minute);
			/*  if(repeatFlag==true)
			  {
				
			        Calendar cal = Calendar.getInstance();
			        cal.set(Calendar.HOUR_OF_DAY,hour);
			        cal.set(Calendar.MINUTE,minute);
			        cal.set(Calendar.SECOND, 0);
			        aManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(), 1000*30, pendingIntent);  
			        
			  }*/
			  //else
			 // {
				  if(testTime()==true )
				  {
						  Calendar cal = Calendar.getInstance();
						  if (!((year==0 && month==0 && day==0)||(year==0 && month==0 && day==0&&hour==0&& minute==0)))
						  {
						    cal.set(year, month-1, day, hour, minute);
						  	aManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(), pendingIntent);
						  	Toast toast = Toast.makeText(this, "will be remember", Toast.LENGTH_SHORT);
						  	toast.show();
						  }
				  }
				  else
				  {
					  	Toast toast = Toast.makeText(this, "Time for Alarm had Passed,no Alarm will be set", Toast.LENGTH_SHORT);
					  	toast.show();
				  }
			//  }

			
		}
		void setLocationAlarm(int id)
		{
			  Intent intent = new Intent(this, ReminderBroadCastReceiver.class);
			  String result_text=topicText.getText().toString();
			  intent.putExtra(EXTRA_MESSAGE, result_text);
			
			 PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_ONE_SHOT);
			 addresschoosen.getLatitude();
			locationManager.addProximityAlert(addresschoosen.getLatitude(), addresschoosen.getLongitude(),50,-1,pendingIntent );
		}
		/* *****************check box in the future (repeated tasks!!!!)*******************************/	
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			if((arg1)&&(arg0==pickTime))// (pickTime.isChecked())&&checkBox.isChecked()==false)
			{
				if (fromEdit==false)
				{
				DatePickerFragment dPick= new DatePickerFragment();
				dPick.show(getSupportFragmentManager(), "Select the Date");			
				TimePickerFragment tPick =new TimePickerFragment();
				tPick.show(getSupportFragmentManager(), "Select the Time");					
				}
				fromEdit=false;
				flagAlarm=true;
			}
			else if(arg0==pickTime)
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
			if (arg0==locationBox && arg1==true)
			{
				locationLabel.setVisibility(0);
				okLocation.setVisibility(0);  /* 0=visable and 4 not */
				choosenLocation.setVisibility(4);
				//address=(String) locationLabel.getText();
				
			}
			if (arg0==locationBox && arg1==false)
			{
				locationLabel.setVisibility(4);
				okLocation.setVisibility(4);
				editLocation.setVisibility(4);
				choosenLocation.setVisibility(0);
				choosenLocation.setText("No Location Was Entered");
				address="No Location Was Entered";
			    choosenLocation.setTextColor(Color.RED);
				//address=(String) locationLabel.getText();
			    locationFlag=false;
			}
			// if(arg0==checkBox && arg1==true) repeatFlag=true;
			// if(arg0==checkBox && arg1==false)repeatFlag=false;
				
			
		}
	
		/*private class GetFromWebTask extends AsyncTask<URL, Integer , String>
		{
			@Override
			protected String doInBackground(URL... urls) 
			{
				String result = getFromWeb(urls[0]);
				return result;
			}

			private String getFromWeb(URL url) 
			{
				
				try{
					HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

					InputStream in = new BufferedInputStream(urlConnection.getInputStream());
					
					InputStreamReader inReader = new InputStreamReader(in);
					
					BufferedReader bufferReader = new BufferedReader(inReader);
					
					String response = new String();
					
					for ( String line = bufferReader.readLine(); line != null; line = bufferReader.readLine() )
					{
						response+=line;
					}
					JSONObject jasonResponse = new JSONObject(response);
					
					
					return jasonResponse.getString("description");
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) 
			{
				editText.setText(result);
				topicText.setText(result);		
				
			}
		}*/
		public  class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener,TimePickerDialog.OnCancelListener
		{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
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
	
		public void chooseAddress( final List<Address> listAddress){
			
			 
			final List<String> listItems = new ArrayList<String>();
			 
			
			 for (int i=0; i<listAddress.size(); i++)
			 {
				    Address address = listAddress.get(i);
				    StringBuilder stringBuilder = new StringBuilder();
				    stringBuilder.append(address.getAddressLine(1)).append(", ").append(address.getAddressLine(0)); 
				    listItems.add(stringBuilder.toString());
				    	
				    	
				   
				 /*   else
				    {
				    	stringBuilder.append("").append(", ").append(address.getAddressLine(0));
					    listItems.add(stringBuilder.toString());
				    }*/
			 }
		 
			CharSequence[] Items = listItems.toArray(new CharSequence[listItems.size()]);
			AlertDialog IntervalAlarmChoice=new AlertDialog.Builder(this).setItems(Items, new DialogInterface.OnClickListener()
			{
			  public void onClick(DialogInterface dialog, int position) {
				
				  int place = position--;
			 
				 // setAlaramLocation(listAddress.get(c));
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
				   	public void onClick(DialogInterface dialog, int id) 
				   	{ 
				   		address="No Location Was Entered";
		        		choosenLocation.setText("No Location Was Entered");
		        		locationLabel.setText("No Location Was Entered");
		        		locationBox.setChecked(false);
				   	}
				}
			  )
		      .create();
			  IntervalAlarmChoice.show();   
			 }
		 public void onStop()
		 {
			    super.onStop();
			    EasyTracker.getInstance().activityStop(this);  
		}
	}

	
