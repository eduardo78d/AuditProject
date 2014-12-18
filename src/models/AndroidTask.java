package models;

public class AndroidTask extends Log{

	private String nameTask;
	
	public AndroidTask(){}
	
	public AndroidTask(String name,String date){
		this.setNameTask(name);
		this.setDate(date);
	}

	public String getNameTask() {
		return nameTask;
	}

	public void setNameTask(String nameTask) {
		this.nameTask = nameTask;
	}


	
}
