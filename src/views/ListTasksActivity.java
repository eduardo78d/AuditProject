package views;

import java.util.ArrayList;
import java.util.List;

import models.AndroidTask;
import models.MySQLiteHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import audit.project.R;
import controllers.AppController;

public class ListTasksActivity extends Activity{
	
	private ListView listView;
	private List<AndroidTask> tasksList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listtask);
		
		this.listView = (ListView) findViewById(R.id.listView1);
		MySQLiteHelper dataBase = new MySQLiteHelper( AppController.context ); 
		
		tasksList =dataBase.getAllTask(); 
		toFillList();
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id){
				newTaskLayout( tasksList.get( position ) ); 
			}});
	}
	
	private void newTaskLayout(AndroidTask task){
		Intent intent = new Intent(this, TaskActivity.class);
		Bundle b = new Bundle();
		b.putInt("idObject", task.getId() ); 
		intent.putExtras(b); 
		startActivity(intent);
		finish();
		
	}
	
	
	public void toFillList(){
		ArrayList<String> valuesList = new ArrayList<String>();
		
		for(int x=0; x< tasksList.size(); x++){
			valuesList.add(tasksList.get(x).getNameTask() ); }
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, valuesList);
		listView.setAdapter(adapter);
	
	}
	
	
	
	
}

