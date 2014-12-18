package controllers;

import java.util.List;
import android.app.ActivityManager.RunningAppProcessInfo;
import models.AndroidProcess;

public class SweeperProcess extends Sweeper implements Runnable{

	private List<RunningAppProcessInfo> curenteListProcessInfo;
	
	public SweeperProcess(){
		super();
		
		this.curenteListProcessInfo = this.myLocalActivityManager.getRunningAppProcesses();
	}
	
	public void run(){
		storeFirtsListProcesses(this.curenteListProcessInfo);
		while(AppController.flagThreadProcesses){
			this.checkCurrentProcess();
			this.sleepThisThread();	
		}
	}
	
	private void checkCurrentProcess(){
		if( this.myLocalActivityManager.getRunningAppProcesses().size() !=  this.curenteListProcessInfo.size() ){
			
			if(this.myLocalActivityManager.getRunningAppProcesses().size()  > this.curenteListProcessInfo.size()){ 
				addNewProcess( this.curenteListProcessInfo , this.myLocalActivityManager.getRunningAppProcesses(), 
								this.myLocalActivityManager.getRunningAppProcesses().size() - this.curenteListProcessInfo.size());
			}else{}
			
			this.curenteListProcessInfo = this.myLocalActivityManager.getRunningAppProcesses();
			
		}
	}
	
	private void addNewProcess(List<RunningAppProcessInfo> listOne, List<RunningAppProcessInfo> listTwo, int numberOfNewProcess){
		int count = 0;
		for(int x=listOne.size()-1; x > 0; x--){
			if(count == numberOfNewProcess)
				break;
			else{
				if( !listTwo.contains( listOne.get(x) ) ){
					this.storageProcess(listOne.get(x)); count++;
				}
			}
		} 
	}
	
	
	private void storeFirtsListProcesses(List<RunningAppProcessInfo> curenteListProcessInfo){
		
		for(int i = 0; i < curenteListProcessInfo.size(); i ++){
			this.storageProcess(curenteListProcessInfo.get(i) );
    	}
	}

	private void storageProcess(RunningAppProcessInfo currentProcess){
		this.dataBase.addProcess(new AndroidProcess( 
				currentProcess.processName,
				Integer.toString(currentProcess.pid),
				Integer.toString(currentProcess.uid),
				this.getCurrentDate()  
			));
	}
	
	
	
	
}
