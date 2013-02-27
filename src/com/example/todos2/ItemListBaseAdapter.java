/*
 * @ItemListBaseAdapter        1.0 2013/02/27	
 *
 * Copyright 2013 Netali & Nadav, Inc. Neatli Gur & Nadav Taoz All Rights Reserved.
 * 
 * This software is the proprietary information of Netali and Nadav- Shenkar College of Engineering and Design
 */

package com.example.todos2;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.content.DialogInterface;

/**
 * this class is the list view adapter
 * takes the data from the DB & xml 
 * and showing it to the user in the
 * main activity.
 * Also managing the dialog which opens when the user
 * is pressing on a task in the list view.
 */

public class ItemListBaseAdapter extends BaseAdapter 
{
	private View 							v1																;// Holds the view for the dialog
	private TextView 						showTopic														;// Topic label in the dialog
	private TextView 						showDescription													;// Description label in the dialog
	private TextView 						showAlarm														;// Alarm label
	private TextView 						showLocation													;// Location label
	private LayoutInflater 					l_Inflater														;// To inflate item_details xml
	private ListController				 	list_details													;// Holds instance of the list
	private Activity 						context															;// Holds the context from main
	private String 							topic															;// Topic of the task
	public final static String 				EXTRA_MESSAGE = "com.example.todos2.CreateTaskActivity"			;// Path to create task activity
	/* Inner Class , represents an item in the listview */
	static class ViewHolder
	{
		TextView txt_itemDescription;
		TextView txt_itemTopic;
		Button btdDelete;
		Button btdDone;
	}
	/* Constructor - initializing important varables */
	public ItemListBaseAdapter(Activity context, ListController results)
	{
		list_details = results;
		l_Inflater = LayoutInflater.from(context);
		this.context=context;	
	}
	/* Inflating an item in the list,Managing it's dialog box */
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		/* If There is no item in the view */
		if (convertView == null)
		{
			convertView = l_Inflater.inflate(R.layout.itemdetails, null);			// Inflating the item from xml
			convertView.setClickable(true);											// Setting the item view so the user can press it
			convertView.setOnClickListener(new OnClickListener() {					// Open dialog when clicking a list item
			public void onClick(View paramView)
			{		                    
				AlertDialog dialogBox = OptionsDialogBox(list_details.get(position),paramView); // Setting the dialog box item
				dialogBox.show();																// Showing the dialog box
				/* Initializing all of the elements of the task in the dialog box */
				showTopic=(TextView)v1.findViewById(R.id.ShowTopicsItem);
				showTopic.setText(list_details.get(position).getTopic());
				showDescription=(TextView)v1.findViewById(R.id.ShowDescriptionItem);
				showDescription.setText(list_details.get(position).getName());
				showAlarm=(TextView)v1.findViewById(R.id.ShowAlarmItem);
				showAlarm.setText(list_details.get(position).toStringDialogAlarm() );
				showLocation=(TextView)v1.findViewById(R.id.ShowLocationItem);
				showLocation.setText(list_details.get(position).toStringAddress() );
			}
			/* Responsible for building the dialog box */
			public AlertDialog OptionsDialogBox(final ItemDetails it, final View v)
			{
				LayoutInflater inflater = LayoutInflater.from(context);				
				v1=inflater.inflate(R.layout.task_details_edit, null);
				AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(R.string.task_menu);
				builder.setView(v1)													// Sets the view of the xml of the dialog		                			
				.setPositiveButton(R.string.button_Edit, new DialogInterface.OnClickListener() {
				/* On Click on the positive button - "Edit " - doing all of the actions need for start the create task activity */
				public void onClick(DialogInterface dialog, int id)
				{
				    Intent intent = new Intent(context, CreateTaskActivity.class);
				    ArrayList <String>  temp= new ArrayList<String>();				// String array for putting in the intent
				    /* Setting the list with all of the task details and put it in the intent */
				    temp.add(0, list_details.get(position).getName());
				    temp.add(1, list_details.get(position).getTopic());
				    temp.add(2,String.valueOf(list_details.get(position).getYear()));
				    temp.add(3,String.valueOf(list_details.get(position).getMonth()));
				    temp.add(4,String.valueOf(list_details.get(position).getDay()));
				    temp.add(5,String.valueOf(list_details.get(position).getHour()));
				    temp.add(6,String.valueOf(list_details.get(position).getMinute()));
				    temp.add(7,(list_details.get(position).getAddress()));
				    intent.putStringArrayListExtra(EXTRA_MESSAGE,temp);	
				    list_details.deleteOrgan(position);
				    context.startActivity(intent);   								// Starting the create task activity
				    /* Cancel button */
				 }}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() 
				 	{
				        public void onClick(DialogInterface dialog, int id){}
				    });      
				 return builder.create();
				 }
			protected Dialog onCreateDialog(final int id)
			{
				return null;
			}});
			/* Instantiating the xml's of a list item from holder */
			holder = new ViewHolder();
			holder.txt_itemDescription = (TextView) convertView.findViewById(R.id.text);
			holder.txt_itemTopic = (TextView) convertView.findViewById(R.id.topic);				
			holder.btdDelete = (Button)convertView.findViewById(R.id.btnDelete);
			holder.btdDone = (Button)convertView.findViewById(R.id.btnDone);
			convertView.setTag(holder);
			} 
		/* Noting will occurred if the item is already exists */
		else 
		{
			holder = (ViewHolder) convertView.getTag();
		}
		String date;
		date=" "+list_details.get(position).toString();	
		/* All of this conditions are responsible to represent the task with a line if it's done */
		/* No Done */
		if (list_details.get(position).getDone()==0)
		{
				/* Checks if needs to print the time alarm */
				if(list_details.get(position).getYear()!=0 && list_details.get(position).getMonth()!=0 && list_details.get(position).getDay()!=0)
					holder.txt_itemDescription.setText(date);					
			    else holder.txt_itemDescription.setText("");
				/* Sets flags in all fields - no line through */
				holder.txt_itemDescription.setPaintFlags( holder.txt_itemDescription.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
				holder.txt_itemTopic.setPaintFlags( holder.txt_itemTopic.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
				holder.txt_itemTopic.setText(list_details.get(position).getTopic());
		}
		/* The task is done */
		if (list_details.get(position).getDone()==1)
		{
			/* Checks if needs to print the time alarm */
			if (list_details.get(position).getYear()!=0 && list_details.get(position).getMonth()!=0 && list_details.get(position).getDay()!=0)
				holder.txt_itemDescription.setText(date);
			else 
				holder.txt_itemDescription.setText("");	
			/* Sets flags in all fields - line through */
			holder.txt_itemDescription.setPaintFlags(holder.txt_itemDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			holder.txt_itemTopic.setText(list_details.get(position).getTopic());
			holder.txt_itemTopic.setPaintFlags(holder.txt_itemTopic.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}
		/* Listener to button delete */
	 	holder.btdDelete.setOnClickListener(new OnClickListener()
	 	{	
	        public void onClick(View v)
	        {
	        	list_details.deleteOrgan(position);						// Deleting the item
	        	notifyDataSetChanged();
	        }
	    });	 
	 	/* Listener for button done */
	 	holder.btdDone.setOnClickListener(new OnClickListener()
	 	{	
	        public void onClick(View v) 
	        {
	        	list_details.updateDone(position);
	        	notifyDataSetChanged();
	        	Intent sendIntent = new Intent();
	        	sendIntent.setAction(Intent.ACTION_SEND);
	        	topic=list_details.get(position).getTopic();
	        	/* If the task is done , suggest the user to share it */
	        	if (list_details.get(position).getDone()==1)
	        	{
		        	sendIntent.putExtra(Intent.EXTRA_TEXT, "I Just Completed "+topic+" via TaskNOID");
		        	sendIntent.setType("text/plain");
		        	context.startActivity(Intent.createChooser(sendIntent, "Share with"));
	        	}        	
	        }
	    });
		return convertView;
	}
	/* Returns the size */
	public int getCount() {
		return list_details.size();	
	}
	/* Returns the item */
	public Object getItem(int position) {
		return list_details.get(position);
	}
	/* Returns the item id */
	public long getItemId(int position) {
		return position;
	}
}
