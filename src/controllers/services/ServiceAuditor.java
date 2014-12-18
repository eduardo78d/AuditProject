package controllers.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import controllers.*;


public class ServiceAuditor extends Service {
		
	/*The Main Service, in this service is triggered all the Threads for audit all 
	 *the processes and tasks
	 **/
	public void onCreate(){
		super.onCreate();
	}
	
	public IBinder onBind(Intent arg0){
		return null;
	}
	
	public void onStart(Intent intent, int startId){
		//For begin a new Thread is necessary Instantiate new objects 
		AppController myAppController = new AppController(); //AppController has a statics variables
		myAppController.startFlags(); //change to true all flags
		
		this.startSweeperTask();
		this.startSweeperProcess();
		//this.stopSelf();
	}
	
	private void startSweeperTask(){
		//Trigger the Sweeper Task
		Thread task = new Thread(new SweeperTask());
		task.start();
	}
	
	private void startSweeperProcess(){
		//Trigger the Sweeper Task
		//Thread process = new Thread(new SweeperProcess());
		//process.start();
	}
	
	public void onDestroy(){
		super.onDestroy();
		Toast.makeText(this, "The Auditor has begun!!!", Toast.LENGTH_LONG ).show();
	}
	
}