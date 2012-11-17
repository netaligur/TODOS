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
		ListController list_details=ListController.getInstance();
		if (list_details.size()<1)
		{
		 list_details=GetSearchResults(list_details);
		}
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

    private ListController GetSearchResults(ListController list_details)
    {
    	
    	ItemDetails item_details;
    	item_details = new ItemDetails();
    	item_details.setName("Adopt a Rat");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Call Amnon");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Go on Shopping");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Eat Pizza");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Make Pizza");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Order Pizza");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("wash the Pizza");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("whatch the Pizza");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("give Pizza to Oozi");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Oozi the king");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Zuza rulls");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Rat Rulls");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Pizza");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("Rats are friends");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("remember the rat");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("iRat");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("IPizza");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("IPizza2");
    	list_details.addOrgan(item_details);
    	item_details = new ItemDetails();
    	item_details.setName("PizzaMAN");
    	list_details.addOrgan(item_details);

    	return list_details;
    }

}
