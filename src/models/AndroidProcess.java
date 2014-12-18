package models;



public class AndroidProcess extends Log{
	
	private String processName;
	private String PID;
	private String UID;
	
	
	public AndroidProcess(){}
	
	public AndroidProcess(String processName, String PID, String UID, String date  ){
		this.setNameProcess(processName);
		this.setPID(PID);
		this.setUID(UID);
		this.setDate(date);
	}
	
	public void setNameProcess(String processName){
		this.processName = processName;
	}
	
	public String getProcessName(){
		return this.processName;
	}
	
	public void setPID(String PID){
		this.PID = PID;
	}
	
	public String getPID(){
		return this.PID;
	}
	
	public void setUID(String UID){
		this.UID = UID;
	}
	
	public String getUID(){
		return this.UID;
	}
	
	
	
	
}
