package views;


import models.MySQLiteHelper;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import audit.project.R;
import controllers.*;

import controllers.services.*;
import controllers.wifii.BackUpNotifier;

import controllers.services.ServiceAuditor;



public class MainActivity extends ActionBarActivity {
	
	public MySQLiteHelper dataBase;
	
	private Button buttonListProcesses;
	private Button buttonListTasks;

	private Button exportDataBase;


	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.buttonListProcesses = (Button) this.findViewById(R.id.buttonListProcesses);
		this.buttonListProcesses.setOnClickListener(btnListenerShowListProcesses);
		
		this.buttonListTasks = (Button) this.findViewById(R.id.buttonListTasks);
		this.buttonListTasks.setOnClickListener(btnListenerShowListTasks);
		
		this.dataBase = new MySQLiteHelper( AppController.context );
		
	}
		
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	/*
	public void onDestroy(){
        super.onDestroy();
        startService(new Intent(this, ServiceAuditor.class));
	}
	*/
	private OnClickListener btnListenerShowListProcesses = new OnClickListener(){
			public void onClick(View v){   
				showSizeDataBase();
			} 
	};  
	private void showSizeDataBase(){
		Toast.makeText(this, "" +this.dataBase.getSize(), Toast.LENGTH_LONG ).show();
		BackUpNotifier backup = new BackUpNotifier(this.getApplicationContext(), this);
		backup.doBackUp("192.168.0.5", "3000", "", "");
	}
	
	private void newListProcesssesLayout(){
		Intent act = new Intent(this, ListProcessesActivity.class);
		
		startActivity(act);
	}
	
	
	private OnClickListener btnListenerShowListTasks = new OnClickListener(){
		public void onClick(View v){   
			newListTasksLayout();
		} 
	};
	
	private void newListTasksLayout(){
		Intent act = new Intent(this, ListTasksActivity.class);
		startActivity(act);
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        
		case R.id.about:
        	Toast.makeText(this, "About us", Toast.LENGTH_LONG ).show();
        	return true;
        
		case R.id.stopservices:
			if (item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
			stopAllServices();
			Toast.makeText(this, "All Services were Stopped", Toast.LENGTH_LONG ).show();
			return true;
		
		/*
		case R.id.toauditprocesses:
			if (item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
			return true;
			
		case R.id.toaudittasks:
			if (item.isChecked()) item.setChecked(false);
            else item.setChecked(true);
			return true;
		*/	
		case R.id.dropdatabases:
			if( (this.dropTableProcesses())  &&  (this.dropTableTasks()) )
				Toast.makeText(this, "All tables were dropped", Toast.LENGTH_LONG ).show();
			else
				Toast.makeText(this, "Problems", Toast.LENGTH_LONG ).show();
			return true;
			
		default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	private boolean dropTableTasks(){
		return this.dataBase.deleteTableTask();
	}
	
	private boolean dropTableProcesses(){
		return this.dataBase.deleteTableProcess();
	}
	
	private void stopAllServices(){
		AppController.flagThreadTasks = false;
		AppController.flagThreadProcesses = false;
	}
	
	
}
