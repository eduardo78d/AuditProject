package views;

import models.MySQLiteHelper;
import controllers.AppController;
import controllers.wifii.BackUpNotifier;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import audit.project.R;

public class BackUpActivity extends Activity{
	
	private Button backUpDataBase;
	private EditText ipText;
	private TextView  text;
	
	public MySQLiteHelper dataBase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_backup);
		
		this.dataBase = new MySQLiteHelper( AppController.context );
		
		this.backUpDataBase = (Button) this.findViewById(R.id.buttonDoBackUp);
		this.backUpDataBase.setOnClickListener(export);
		
		this.ipText = (EditText) this.findViewById(R.id.ipText);
		
		this.text = (TextView)this.findViewById(R.id.textIp);
		this.ipText.setText("192.168.0.1");
	}
	
	private OnClickListener export = new OnClickListener(){
		public void onClick(View v){
			check();
		} 
	};
	
	private void check(){
		String p = ".";
		if ( (this.ipText.getText().toString().contains(p) ) && (this.countP(this.ipText.getText().toString()) ==3) )
			this.backUp(this.ipText.getText().toString(), "3000");
		else{
			this.ipText.setText("");
			Toast.makeText(this, "Ip incorrecta " , Toast.LENGTH_LONG ).show();
		}
			
	}
	private int countP(String s) {
		return s.length() - s.replace(".", "").length();
	}

	
	private void backUp(String ip, String puerto){
		BackUpNotifier backup = new BackUpNotifier(this.getApplicationContext(), this);
		backup.doBackUp(ip, puerto, "", "");
		AppController.flagBackUp = true;
		this.dataBase.deleteTableTask();
	}

}
