package com.example.todos2;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TaskNOID extends  Activity
{   

	public void onCreate(Bundle savedInstanceState)
    {
		 ArrayList<ItemDetails>list_details=GetSearchResults();
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
    	   list_details.add(temp);
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
    	/*EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);*/
    	startActivity(intent);
    }

    private ArrayList<ItemDetails> GetSearchResults(){
    	ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();
    	
    	ItemDetails item_details = new ItemDetails();
    	item_details.setName("Adopt a Rat");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Call Amnon");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Go on Shopping");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Eat Pizza");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Make Pizza");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Order Pizza");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("wash the Pizza");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("whatch the Pizza");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("give Pizza to Oozi");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Oozi the king");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Zuza rulls");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Rat Rulls");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Pizza");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Rats are friends");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("remember the rat");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("iRat");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("IPizza");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("IPizza2");
    	results.add(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("PizzaMAN");
    	results.add(item_details);
    	
    	
    	return results;
    }

}
