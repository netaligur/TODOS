package com.example.todos2;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateTaskActivity extends Activity  implements OnClickListener, OnCheckedChangeListener 
{
private Button create;
private EditText editText;
private TimePicker timePicker;
private DatePicker datePicker;
private CheckBox checkBox;
private int hour;
private int minute;
private int year;
private int month;
private int day;
private AlarmManager aManager;

	 public final static String EXTRA_MESSAGE = "com.example.todos2.TaskNOID";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        create= (Button)findViewById(R.id.button_create);
    	create.setOnClickListener(this);
    	timePicker=(TimePicker)findViewById (R.id.timePicker);
    	datePicker = (DatePicker)findViewById(R.id.datePicker);
    	checkBox = (CheckBox)findViewById(R.id.checkBox);
    	checkBox.setOnCheckedChangeListener(this);
    	timePicker.setIs24HourView(true);
    	aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
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
  /*  public void create(View view) {
  	  // Do something in response to button create
  	Intent intent = new Intent(this, TaskNOID.class);
  	EditText editText = (EditText) findViewById(R.id.edit_message);
  	String message = editText.getText().toString();
  	intent.putExtra(EXTRA_MESSAGE, message);
 	
  	startActivity(intent);
  	
  	
  }*/


	

	public void onClick(View v) 
	{
			
		Intent intent = new Intent(this,TaskNOID.class);
		editText=(EditText)findViewById(R.id.edit_message);
		String result_text=editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, result_text);					
		if(checkBox.isChecked())
		{
			hour = timePicker.getCurrentHour();
			minute=timePicker.getCurrentMinute();
			year = datePicker.getYear();
			month = datePicker.getMonth();
			day = datePicker.getDayOfMonth();
			
			setTimeAlarm();	
			}
		startActivity(intent);
	}
		private void setTimeAlarm() 
		{
			  Intent intent = new Intent(this, ReminderBroadCastReceiver.class);
			  String result_text=editText.getText().toString();
			  
			  intent.putExtra(EXTRA_MESSAGE, result_text);
			  
			  PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
			  
			  Calendar cal = Calendar.getInstance();
			  
			  cal.set(year, month, day, hour, minute);
			  
			  aManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(), pendingIntent);
			  
			  Toast toast = Toast.makeText(this, "Alarm Is Set", Toast.LENGTH_SHORT);
			  toast.show();
			
		}

		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			 
			if(arg1)
			{
				timePicker.setVisibility(View.VISIBLE);
				datePicker.setVisibility(View.VISIBLE);
			}
			else
			{
				timePicker.setVisibility(View.INVISIBLE);
				datePicker.setVisibility(View.INVISIBLE);
			}
		}
	}
