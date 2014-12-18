package controllers.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceBoot extends BroadcastReceiver{

	public void onReceive(Context context, Intent intent) {
		//When the Device Reboot or start is triggered a new Service
		Intent serviceIntent = new Intent();
		serviceIntent.setAction("ServiceAuditor");
		context.startService(serviceIntent);
	}

}