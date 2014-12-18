package views;


import models.MySQLiteHelper;
import controllers.AppController;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import audit.project.R;
import models.AndroidProcess;

public class ProcessActivity extends Activity{
	
	private TextView name;
	private TextView PID;
	private TextView UID;
	private TextView Date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_process);
		
		this.name = (TextView) this.findViewById(R.id.name);
		this.PID = (TextView) this.findViewById(R.id.PID);
		this.UID = (TextView) this.findViewById(R.id.UID);
		this.Date = (TextView) this.findViewById(R.id.Date);
		
		Bundle b = getIntent().getExtras();
		
		MySQLiteHelper dataBase = new MySQLiteHelper( AppController.context );
		this.showProcess( dataBase.getProcess( b.getInt("idObject") ) );
	}
	
	private void showProcess(AndroidProcess log){
		this.name.setText("Process Name : " +log.getProcessName());
		this.PID.setText("PID : "+ log.getPID());
		this.UID.setText("UID : "+log.getUID());
		this.Date.setText("Date : "+log.getDate());
		
	}
	
	
	
	
}
