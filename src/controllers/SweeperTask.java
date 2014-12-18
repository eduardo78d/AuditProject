package controllers;

import android.app.ActivityManager.RunningTaskInfo;
import models.AndroidTask;


public class SweeperTask extends Sweeper implements Runnable{
	
	private String lastProcess = "";
	private final int numberOfTasks = 1;
	
	public SweeperTask(){
		super();
	}
	
	public void run(){
		while(AppController.flagThreadTasks){
			this.chechCurrentTask();
			this.sleepThisThread();
		}
	}
	
	private void chechCurrentTask(){
		//Get the last Task open , if this task is new add the new task to DB
		RunningTaskInfo task = this.myLocalActivityManager.getRunningTasks(this.numberOfTasks).get(0);
		if(!lastProcess.equals( task.baseActivity.toShortString() )){
			storageTask(task);
			lastProcess = task.baseActivity.toShortString();
		}
	}
	
	private void storageTask(RunningTaskInfo task){
		//Save the new Task
		this.dataBase.addTask(new AndroidTask(
				task.baseActivity.toShortString(),
				this.getCurrentDate()
				));
	}

}
