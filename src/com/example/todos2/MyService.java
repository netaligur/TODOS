/*package com.example.todos2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.IntentService;
import android.content.Intent;


public class MyService extends IntentService
{

	public MyService() 
	{
		super("Myservice");	 
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		try {
			URL url = new URL("http://mobile1-tasks-dispatcher.herokuapp.com/task/random");
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			
			InputStreamReader inReader = new InputStreamReader(in);
			
			BufferedReader bufferReader = new BufferedReader(inReader);
			
			String responseBulider = new String();
			
			for ( String line = bufferReader.readLine(); line != null; line = bufferReader.readLine() )
			{
				responseBulider+=line;
			}
			JSONObject jasonResponse = new JSONObject(responseBulider);
	
			String Result_txt =jasonResponse.getString("description");
						
			ItemDetails item = new ItemDetails();
			
			item.setName(Result_txt);
			item.setTopic(Result_txt);			
			DatabaseHandler db  = new DatabaseHandler(this);
			db.addTask(item);
		    db.close();	
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (JSONException e) {
			
			e.printStackTrace();
		}	 
	}
}
*/