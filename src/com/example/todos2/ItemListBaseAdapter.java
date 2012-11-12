package com.example.todos2;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ItemListBaseAdapter extends BaseAdapter
{
	private LayoutInflater l_Inflater;
	private static ArrayList<ItemDetails>list_details;
	static class ViewHolder
	{
	TextView txt_itemDescription;
	Button btdDone;
	}
	public ItemListBaseAdapter(Context context, ArrayList<ItemDetails> results) {
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
					holder.btdDone = (Button)convertView.findViewById(R.id.btnDone);
					convertView.setTag(holder);
				} 
				else 
				{
					holder = (ViewHolder) convertView.getTag();
				}	
		holder.txt_itemDescription.setText(list_details.get(position).getName());
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
