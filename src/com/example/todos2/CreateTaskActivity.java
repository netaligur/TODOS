package com.example.todos2;

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

	 public final static String EXTRA_MESSAGE = "com.example.todos2.MESSAGE";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

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
    public void create(View view) {
  	  // Do something in response to button create
  	Intent intent = new Intent(this, TaskNOID.class);
  	EditText editText = (EditText) findViewById(R.id.edit_message);
  	String message = editText.getText().toString();
  	intent.putExtra(EXTRA_MESSAGE, message);
 	
  	startActivity(intent);
  	
  	
  }


	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
