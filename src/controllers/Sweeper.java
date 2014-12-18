package controllers;

import java.util.Date;

import models.MySQLiteHelper;

import android.app.ActivityManager;

class Sweeper{

	public ActivityManager myLocalActivityManager;
	public MySQLiteHelper dataBase;
	
	public int auditTime;
	
	public Sweeper(){
		this.auditTime = 500;
		this.myLocalActivityManager = AppController.activityManager;
		this.dataBase = new MySQLiteHelper( AppController.context );
	} 
	//Get the date
	public String getCurrentDate(){
		Date now = new Date();
		return now.toString();
	}
	
	//Sleep the Thread
	public void sleepThisThread(){
		try {
			Thread.sleep(this.auditTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}