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
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
private Button random;
private EditText editText;
private EditText topicText;
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
    	random= (Button)findViewById(R.id.button_random);
    	random.setOnClickListener(this);
    	timePicker=(TimePicker)findViewById (R.id.timePicker);
    	datePicker = (DatePicker)findViewById(R.id.datePicker);
    	checkBox = (CheckBox)findViewById(R.id.checkBox);
    	checkBox.setOnCheckedChangeListener(this);
    	timePicker.setIs24HourView(true);
    	aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    	editText=(EditText)findViewById(R.id.edit_message);
    	topicText=(EditText)findViewById(R.id.edit_topic);
    	//topicText.setPaintFlags(topicText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
       /* Typeface font = Typeface.createFromAsset(this.getAssets(), "Bleeding_Cowboys.ttf‬"); 
        create.setTypeface(font);
        random.setTypeface(font);*/
   
         
    	
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
			
		if (v.getId()==R.id.button_create)
		{
		
		Intent intent = new Intent(this,TaskNOID.class);
		//editText=(EditText)findViewById(R.id.edit_message);
		String result_text=editText.getText().toString();
		//topicText=(EditText)findViewById(R.id.edit_topic);
		String topic_text=topicText.getText().toString();
		ArrayList <String>  temp= new ArrayList<String>();
		temp.add(0, result_text);
		temp.add(1, topic_text);
		
		intent.putStringArrayListExtra(EXTRA_MESSAGE,temp);	
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
			  
			  Toast toast = Toast.makeText(this, "will be remember", Toast.LENGTH_SHORT);
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

	}

	
