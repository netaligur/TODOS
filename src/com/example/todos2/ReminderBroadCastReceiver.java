package com.example.todos2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public	class	ReminderBroadCastReceiver	extends	BroadcastReceiver	
{	
		public	void	onReceive(Context context,Intent intent)	
		{	
			/*//System.out.print("YYOYOYOYOYOY");
			Intent	myIntent =	new	Intent(context,TaskNOID.class);	
			PendingIntent pendingIntent	=	PendingIntent.getActivity(context,	0,	myIntent,	0);	
			NotificationManager notificationManager	=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);									
			Notification notification	=	new	Notification(R.drawable.ic_launcher,"Notification Ticker",System.currentTimeMillis());	
			notification.setLatestEventInfo(context,"Notification Title","Notification Text",	pendingIntent);	
			notificationManager.notify(0,	notification);	//0	is	id	*/
		}	
}
