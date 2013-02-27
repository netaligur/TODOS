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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

/**
 * this class represents the app Widget
 * and does all of it's managing.
 * 
 */

public class TaskNOIDAppWidgetProvider extends AppWidgetProvider 
{
	private static PendingIntent	buttonP											;		// the pen' intent 
	private ListController	 		list_details									;		//
	private RemoteViews 			views											;		//
	public static String			ACTION_WIDGET_REFRESH = "ActionReceiverRefresh"	;		//
	private static int 				indexCurr=0										;		//
	private static int[] 			temp											;		//
	@Override
	public void onReceive(Context arg0, Intent arg1)
	{
		
		 super.onReceive(arg0, arg1);

		 //Toast.makeText(arg0, String.valueOf(indexCurr), Toast.LENGTH_LONG).show();
		 list_details=ListController.getInstance(arg0);
		 list_details.boot();
		 RemoteViews views1 = new RemoteViews(arg0.getPackageName(), R.layout.widget_layout);
			 if (list_details.size()==0)
			 { 
				 views1.setTextViewText(R.id.widgetTask, "No Task To Show");
				 //Toast.makeText(arg0, String.valueOf(indexCurr)+"size=0", Toast.LENGTH_LONG).show();
			 }
			  if(list_details.size()>=(indexCurr+1) && list_details.size()!=0)
			 {
				 //Toast.makeText(arg0, String.valueOf(indexCurr)+"size>=int", Toast.LENGTH_LONG).show();
				  if (list_details.get(indexCurr).getDone()==0)
				  {
					  views1.setTextViewText(R.id.widgetTask, list_details.get(indexCurr).getTopic());
				  }
				  if (list_details.get(indexCurr).getDone()==1)
				  {
					  views1.setTextViewText(R.id.widgetTask, (list_details.get(indexCurr).getTopic()+"-Done"));
				  }
				 
				 indexCurr++;
				 
			 }
			  else  if(list_details.size()<(indexCurr+1) && list_details.size()!=0)
			 { 
				 indexCurr=0; 
				 //Toast.makeText(arg0, String.valueOf(indexCurr)+"size<int", Toast.LENGTH_LONG).show();

				 if (list_details.get(indexCurr).getDone()==0)
				  {
					  views1.setTextViewText(R.id.widgetTask, list_details.get(indexCurr).getTopic());
				  }
				  if (list_details.get(indexCurr).getDone()==1)
				  {
					  views1.setTextViewText(R.id.widgetTask, (list_details.get(indexCurr).getTopic()+"-Done"));
				  }

			 }
			 AppWidgetManager manager = AppWidgetManager.getInstance(arg0);
			 manager.updateAppWidget(temp, views1);
	}
	@Override
		public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
	  {
		 final int N = appWidgetIds.length;
		 temp = appWidgetIds;
		
		 Toast.makeText(context, "TaskNOID widget ", Toast.LENGTH_SHORT).show();
	        // Perform this loop procedure for each App Widget that belongs to this provider
	        for (int i=0; i<N; i++) 
	        {
	        	
	            int appWidgetId = appWidgetIds[i];
	            
	            // Create an Intent to launch ExampleActivity
	            Intent intent = new Intent(context, CreateTaskActivity.class);
	            Intent buttonI = new Intent(context, TaskNOIDAppWidgetProvider.class);
	            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            buttonI.setAction(ACTION_WIDGET_REFRESH);
	            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
	            buttonP = PendingIntent.getBroadcast(context, 0, buttonI, 0);
	            // Get the layout for the App Widget and attach an on-click listener
	            // to the button
	            views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
	            views.setOnClickPendingIntent(R.id.play_button, pendingIntent);
	            views.setOnClickPendingIntent(R.id.nextTask, buttonP);
	            LayoutInflater inflater = LayoutInflater.from(context);
	          //  v1=inflater.inflate(R.layout.widget_layout, null);
	            // Tell the AppWidgetManager to perform an update on the current app widget
	            list_details=ListController.getInstance(context);
	    		list_details.boot();
	    		
	         //   views.setTextViewText(R.id.widgetTask, "Test message");
	            appWidgetManager.updateAppWidget(appWidgetId, views);
	            
	            final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	            alarm.cancel(buttonP);
	            long interval = 1000*30;
	            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),interval, buttonP);
	            
	        }
	    

	  }
	@Override
	public void onDeleted(Context context, int [] appWidgetId){
		super.onDeleted(context, appWidgetId);
        final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(buttonP);
	}

	}
