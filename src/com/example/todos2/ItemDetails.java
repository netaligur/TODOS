package com.example.todos2;

public class ItemDetails 
{
	
	private String description;
	private String topic;
	private int done=0; /* if done==0 then it's not done*/
	private int id=0; /*maybe it will be removed*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDone() {
		return done;
	}
	public void setDone(int done) {
		this.done = done;
	}
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
		this.done=0;
		this.id=item.id;
	}
	public ItemDetails() {
		this.description="no description";
		this.topic="no topic";
		this.done=0;
		
	}
	public ItemDetails(String description) {
		super();
		this.description = description;
		this.topic="no topic";
		this.done=0;
	}
	public ItemDetails(String description,String topic) {
		super();
		this.description = description;
		this.topic=topic;
		this.done=0;
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
