package com.example.todos2;

public class ItemDetails 
{
	
	private String description;
	public ItemDetails (ItemDetails item)
	{
		this.description=item.getName();
	}
	public ItemDetails() {
		this.description="no name";
	}
	public ItemDetails(String description) {
		super();
		this.description = description;
	}
	public String getName()
	{
		return description;
	}
	public void setName(String descriptionName) 
	{
		this.description = descriptionName;
	}
	
}
