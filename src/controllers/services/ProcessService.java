package controllers.services;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import controllers.SweeperProcess;

public class ProcessService extends Service {
	
	public void onCreate(){
		super.onCreate();
	}
	
	public IBinder onBind(Intent arg0){
		return null;
	}
	
	public void onStart(Intent intent, int startId){
		Thread t = new Thread(new SweeperProcess());
		t.start();
		this.stopSelf();
	}
	
	public void onDestroy(){
		super.onDestroy();
	}
	
}