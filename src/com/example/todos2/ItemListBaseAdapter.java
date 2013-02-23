package com.example.todos2;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

public class ItemListBaseAdapter extends BaseAdapter 
{
	private View v1;
	private TextView showTopic;
	private TextView showDescription;
	private TextView showAlarm;
	private TextView showLocation;
	private LayoutInflater l_Inflater;
	private  ListController list_details;
	private Activity context;
	public final static String EXTRA_MESSAGE = "com.example.todos2.CreateTaskActivity";
	static class ViewHolder
	{
		TextView txt_itemDescription;
		TextView txt_itemTopic;
		Button btdDelete;
		//Button btdEdit;
		Button btdDone;
	}
	public ItemListBaseAdapter(Activity context, ListController results) {
		list_details = results;
		l_Inflater = LayoutInflater.from(context);
		this.context=context;
		
	
		
	}
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
				if (convertView == null)
				{
					////////////////////////////////
					
					convertView = l_Inflater.inflate(R.layout.itemdetails, null);
					convertView.setClickable(true);
				    convertView.setOnClickListener(new OnClickListener() {//open dialog when clicking a list item
				                    public void onClick(View paramView)
				                    {
				                    
				                			AlertDialog dialogBox = OptionsDialogBox(list_details.get(position),paramView);
				                			dialogBox.show();
				                			showTopic=(TextView)v1.findViewById(R.id.ShowTopicsItem);
				                			showTopic.setText(list_details.get(position).getTopic());
				                			showDescription=(TextView)v1.findViewById(R.id.ShowDescriptionItem);
				                			showDescription.setText(list_details.get(position).getName());
				                			showAlarm=(TextView)v1.findViewById(R.id.ShowAlarmItem);
				                			showAlarm.setText(list_details.get(position).toStringDialogAlarm() );
				                			showLocation=(TextView)v1.findViewById(R.id.ShowLocationItem);
				                			showLocation.setText(list_details.get(position).getAddress() );
				                		
				                	}
				                		
				                		public AlertDialog OptionsDialogBox(final ItemDetails it, final View v)
				                		{
				                			//final CharSequence[] options = {"Edit Task","Show Task Details","Mark Me As Important"};
				                			LayoutInflater inflater = LayoutInflater.from(context);
				                			 v1=inflater.inflate(R.layout.task_details_edit, null);
				                			AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(R.string.task_menu);
				                			builder.setView(v1)				                			
				                			.setPositiveButton(R.string.button_Edit, new DialogInterface.OnClickListener() {
				                	               public void onClick(DialogInterface dialog, int id)
				                	               {
				                	            	   Intent intent = new Intent(context, CreateTaskActivity.class);
				                	            	   
				                	            	   ArrayList <String>  temp= new ArrayList<String>();
				                	            		temp.add(0, list_details.get(position).getName());
				                	            		temp.add(1, list_details.get(position).getTopic());
				                	            		temp.add(2,String.valueOf(list_details.get(position).getYear()));
				                	            		temp.add(3,String.valueOf(list_details.get(position).getMonth()));
				                	            		temp.add(4,String.valueOf(list_details.get(position).getDay()));
				                	            		temp.add(5,String.valueOf(list_details.get(position).getHour()));
				                	            		temp.add(6,String.valueOf(list_details.get(position).getMinute()));
				                	            		intent.putStringArrayListExtra(EXTRA_MESSAGE,temp);	
				                	            		list_details.deleteOrgan(position);
				                	            		context.startActivity(intent);
				                	            	   
				                	               }
				                	           })
				                	           .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				                	               public void onClick(DialogInterface dialog, int id) {
				                	                   
				                	               }
				                	           });      
				                		
				                	    return builder.create();
				                	}
				                		
				    
				                	    protected Dialog onCreateDialog(final int id)
				                		{
											return null;
				                	       /*  LayoutInflater factory = LayoutInflater.from(context);
				                	         final View textEntryView = factory.inflate(R.layout.edititemmenu, null);
				                	          return new AlertDialog.Builder(TaskListActivity.this).setTitle("Set Item").setView(textEntryView).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				                	                    public void onClick(DialogInterface dialog, int whichButton) {
				                	                    	
				                	                    	EditText etTitle = (EditText)textEntryView.findViewById(R.id.etTitle);
				                	                    	EditText etDescription = (EditText)textEntryView.findViewById(R.id.etDescription);
				                	                    	EditText etLocation = (EditText)textEntryView.findViewById(R.id.etLocation);
				                	                    	title = etTitle.getText().toString();
				                	                    	description = etDescription.getText().toString();
				                	                    	
				                	                    	setEditTask(id,title,description);
				                	                    }

				                						private void setEditTask(int id, String title,String description) 
				                						{
				                							db.open();
				                		        			db.updateTask(id, title, description);
				                		        			db.close();
				                		        			for(int i=0;i<databasesingleton.getArrayList().size();i++){
				                		        				
				                		        				if(databasesingleton.getArrayList().get(i).getId() == id){
				                		        					
				                		        					databasesingleton.getArrayList().get(i).setItemDescription(title);
				                				        			databasesingleton.getArrayList().get(i).setDescription(description);
				                					        		adapter .notifyDataSetChanged();
				                				        			break;
				                		        				}
				                		        				
				                		        			}
				                		        			

				                						}
				                	                })
				                	                .setNegativeButton("No", new DialogInterface.OnClickListener() {
				                	                    public void onClick(DialogInterface dialog, int whichButton) {

				                	                      dialog.dismiss();
				                	                    }
				                	                })
				                	                .create();
				                	        }
				                		*/
				                    }
				                    });
					holder = new ViewHolder();
					holder.txt_itemDescription = (TextView) convertView.findViewById(R.id.text);
					holder.txt_itemTopic = (TextView) convertView.findViewById(R.id.topic);
					//Typeface font = Typeface.createFromAsset(this.getAssets(), path);
					//holder.txt_itemDescription.setTypeface(font);					
					holder.btdDelete = (Button)convertView.findViewById(R.id.btnDelete);
					//holder.btdEdit = (Button)convertView.findViewById(R.id.btnEdit);
					holder.btdDone = (Button)convertView.findViewById(R.id.btnDone);
					convertView.setTag(holder);
				} 
				else 
				{
					holder = (ViewHolder) convertView.getTag();
				}
				String date;
				date=" "+list_details.get(position).toString();
	
				
		if (list_details.get(position).getDone()==0)
		{
		System.out.print(date);
			if (list_details.get(position).getYear()!=0 && list_details.get(position).getMonth()!=0 && list_details.get(position).getDay()!=0)
				holder.txt_itemDescription.setText(list_details.get(position).getName()+date);
			
			else
				holder.txt_itemDescription.setText(list_details.get(position).getName());
		holder.txt_itemDescription.setPaintFlags( holder.txt_itemDescription.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		holder.txt_itemTopic.setPaintFlags( holder.txt_itemTopic.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		holder.txt_itemTopic.setText(list_details.get(position).getTopic());
		}
		if (list_details.get(position).getDone()==1)
		{
			if (list_details.get(position).getYear()!=0 && list_details.get(position).getMonth()!=0 && list_details.get(position).getDay()!=0)
				holder.txt_itemDescription.setText(list_details.get(position).getName()+date);
			
			else
				holder.txt_itemDescription.setText(list_details.get(position).getName());
			
			holder.txt_itemDescription.setPaintFlags(holder.txt_itemDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			holder.txt_itemTopic.setText(list_details.get(position).getTopic());
			holder.txt_itemTopic.setPaintFlags(holder.txt_itemTopic.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}
		
	 	holder.btdDelete.setOnClickListener(new OnClickListener()
	 	{
	 		
	 		
	        public void onClick(View v) {
	           
	        	list_details.deleteOrgan(position);
	        	 notifyDataSetChanged();
	        }
	    });
	 
	 	holder.btdDone.setOnClickListener(new OnClickListener()
	 	{	
	        public void onClick(View v) {
	        	
	        	list_details.updateDone(position);
	        	notifyDataSetChanged();
	        }
	    });
		return convertView;
	}

	public int getCount() {
		return list_details.size();
		
	}
	public Object getItem(int position) {
		return list_details.get(position);
	}
	public long getItemId(int position) {
		return position;
	}


}
