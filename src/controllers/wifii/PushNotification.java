package controllers.wifii;

import views.BackUpActivity;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import audit.project.R;
import controllers.AppController;
import android.os.Vibrator;

public class PushNotification {

	public PushNotification(){
		
	}
	
	@SuppressLint("NewApi")
	public void showPushNotification(String Message){
		Vibrator vibrator = (Vibrator) AppController.context.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(1000);
		  
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(AppController.context)
		.setSmallIcon(R.drawable.ic_action_refresh)
        .setContentTitle("BackUp")
        .setContentText(Message);

		Intent resultIntent = new Intent(AppController.context, BackUpActivity.class);
		mBuilder.setAutoCancel(true);
		
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(AppController.context);
		
		stackBuilder.addParentStack(BackUpActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		
		PendingIntent resultPendingIntent = 
				stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = 
				(NotificationManager) AppController.context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(001, mBuilder.build());
		
	}
}
