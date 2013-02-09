package com.example.todos2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateTaskActivity extends Activity  implements OnClickListener, OnCheckedChangeListener 
{
private TextView timeLabel;
private CheckBox pickTime;
private Button create;
private Button random;
private EditText editText;
private EditText topicText;
//private TimePicker timePicker;
//private DatePicker datePicker;
private CheckBox checkBox;
private int hour;
private int minute;
private int year;
private int month;
private int day;
private AlarmManager aManager;
private boolean flagAlarm=false;

	 public final static String EXTRA_MESSAGE = "com.example.todos2.TaskNOID";
    @Override
   public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        timeLabel=(TextView)findViewById(R.id.show_date1);
        timeLabel.setText("no date entered");
        timeLabel.setTextColor(Color.RED);
        pickTime= (CheckBox)findViewById(R.id.box_pick_time1);
        pickTime.setOnCheckedChangeListener(this);
        create= (Button)findViewById(R.id.button_create);
    	create.setOnClickListener(this);
    	random= (Button)findViewById(R.id.button_random);
    	random.setOnClickListener(this);
    	//timePicker=(TimePicker)findViewById (R.id.timePicker);
    	//datePicker = (DatePicker)findViewById(R.id.datePicker);
    	checkBox = (CheckBox)findViewById(R.id.checkBox);
    	checkBox.setOnCheckedChangeListener(this);
    	//timePicker.setIs24HourView(true);
    	aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    	editText=(EditText)findViewById(R.id.edit_message);
    	topicText=(EditText)findViewById(R.id.edit_topic);
    	//topicText.setPaintFlags(topicText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
       /* Typeface font = Typeface.createFromAsset(this.getAssets(), "Bleeding_Cowboys.ttf‬"); 
        create.setTypeface(font);
        random.setTypeface(font);*/
    	 Intent intent = getIntent();
    	  ArrayList<String>message = intent.getStringArrayListExtra(CreateTaskActivity.EXTRA_MESSAGE);
    	   if (message!=null)
           {
        	   ItemDetails temp= new ItemDetails();
        	   temp.setName(message.get(0));
        	   temp.setTopic(message.get(1));
        	   //temp.setDone(0);        	   
        	/*   temp.setYear(Integer.valueOf(message.get(2)));
        	   temp.setMonth(Integer.valueOf(message.get(3)));
        	   temp.setDay(Integer.valueOf(message.get(4)));
        	   temp.setHour(Integer.valueOf(message.get(5)));
        	   temp.setMinute(Integer.valueOf(message.get(6)));*/
        	   System.out.println(temp.toString());       	   
        	   editText.setText(temp.getName());
        	   topicText.setText(temp.getTopic());   
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


	public void onClick(View v) 
	{
		
		if (v.getId()==R.id.button_random)
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
			}
	/*	if (v.getId()==R.id.button_pick_time)
		{
			DatePickerFragment dPick= new DatePickerFragment();
			dPick.show(getFragmentManager(), "Select the Date");
			TimePickerFragment tPick =new TimePickerFragment();
			tPick.show(getFragmentManager(), "Select the Time");
			flagAlarm=true;
			
		}*/
			
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
		
		
		
		if (flagAlarm==true)
		{
			setTimeAlarm();	
			flagAlarm=false;
			
		}
		temp.add(0, result_text);
		temp.add(1, topic_text);
		temp.add(2,String.valueOf(year));
		temp.add(3,String.valueOf(month));
		temp.add(4,String.valueOf(day));
		temp.add(5,String.valueOf(hour));
		temp.add(6,String.valueOf(minute));
		
		
		
		intent.putStringArrayListExtra(EXTRA_MESSAGE,temp);	
		startActivity(intent);
		}
	}
		private void setTimeAlarm() 
		{
			  Intent intent = new Intent(this, ReminderBroadCastReceiver.class);
			  String result_text=editText.getText().toString();
			  
			  intent.putExtra(EXTRA_MESSAGE, result_text);
			  
			  PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
			  
			  Calendar cal = Calendar.getInstance();
			  
			  cal.set(year, month-1, day, hour, minute);
			  
			  aManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(), pendingIntent);
			  
			  Toast toast = Toast.makeText(this, "will be remember", Toast.LENGTH_SHORT);
			  toast.show();
			
		}
		/* *****************check box in the future (repeated tasks!!!!)*******************************/	
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			 
			/*if(arg1)
			{
				timePicker.setVisibility(View.VISIBLE);
				datePicker.setVisibility(View.VISIBLE);
			}
			else
			{
				timePicker.setVisibility(View.INVISIBLE);
				datePicker.setVisibility(View.INVISIBLE);
				
			}*/
			if((arg1)&&(arg0==pickTime))// (pickTime.isChecked())&&checkBox.isChecked()==false)
			{
				DatePickerFragment dPick= new DatePickerFragment();
				dPick.show(getFragmentManager(), "Select the Date");
				TimePickerFragment tPick =new TimePickerFragment();
				tPick.show(getFragmentManager(), "Select the Time");
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
		}
	
		private class GetFromWebTask extends AsyncTask<URL, Integer , String>
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
		}
		public  class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener
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
		public  class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
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
	
		
	}

	
