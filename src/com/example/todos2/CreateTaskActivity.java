package com.example.todos2;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class CreateTaskActivity extends Activity {

	 public final static String EXTRA_MESSAGE = "com.example.todos2.MESSAGE";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void create(View view) {
  	  // Do something in response to button
  	Intent intent = new Intent(this, TaskNOID.class);
  	EditText editText = (EditText) findViewById(R.id.edit_message);
  	String message = editText.getText().toString();
  	intent.putExtra(EXTRA_MESSAGE, message);
  	startActivity(intent);
  	
  }

}
