package com.example.todos2;

import java.util.LinkedList;

import android.app.ListActivity;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;

// SINGLETONE //
public	class	ListController extends ListActivity
{	
private Context context;
private	static	ListController	instance	=	null;
private static DatabaseHandler db =null;
  SQLiteDatabase tasksDB = null;
//private	Context context;	
private static  LinkedList <ItemDetails>list_details;
private	ListController(Context context)
{	 
	 this.context = context;
	 db = new DatabaseHandler(context);
	 list_details = new  LinkedList<ItemDetails>();
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
	
	list_details=db.getAllTasks();
}


public void addOrgan(ItemDetails item)
{
	//String name=item.getName();
	
	list_details.addFirst(item);
	/* try {
	        tasksDB.execSQL("INSERT INTO " +
	             TABLE_NAME +
	            "  Values ("+name+");");
	     }
	     catch (SQLiteException se ) {
	         Log.e(getClass().getSimpleName(), "Could not create records");
	     }
		
	
	*/
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
list_details.remove(index);

db.deleteTask(item);
}
/*****/
public void editOrgan (int index,ItemDetails newItem)
{
	

list_details.add(index, newItem);
db.updateTask(newItem);
}
}