/*
 * @ListController        1.0 2013/02/27	
 *
 * Copyright 2013 Netali & Nadav, Inc. Neatli Gur & Nadav Taoz All Rights Reserved.
 * 
 * This software is the proprietary information of Netali and Nadav- Shenkar College of Engineering and Design
 */

package com.example.todos2;

import java.util.LinkedList;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import co.il.shenkar.tasknoid.R;

/**
 * this class is managing the Singltone
 * design pattern, the purpose of this class
 * is to represent one list of tasks and make 
 * sure that all of the actions that will
 * be held on the list will pass through
 * this class - it's the interface of the
 * list & DB.
 *
 */

public	class	ListController extends ListActivity
{	
private int 								count										;	// How many items there are in the list
private Context 							context										;	// For saving the context
private	static	ListController				instance=null								;	// Single Instance of the class
private static DatabaseHandler 				db=null										;	// DB manager handler
SQLiteDatabase							 	tasksDB=null								;	// DB SQLite instance
private boolean 							edit=false									;	
private int 								index										;	// The item index
private static  							LinkedList <ItemDetails>list_details		;	// The list itself
private AlarmManager			        	aManager									;	// Alarm manager
private LocationManager 					lManager									;	// Location manager
	/* Constructor which get context,Doing first initializations */
	private	ListController(Context context)
	{	 
		 this.context = context;									// Setting the context 
		 db = new DatabaseHandler(context);							// Initializing DB
		 list_details = new  LinkedList<ItemDetails>();				// Creating the List
	}	
	/* Sets the Alarm Manager */
	public void setAlarmManager (AlarmManager aManagers)
	{
		aManager=aManagers;	
	}
	/* Sets the Location Manager */
	public void setLocationManager (LocationManager lManagers)
	{
		lManager=lManagers;
	}
	/* Doing the instantiation of the class (Singleton) */
	public static	ListController getInstance(Context context)
	{	
		/* New instance condition */
		if(instance	==	null)
		{	
			instance	=	new ListController(context);	
		}	
		/* Returning the instance of the new object or the instance of the exiting object */
		return	instance;							
	}	
	/* Does the initialization of the list(tasks) from the DB and updating the max id */
	public void boot()
	{
		int max=0;
		list_details=db.getAllTasks();								// Gets the tasks from the DB
		/* Loop for updating the most id existing */
		for (ItemDetails temp:list_details)
		{
			if (max<temp.getId()) max=temp.getId();
		}
		count=max;	
	}
/**
* Function for adding a new item to the list(updating the list + DB)
*/	
	public void addOrgan(ItemDetails item)
	{		
		item.setId(++count);										// Setting a new id from count + 1
		item.setDone(0);											// Set the new task to not-done
		list_details.addFirst(item);								// Adding the item in the beginning of the list
		db.addTask(new ItemDetails(item));							// Updates the DB
	}
	/* Returns item at position */
	public ItemDetails get(int position)
	{	
		return list_details.get(position);		
	}
	/* Returns the size of the list */
	public int size()
	{	
		return list_details.size();
	}
	/* For check if there is an instance or not */
	public static  boolean isOn()
	{
		if(instance	==	null)
			return false;
		return true;
	}
	/* Deleting an item from the list and DB */
	public void deleteOrgan (int index)
	{		
		ItemDetails item=list_details.get(index);									// Gets the item 
		/* Checking whether there upcomming event from that item (with the id as an intent identifier) if there is-cancel it */
		Intent myIntent = new Intent(context, ReminderBroadCastReceiver.class);		
		if (myIntent!=null)
		{
			 PendingIntent pendingIntent= PendingIntent.getBroadcast(context,item.getId(), myIntent,PendingIntent.FLAG_ONE_SHOT);
			 aManager.cancel(pendingIntent);										// Cancel time notification - if there is
			 lManager.removeProximityAlert(pendingIntent);							// Cancel location notification - if there is
		}
		list_details.remove(index);													// Delete from the list
		db.deleteTask(item);														// Delete from DB
		
	}
	/* Dealing with edit an organ */
	public void editOrgan (int index)
	{
		edit=true;													
		this.index=index;
	}
	/* Checking if the organ is edit */
	public boolean isEdit() {
		return edit;
	}
	/* Setting edit */
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	/* Getting the index */
	public int getIndex() {
		return index;
	}
	/* Setting the index */
	public void setIndex(int index) {
		this.index = index;
	}
	/* Update that the task is no "Done" or sets from done to undone */
	public void updateDone (int index)
	{
		ItemDetails temp;
		temp=list_details.get(index);
		if (temp.getDone()==0)
		{
			temp.setDone(1);
		}
		else temp.setDone(0);
		db.updateTask(temp);
	}
}