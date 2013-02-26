package com.example.todos2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceFromBroadcast extends BroadcastReceiver 
{

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		 context.startService(new Intent(context, ReminderBroadCastReceiver.class));
	}

}
