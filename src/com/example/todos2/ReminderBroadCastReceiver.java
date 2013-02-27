/*
 * @ReminderBroadCastReceiver        1.0 2013/02/27	
 *
 * Copyright 2013 Netali & Nadav, Inc. Neatli Gur & Nadav Taoz All Rights Reserved.
 * 
 * This software is the proprietary information of Netali and Nadav- Shenkar College of Engineering and Design
 */

package com.example.todos2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * this class is the receiver of all 
 * of the out-of-program events and
 * handles them equally.
 * events:time alarm & location alarm
 * 
 */

public	class	ReminderBroadCastReceiver	extends	BroadcastReceiver	
{	
private NotificationManager notificationManager;
/**
 * This function is invoked when an notification is
 * coming - time alarm or location alarm
 * 
 */
	public void onReceive(Context context, Intent intent) 
	{		
		/* Extracting the Topic from the task of the alarm */
		String message  = intent.getStringExtra(CreateTaskActivity.EXTRA_MESSAGE);							
		notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);	// Initializing notification manager	 
		CharSequence from = "TaskNOID";				  
		Intent intent2 = new Intent(context, TaskNOID.class);		 
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent2 , 0);
		Notification notif = new Notification(R.drawable.arik_rat,message, System.currentTimeMillis());		// The image of the notification
		notif.setLatestEventInfo(context, from, message, contentIntent);		 
		notif.defaults |= Notification.DEFAULT_SOUND;		 												// The sound of the notification
		notif.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, notif);																// Doing the notify 
    }	
}



