package controllers.receivers;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import controllers.wifii.BackUpNotifier;
import controllers.wifii.PushNotification;
import controllers.AppController;

public class AlarmReceiver extends BroadcastReceiver
{
      
    @Override
    public void onReceive(Context context, Intent intent)
    {
    	//Allows us send a push notification for do the back up
    	
    	//The message will be repeat if the backup flag is false or the number of the repeat is less that 5
    	if ( (AppController.flagBackUp== false) && (AppController.numberOfRepeat < 5 ) ){
    		BackUpNotifier backup = new BackUpNotifier(AppController.context , null);
    		PushNotification pN = new PushNotification();
    		
    		if(backup.testConnection()) //If the device is connected to WIFI send a special notification
    			pN.showPushNotification( "BackUp Available!" );
    		else
    			pN.showPushNotification( "Is necessary to connect to a network to perform BackUp!" );
    		
    		AppController.numberOfRepeat ++; //increase the variable for the flag
    	}else
    		PendingIntent.getBroadcast(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT).cancel(); //Stop the AlarmService
    }
    
}
