package com.example.todos2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ItemListBaseAdapter extends BaseAdapter
{
	private LayoutInflater l_Inflater;
	private  ListController list_details;
	static class ViewHolder
	{
		TextView txt_itemDescription;
		TextView txt_itemTopic;
		Button btdDone;
		Button btdEdit;
	}
	public ItemListBaseAdapter(Context context, ListController results) {
		list_details = results;
		l_Inflater = LayoutInflater.from(context);
	}
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
				if (convertView == null)
				{
					////////////////////////////////
					convertView = l_Inflater.inflate(R.layout.itemdetails, null);
					holder = new ViewHolder();
					holder.txt_itemDescription = (TextView) convertView.findViewById(R.id.text);
					holder.txt_itemTopic = (TextView) convertView.findViewById(R.id.topic);
					holder.btdDone = (Button)convertView.findViewById(R.id.btnDone);
					holder.btdEdit = (Button)convertView.findViewById(R.id.btnEdit);
					convertView.setTag(holder);
				} 
				else 
				{
					holder = (ViewHolder) convertView.getTag();
				}
		
		holder.txt_itemDescription.setText(list_details.get(position).getName());
		holder.txt_itemTopic.setText(list_details.get(position).getTopic());
	 	holder.btdDone.setOnClickListener(new OnClickListener()
	 	{
	 		
	 		
	        public void onClick(View v) {
	           
	        	list_details.deleteOrgan(position);
	        	 notifyDataSetChanged();
	        }
	    });
	 	holder.btdEdit.setOnClickListener(new OnClickListener()
	 	{
	 		
	 		
	        public void onClick(View v) {
	           /*******/
	        	
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
