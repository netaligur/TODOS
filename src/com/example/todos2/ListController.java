package com.example.todos2;

import java.util.LinkedList;

public	class	ListController
{	
private	static	ListController	instance	=	null;	
//private	Context context;	
private static  LinkedList <ItemDetails>list_details;
private	ListController()
{	
	//this.context	=	context;	
}		
public	static	ListController getInstance()
	{	
	if(instance	==	null)
		{	
			instance	=	new ListController();	
			list_details = new  LinkedList<ItemDetails>();
		}
	
						return	instance;	
	}	
public void addOrgan(ItemDetails item)
{
	list_details.addFirst(item);
	
	
}
public ItemDetails get(int position)
{
	return list_details.get(position);
	
}
public int size()
{
	
	return list_details.size();
}
public static  boolean isOn()
{
	if(instance	==	null)
		return false;
	return true;
}
public void deleteOrgan (int index)
{
	list_details.remove(index);
}


}
	