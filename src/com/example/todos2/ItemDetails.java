package com.example.todos2;

public class ItemDetails 
{
	
	private String description;
	private String topic;
	
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public ItemDetails (ItemDetails item)
	{
		this.description=item.getName();
		this.topic=item.getTopic();
	}
	public ItemDetails() {
		this.description="no description";
		this.topic="no topic";
	}
	public ItemDetails(String description) {
		super();
		this.description = description;
		this.topic="no topic";
	}
	public ItemDetails(String description,String topic) {
		super();
		this.description = description;
		this.topic=topic;
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
