package com.example.todos2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
public class TaskNOID extends  Activity
{   

	public void onCreate(Bundle savedInstanceState)
    {
		ListController list_details=ListController.getInstance(this);
		list_details.boot();
		//list_details.createTable();										// building the data base table
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_noid);
        Intent intent = getIntent();
        String message = intent.getStringExtra(CreateTaskActivity.EXTRA_MESSAGE);

        ListView lv1 = (ListView) findViewById(R.id.listV_main);
        lv1.setAdapter(new ItemListBaseAdapter(this,  list_details));

       if (message!=null)
       {
    	   ItemDetails temp= new ItemDetails();
    	   temp.setName(message);
    	   list_details.addOrgan(temp);
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

 

}
