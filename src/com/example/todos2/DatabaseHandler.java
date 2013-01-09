package com.example.todos2;


import java.util.LinkedList;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper
{
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 5;
  
    // Database Name
    private static final String DATABASE_NAME = "taskManager6";
 
    // Contacts table name
    private static final String TABLE_CONTACTS = "tasks5";
 
    // Contacts Table Columns names
    private static final String KEY_TASK = "task";
    private static final String KEY_TOPIC = "topic";
    private static final String KEY_DONE = "done";
    private static final String KEY_ID = "id";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
 

	// Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("+ KEY_TASK + " TEXT,"+ KEY_TOPIC + " TEXT,"+ KEY_DONE+" TEXT,"+KEY_ID+" TEXT"+")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
 
        // Create tables again
        onCreate(db);
    }
    
    // Adding new task
public void addTask(ItemDetails item) {
	 
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(KEY_TASK, item.getName()); // task detail
    values.put(KEY_TOPIC, item.getTopic()); // topic detail
    values.put(KEY_DONE, String.valueOf(item.getDone()));
    values.put(KEY_ID, String.valueOf(item.getId()));
   
    
 
    // Inserting Row
    db.insert(TABLE_CONTACTS, null, values);
    db.close(); // Closing database connection
 
}

// Getting single contact
/* maybe need to change to int id*/
public ItemDetails getTask(String name) {
SQLiteDatabase db = this.getReadableDatabase();

Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_TASK,KEY_TOPIC,KEY_DONE,KEY_ID}, KEY_TASK + "=?",
        new String[] { name }, null, null,null,null);
if (cursor != null)
    cursor.moveToFirst();

ItemDetails item = new ItemDetails();
item.setName(cursor.getString(0));
item.setTopic(cursor.getString(1));
item.setDone(Integer.parseInt(cursor.getString(2)));
// return contact
cursor.close();
return item;
}

// Getting All Contacts
public LinkedList<ItemDetails> getAllTasks() {
	LinkedList<ItemDetails> taskList = new LinkedList<ItemDetails>();
// Select All Query
String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

SQLiteDatabase db = this.getWritableDatabase();
Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list

if (cursor.moveToFirst()) {
    do {
        ItemDetails item = new ItemDetails ();
        item.setName(cursor.getString(0));
        item.setTopic(cursor.getString(1));
        item.setDone(Integer.parseInt(cursor.getString(2)));
        item.setId(Integer.parseInt(cursor.getString(3)));
        System.out.println(item.getTopic()+","+item.getDone());
        // Adding contact to list
      taskList.add(item);
     
    } while (cursor.moveToNext());
}

cursor.close();
// return contact list
return taskList;
}
//Getting contacts Count
public int getTasksCount() {
    String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(countQuery, null);
    cursor.close();

    // return count
    return cursor.getCount();
}

// Updating single contact
public int updateTask(ItemDetails item) {
SQLiteDatabase db = this.getWritableDatabase();

ContentValues values = new ContentValues();
values.put(KEY_TASK, item.getName());
values.put(KEY_TOPIC, item.getTopic());
values.put(KEY_DONE, String.valueOf(item.getDone()));
values.put(KEY_ID, String.valueOf(item.getId()));

// updating row
return  db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",new String[] {String.valueOf(item.getId()) } );

}

// Deleting single contact
public void deleteTask(ItemDetails item) {
SQLiteDatabase db = this.getWritableDatabase();
db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] {String.valueOf(item.getId() )});
db.close();
}
}