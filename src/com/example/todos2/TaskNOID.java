package com.example.todos2;


import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
public class TaskNOID extends  Activity
{   
	public static final String ONE_TIME = "";
	private Intent alarm;
	public void onCreate(Bundle savedInstanceState)
    {
		ListController list_details=ListController.getInstance(this);
		list_details.boot();
		//list_details.createTable();										// building the data base table
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_noid);
        Intent intent = getIntent();
        ArrayList<String>message = intent.getStringArrayListExtra(CreateTaskActivity.EXTRA_MESSAGE);

        ListView lv1 = (ListView) findViewById(R.id.listV_main);
        lv1.setAdapter(new ItemListBaseAdapter(this,  list_details));
        
         // SharedPreferences preferences = getSharedPreferences(TaskNOID.ONE_TIME, 0);
         //preferences.edit().remove("IsServiceRun").commit();
        
        SharedPreferences settings = getSharedPreferences(TaskNOID.ONE_TIME, 0);
        
        boolean Servic_Up = settings.getBoolean("IsServiceRun", false);
        if(Servic_Up==false)
        {
         SetAlarmService();
        }
        
       if (message!=null)
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
    	   System.out.println(temp.toString());
       }

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

}
