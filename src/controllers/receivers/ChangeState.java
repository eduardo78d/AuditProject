package controllers.receivers;

import controllers.AppController;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class ChangeState extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		//http://developer.android.com/reference/android/content/Intent.html
		
		//Class that allows us when the device change him state
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
			//When the device off him screen the flags that control the Audit change him values for false
			//This Stop All the project
			AppController.flagThreadProcesses = false;
        	AppController.flagThreadTasks = false;
        	
		}else if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
			//Else if the User are present the application start a new Service 
	    	Intent serviceIntent = new Intent();
			serviceIntent.setAction("ServiceAuditor");
			context.startService(serviceIntent);
		}
		
		
	
	
	}
	
} 