package views;

import models.AndroidTask;
import models.MySQLiteHelper;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import audit.project.R;
import controllers.AppController;

public class TaskActivity extends Activity{
	
	private TextView ID;
	private TextView Name;
	private TextView Date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		
		this.Name = (TextView) this.findViewById(R.id.Name);
		this.ID = (TextView) this.findViewById(R.id.ID);
		this.Date = (TextView) this.findViewById(R.id.Date);
		
		Bundle b = getIntent().getExtras();
		
		MySQLiteHelper dataBase = new MySQLiteHelper( AppController.context );
		this.showTask( dataBase.getTask( b.getInt("idObject") ) );
	}
	
	private void showTask(AndroidTask task){
		this.ID.setText("ID : "+ task.getId());
		this.Name.setText("Task Name : " +task.getNameTask());
		this.Date.setText("Date : "+task.getDate());
		
	}
}

