/*
 * @TaskNOIDAppWidgetProvider      1.0 2013/02/27	
 *
 * Copyright 2013 Netali & Nadav, Inc. Neatli Gur & Nadav Taoz All Rights Reserved.
 * 
 * This software is the proprietary information of Netali and Nadav- Shenkar College of Engineering and Design
 */

package com.example.todos2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * this class represents the app Widget
 * and does all of it's managing.
 * 
 */

public class TaskNOIDAppWidgetProvider extends AppWidgetProvider 
{
	private static PendingIntent	buttonP											;		// the pen' intent for scheduling the task switching every 30 sec
	private ListController	 		list_details									;		// reference to the list (singletone)
	private RemoteViews 			views											;		// connecting to the Widget xml
	public static String			ACTION_WIDGET_REFRESH = "ActionReceiverRefresh"	;		// actions identifier for the onReceive
	private static int 				indexCurr=0										;		// indicates the position of the item in the list that the widget shows
	private static int[] 			temp											;		// hold the array of all widgets id's
	
/**
 * this method is activated first when the widget is up, and every time a
 * widget event occur (in this case every 30 sec or when the user press 
 * the next task button.
 * 
 */	
	@Override
	public void onReceive(Context arg0, Intent arg1)
	{
		
		 super.onReceive(arg0, arg1);		 
		 list_details=ListController.getInstance(arg0);	// Instantiating the list
		 list_details.boot();							// boots the list
		 RemoteViews views1 = new RemoteViews(arg0.getPackageName(), R.layout.widget_layout);
		 /* deals with empty list message */
	     if(list_details.size()==0)
		 { 
			views1.setTextViewText(R.id.widgetTask, "No Task To Show");	// message to show
		 }
	     /* deals with non-empty list when the item that is viewed is not the last*/
		 if(list_details.size()>=(indexCurr+1) && list_details.size()!=0)
		 {
			if (list_details.get(indexCurr).getDone()==0)				// checks if the task is not "Done"
			{
				views1.setTextViewText(R.id.widgetTask, list_details.get(indexCurr).getTopic());
			}
			if (list_details.get(indexCurr).getDone()==1)				// checks if the task is "Done" and adding to the task "Done" title
			{
				views1.setTextViewText(R.id.widgetTask, (list_details.get(indexCurr).getTopic()+"-Done"));
			}
			indexCurr++;												// moving to the next item
		 }
		 /* deals with non-empty list when the item that is viewed is the last*/
		 else if(list_details.size()<(indexCurr+1) && list_details.size()!=0)
		 { 
				indexCurr=0; 											// moving to the first item
				if (list_details.get(indexCurr).getDone()==0)			// checks if the task is not "Done"
				{
					views1.setTextViewText(R.id.widgetTask, list_details.get(indexCurr).getTopic());
				}
				if (list_details.get(indexCurr).getDone()==1)			// checks if the task is "Done" and adding to the task "Done" title
				{
					views1.setTextViewText(R.id.widgetTask, (list_details.get(indexCurr).getTopic()+"-Done"));
				}

		 }
		/*Notifying changes to the widget manager */
		AppWidgetManager manager = AppWidgetManager.getInstance(arg0);
		manager.updateAppWidget(temp, views1);
	}
/**
* this method is activated first when the widget is up, and every time interval (which defined in the xml-
* layout/xml/example_app_widget_info.xml .
* it's starting the xml connections , deals with the time schedling switch tasks
*/	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		final int N = appWidgetIds.length;													//Gets how many widgets are on at the home screen
		temp = appWidgetIds;
		Toast.makeText(context, "TaskNOID widget ", Toast.LENGTH_SHORT).show();				//Telling the user that the widget is on
		/* passing with a loop on all of the widgets */
	    for (int i=0; i<N; i++) 
	    {
	        int appWidgetId = appWidgetIds[i];
	        Intent intent = new Intent(context, CreateTaskActivity.class);					// Intent for the add button		
	        Intent buttonI = new Intent(context, TaskNOIDAppWidgetProvider.class);			// Intent for moving between tasks
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        buttonI.setAction(ACTION_WIDGET_REFRESH);
	        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0); // Pendingintent for the add button
	        buttonP = PendingIntent.getBroadcast(context, 0, buttonI, 0);					// Pendingintent for moving between tasks 
	        /* Get the layout for the App Widget and attach an on-click listener to the button */
	        views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
	        views.setOnClickPendingIntent(R.id.play_button, pendingIntent);
	        views.setOnClickPendingIntent(R.id.nextTask, buttonP);
	        /*Tell the AppWidgetManager to perform an update on the current app widget		   */
	        list_details=ListController.getInstance(context);
	    	list_details.boot();
	        appWidgetManager.updateAppWidget(appWidgetId, views);
	        final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	        alarm.cancel(buttonP);															// Canceling previous alarm
	        long interval = 1000*30;														// Time interval for moving between tasks(30sec)
	        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),interval, buttonP);    
	     }
	}
	/* Cancelling the last alarm that was fired when the widget is terminated */
	@Override
	public void onDeleted(Context context, int [] appWidgetId)
	{
		super.onDeleted(context, appWidgetId);
        final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(buttonP);
	}
}
