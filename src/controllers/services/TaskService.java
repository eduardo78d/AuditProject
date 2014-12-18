package controllers.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import controllers.SweeperTask;

public class TaskService extends Service {

	public void onCreate(){
		super.onCreate();
	}
	
	public IBinder onBind(Intent arg0){
		return null;
	}
	
	public void onStart(Intent intent, int startId){
		Thread t = new Thread(new SweeperTask());
		t.start();
		this.stopSelf();
	}
	
	public void onDestroy(){
		super.onDestroy();
	}
}