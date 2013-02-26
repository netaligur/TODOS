package com.example.todos2;

import java.util.LinkedList;
import java.util.ListIterator;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;

// SINGLETONE //
public	class	ListController extends ListActivity
{	
private int count;
private Context context;
private	static	ListController	instance	=	null;
private static DatabaseHandler db =null;
  SQLiteDatabase tasksDB = null;
  private boolean edit=false;
  private int index;
//private	Context context;	
private static  LinkedList <ItemDetails>list_details;
private AlarmManager aManager;
private	ListController(Context context)
{	 
	 this.context = context;
	 db = new DatabaseHandler(context);
	 list_details = new  LinkedList<ItemDetails>();
}	
public void setAlarmManager (AlarmManager aManagers)
{
	aManager=aManagers;
	
}
public static	ListController getInstance(Context context)
{	
	
	if(instance	==	null)
		{	
			instance	=	new ListController(context);	
		}	

	return	instance;							
}	
public void boot()
{
	int max=0;
	
	list_details=db.getAllTasks();
	 for (ItemDetails temp:list_details)
	 {
		 if (max<temp.getId())
			 max=temp.getId();
	 }
	
	count=max;
	
}


public void addOrgan(ItemDetails item)
{
	
	item.setId(++count);
	//String name=item.getName();
	item.setDone(0);
	list_details.addFirst(item);
	System.out.println(item.getTopic()+item.toString());
	db.addTask(new ItemDetails(item));
}
public ItemDetails get(int position)
{
	
	return list_details.get(position);
	
}
public int size()
{
	
	return list_details.size();
}
public static  boolean isOn()
{
	if(instance	==	null)
		return false;
	return true;
}
public void deleteOrgan (int index)
{
	
ItemDetails item=list_details.get(index);
Intent myIntent = new Intent(context, ReminderBroadCastReceiver.class);
if (myIntent!=null)
{
 PendingIntent pendingIntent= PendingIntent.getBroadcast(context,item.getId(), myIntent,PendingIntent.FLAG_ONE_SHOT);
 aManager.cancel(pendingIntent);
 }
list_details.remove(index);
db.deleteTask(item);

}
/*****/
public void editOrgan (int index)
{
	edit=true;
	this.index=index;


}
public boolean isEdit() {
	return edit;
}

public void setEdit(boolean edit) {
	this.edit = edit;
}

public int getIndex() {
	return index;
}

public void setIndex(int index) {
	this.index = index;
}

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