package com.example.todos2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;

public	class	ReminderBroadCastReceiver	extends	BroadcastReceiver	
{	
private NotificationManager notificationManager;
	
	
	public void onReceive(Context context, Intent intent) 
	{
		
		 String message  = intent.getStringExtra(CreateTaskActivity.EXTRA_MESSAGE);
		
		 notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		 
		 CharSequence from = " Thinking Of You!!";
				  
		 Intent intent2 = new Intent(context, TaskNOID.class);
		 
		 PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent2 , 0);
 
		 Notification notif = new Notification(R.drawable.arik_rat,message, System.currentTimeMillis());
		
		 notif.setLatestEventInfo(context, from, message, contentIntent);
		 
		 notif.flags |= Notification.FLAG_AUTO_CANCEL;
		
		 notificationManager.notify(0, notif);
		 
    }
	
}



