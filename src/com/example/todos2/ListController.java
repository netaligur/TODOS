package com.example.todos2;

import java.util.LinkedList;
import java.util.ListIterator;

import android.app.ListActivity;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;

// SINGLETONE //
public	class	ListController extends ListActivity
{	
private int count;
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
	int max=0;
	
	list_details=db.getAllTasks();
	System.out.println("boot");
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
public void updateDone (int index)
{
	ItemDetails temp;
	temp=list_details.get(index);
	temp.setDone(1);
	db.updateTask(temp);
}
}