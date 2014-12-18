package views;

import java.util.ArrayList;
import java.util.List;

import models.AndroidProcess;
import models.MySQLiteHelper;

import controllers.AppController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import audit.project.R;



public class ListProcessesActivity extends Activity{
	
	private ListView listView;
	private List<AndroidProcess> processesList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listprocesses);
		
		this.listView = (ListView) findViewById(R.id.listView1);
		MySQLiteHelper dataBase = new MySQLiteHelper( AppController.context ); 
		
		processesList =dataBase.getAllProcess(); 
		toFillList();
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id){
				newProcessLayout( processesList.get( position ) ); 
			}});
	}
	
	private void newProcessLayout(AndroidProcess process){
		Intent intent = new Intent(this, ProcessActivity.class);
		Bundle b = new Bundle();
		b.putInt("idObject", process.getId() ); 
		intent.putExtras(b); 
		startActivity(intent);
		finish();
	}
	
	public void toFillList(){
		ArrayList<String> valuesList = new ArrayList<String>();
		
		for(int x=0; x< processesList.size(); x++){
			valuesList.add(processesList.get(x).getProcessName() ); }
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, valuesList);
		listView.setAdapter(adapter);
	
	}
	
	
	
	
}
