package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

 
public class MySQLiteHelper extends SQLiteOpenHelper {
 
	private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "projectV1";
 
    private final String TABLE_PROCESSES = "processes";
    private final String TABLE_TASKS = "tasks";
    
    //Columns
    private final String key_id = "id";
    private final String processName = "processName";
    private final String PID = "PID";
    private final String UID = "UID";
    private final String date = "date";
    
    private final String taskName = "taskName";
    
    
    private final String[] COLUMNS_PROCESSES = {this.key_id, this.processName, this.PID, this.UID, this.date};
    private final String [] COLUMNS_TASKS = {this.key_id, this.taskName, this.date};
   
    
    public MySQLiteHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        
    	db.execSQL(CREATE_PROCESSES_TABLE());
    	db.execSQL(CREATE_VERSIONS_TABLE());
    	db.execSQL(CREATE_TASKS_TABLE());
    	
    }
 
    private String CREATE_PROCESSES_TABLE(){
    	  return  "CREATE TABLE processes ( " +
                  "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                  "processName TEXT, "+   "PID TEXT, "+   "UID TEXT, "+ "date TEXT )";
    } 
    
    private String CREATE_VERSIONS_TABLE(){
  	  		return  "CREATE TABLE versions ( " +
  	  				"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
  	  				"version TEXT, "+ "date TEXT )";
    }
    
    private String CREATE_TASKS_TABLE(){
	  		return  "CREATE TABLE tasks ( " +
	  				"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
	  				"taskName TEXT, "+ "date TEXT )";
    }	 

    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS logs");
        this.onCreate(db);
    }
    
    public void addProcess(AndroidProcess log){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	try{
    	//values.put(this.key_id, log.getId() ); 
        values.put(this.processName, log.getProcessName()); 
        values.put(this.PID, log.getPID()); 
        values.put(this.UID, log.getUID());
        values.put(this.date, log.getDate());
        
        	db.insert(TABLE_PROCESSES, null, values);
        	db.close();
        }catch (Exception e){
        	System.err.println("Un problema acaba de ocurrir");
        	System.err.println(e);
        }
    }
    
    public void addTask(AndroidTask task){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	try{
    		//values.put(this.key_id, log.getId() ); 
    		values.put(this.taskName, task.getNameTask()); 
    		values.put(this.date, task.getDate()); 
       
        	db.insert(TABLE_TASKS, null, values);
        	db.close();
        }catch (Exception e){
        	System.err.println("Un problema acaba de ocurrir");
        	System.err.println(e);
        }
    }

    
    public AndroidProcess getProcess(int id){
    	
    	SQLiteDatabase db =  this.getReadableDatabase();
    	Cursor myCursor = db.query( TABLE_PROCESSES,
    			COLUMNS_PROCESSES, " id = ?", new String[] {String.valueOf(id)},
    			null, null,null,null);
    	
    	if(myCursor !=  null)
    		myCursor.moveToFirst();
    	
    	AndroidProcess myProcess = new AndroidProcess();
    	myProcess.setId( Integer.parseInt(myCursor.getString(0) ) );
    	myProcess.setNameProcess(myCursor.getString(1));
    	myProcess.setPID(myCursor.getString(2));
    	myProcess.setUID(myCursor.getString(3));
    	myProcess.setDate(myCursor.getString(4));
    	
    	return myProcess;
    }
    
    public AndroidTask getTask(int id){
    	
    	SQLiteDatabase db =  this.getReadableDatabase();
    	Cursor myCursor = db.query( TABLE_TASKS,
    			COLUMNS_TASKS, " id = ?", new String[] {String.valueOf(id)},
    			null, null,null,null);
    	
    	if(myCursor !=  null)
    		myCursor.moveToFirst();
    	
    	AndroidTask myTask = new AndroidTask();
    	myTask.setId( Integer.parseInt(myCursor.getString(0) ) );
    	myTask.setNameTask(myCursor.getString(1));
    	myTask.setDate(myCursor.getString(2));
  
    	return myTask;
    }
    
    public int updateProcess(AndroidProcess Process){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	
    	values.put(this.key_id, Process.getId() ); 
    	values.put(this.processName, Process.getProcessName()); 
    	values.put(this.PID, Process.getPID()); 
    	values.put(this.UID, Process.getUID());
    	values.put(this.date, Process.getDate());
    	
    	int i = db.update(TABLE_PROCESSES, values, key_id+ " = ?", new String[] {String.valueOf(Process.getId())});
    	db.close();
    	return i;
    }
    
    public int updateTask(AndroidTask task){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	
    	values.put(this.key_id, task.getId() ); 
    	values.put(this.processName, task.getNameTask()); 
    	values.put(this.PID, task.getDate()); 
    	
    	int i = db.update(TABLE_TASKS, values, key_id+ " = ?", new String[] {String.valueOf(task.getId())});
    	db.close();
    	return i;
    }
    
    public boolean deleteTableProcess(){
    	try{
    		SQLiteDatabase db = this.getWritableDatabase();
    		db.delete(TABLE_PROCESSES, null, null);
    		return true;
    	}catch(Exception e){
    		return false;
    	}
    }
    
    public boolean deleteTableTask(){
    	try{
    		SQLiteDatabase db = this.getWritableDatabase();
    		db.delete(TABLE_TASKS, null, null);
    		return true;
    	}catch(Exception e){
    		return false;
    	}
    }
    
    public boolean deleteProcess(AndroidProcess Process){
    	SQLiteDatabase db = this.getWritableDatabase();
    	try{
    		db.delete(TABLE_PROCESSES, key_id+  " = ?", new String[] {String.valueOf(Process.getId())});
    		db.close();
        	return true;
    	}catch(Exception e){
    		return false;
    	}
    }
	
    public boolean deleteTask(AndroidTask task){
    	SQLiteDatabase db = this.getWritableDatabase();
    	try{
    		db.delete(TABLE_TASKS, key_id+  " = ?", new String[] {String.valueOf(task.getId())});
    		db.close();
        	return true;
    	}catch(Exception e){
    		return false;
    	}
    }
    
    public List<AndroidProcess> getAllProcess(){
    	List<AndroidProcess> processList = new LinkedList<AndroidProcess>();
    	String query = "SELECT * FROM "+ this.TABLE_PROCESSES;
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor myCursor = db.rawQuery(query,null );
    	AndroidProcess myProcess = null;
    	
    	if(myCursor.moveToFirst()){
    		do{
    			myProcess = new AndroidProcess();
    			myProcess.setId( Integer.parseInt(myCursor.getString(0) ) );
    			myProcess.setNameProcess(myCursor.getString(1));
    			myProcess.setPID(myCursor.getString(2));
    			myProcess.setUID(myCursor.getString(3));
    			myProcess.setDate(myCursor.getString(4));
    	    	
    			processList.add(myProcess);
    		}while(myCursor.moveToNext());
    	}
    	return processList;
    }
    
    public List<AndroidTask> getAllTask(){
    	List<AndroidTask> taskList = new LinkedList<AndroidTask>();
    	String query = "SELECT * FROM "+ this.TABLE_TASKS;
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor myCursor = db.rawQuery(query,null );
    	AndroidTask myTaks = null;
    	
    	if(myCursor.moveToFirst()){
    		do{
    			myTaks = new AndroidTask();
    			myTaks.setId( Integer.parseInt(myCursor.getString(0) ) );
    			myTaks.setNameTask(myCursor.getString(1));
    			myTaks.setDate(myCursor.getString(2));
    			
    	    	taskList.add(myTaks);
    		}while(myCursor.moveToNext());
    	}
    	return taskList;
    }
    

    public boolean backupDatabase(Context myContext){
    	try{
    		
    		FileInputStream fis = new FileInputStream( myContext.getDatabasePath(DATABASE_NAME) );
            OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+"/MYDB.txt");

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer))>0){
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            fis.close();
            
            return true;
    	}catch(Exception ex){
    		return false;
    	}
        
    }
    
    
    public void migration(){
    	SQLiteDatabase db = this.getWritableDatabase();
    	this.onUpgrade(db, 1, 1);
    }
    
    public int getSize(){
    	SQLiteDatabase db = this.getWritableDatabase();
    	long size = new File(db.getPath()).length();
    	return (int) size; 
    	
    }
}