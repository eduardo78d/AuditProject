package controllers;


import java.util.Calendar;

import controllers.receivers.AlarmReceiver;
import controllers.receivers.ChangeState;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
public class AppController extends Application{
	
	//Objects for operating the sweeper
	public static ActivityManager activityManager;
	public static Context context;
	
	
	//Variables for operating Sweeper
	public static boolean flagThreadTasks;
	public static boolean flagThreadProcesses;
	
	//Flag for know if the BackUp was sent
	public static boolean flagBackUp;
	
	public static int numberOfRepeat = 0;

	//Variables for the Alarm 
	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;
	
	public void onCreate(){
		super.onCreate();
		
		//instantiate new objects (statics) for the Audit Project , this Objects are necessary for get 
		//The processes and the tasks
		this.makeStatic();
		this.makeContext(); 
		
		this.registerSleepMode();
		this.setClock();
	}
	
	private void makeStatic(){
		AppController.activityManager = (ActivityManager) this.getSystemService( ACTIVITY_SERVICE );
	}
	
	private void makeContext(){
		AppController.context = this.getApplicationContext();
	}
	
	public void startFlags(){
		//Change states to notify the device that the project is running
		AppController.flagThreadProcesses =true;
		AppController.flagThreadTasks = true;
		AppController.flagBackUp=false;
	}
	
	private void setClock(){
		//set an alarm that will be executed 7:30 PM
		alarmMgr = (AlarmManager) getSystemService( ALARM_SERVICE );
		
		Intent AlarmIntent = new Intent(this, AlarmReceiver.class);
		alarmIntent = PendingIntent.getBroadcast(this,0,AlarmIntent, 0);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, 19);
		calendar.set(Calendar.MINUTE, 30);

		alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
		        1000 * 60 * 30, alarmIntent);//Set a repeat Alarm
	
	}
	
	private void registerSleepMode(){
		//register all the change states that triggers something
		BroadcastReceiver myReceiver = new ChangeState();
		
		IntentFilter screenStateFilter = new IntentFilter();
		
		screenStateFilter.addAction(Intent.ACTION_USER_PRESENT);
		screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
		screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
		registerReceiver(myReceiver, screenStateFilter);        
		  
	}
	
}

