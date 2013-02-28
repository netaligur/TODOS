/*
 * @ServiceFromBroadcast       1.0 2013/02/27	
 *
 * Copyright 2013 Netali & Nadav, Inc. Neatli Gur & Nadav Taoz All Rights Reserved.
 * 
 * This software is the proprietary information of Netali and Nadav- Shenkar College of Engineering and Design
 */

package com.example.todos2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import co.il.shenkar.tasknoid.R;

public class ServiceFromBroadcast extends BroadcastReceiver 
{

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		 context.startService(new Intent(context, ReminderBroadCastReceiver.class));
	}

}
