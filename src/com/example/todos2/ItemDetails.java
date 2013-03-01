/*
 * @ItemDetails        1.0 2013/02/27	
 *
 * Copyright 2013 Netali & Nadav, Inc. Neatli Gur & Nadav Taoz All Rights Reserved.
 * 
 * This software is the proprietary information of Netali and Nadav- Shenkar College of Engineering and Design
 */

package com.example.todos2;

import co.il.shenkar.tasknoid.R;

/**
 * this class represents an item on the
 * list view and each item is a task.
 * that includes all task attributes.
 */

public  class ItemDetails 
{	
	private String 			description			; // Description of the task	
	private String 			topic				; // Title \ Topic
	private int 			done=0				; // if done==0 then it's not done
	private int 			id=0				; // Task's id
	private int 			year				; // Year
	private int				month				; // Month
	private int 			day					; // Day
	private int 			hour				; // Hour
	private int 			minute				; // Minute
	private String 			address				; // Address \ Location

	public String getAddress()
	{
		if (address==null)						  // No location = returns default location
			return "No Location Was Entered";
		else
		return  address ;		
	}
	/* Setters and Getters to the variables of the class */
	public void setAddress(String address)
	{
		this.address = address;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
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
	public String getName()
	{
		return description;
	}
	public void setName(String descriptionName) 
	{
		this.description = descriptionName;
	}
	/* Constructors - Copy Comstructor */
	public ItemDetails (ItemDetails item)
	{
		this.description=item.getName();
		this.topic=item.getTopic();
		this.done=0;
		this.id=item.id;
		this.day=item.day;
		this.hour=item.hour;
		this.minute=item.minute;
		this.month=item.month;
		this.year=item.year;
		this.address=item.address;
	}
	/* Default Constructor */
	public ItemDetails() {
		this.description="no description";
		this.topic="no topic";
		this.done=0;
		this.day=0;
		this.hour=0;
		this.minute=0;
		this.month=0;
		this.year=0;
		this.address="no address";
	}
	/* Constructor from string */
	public ItemDetails(String description) {
		super();
		this.description = description;
		this.topic="no topic";
		this.done=0;
		this.day=0;
		this.hour=0;
		this.minute=0;
		this.month=0;
		this.year=0;
		this.address="no address";
	}
	/* Half Constructor */
	public ItemDetails(String description,String topic) {
		super();
		this.description = description;
		this.topic=topic;
		this.done=0;
		this.day=0;
		this.hour=0;
		this.minute=0;
		this.month=0;
		this.year=0;
		this.address="no address";
	}
	/* Print methods */
	@Override
	public String toString() {
		if (day==0 && month==0 && year==0)
			return "Alarmed to: "+"No Alarm Was Set";
		return "Alarmed to: " + day + "/" + month + "/"+ year + ",At  " + hour + ":" + minute;
	}
	public String toStringDialogAlarm() 
	{
		if (day==0 && month==0 && year==0)
			return "No Alarm Was Set";
		return day + "/" + month + "/"+ year + ", At  " + hour + ":" + minute ;
	}
	public String toStringAddress() 
	{
		if (address==null)
			return "No Location Was Entered";
		else
		return address ;
	}	
}
