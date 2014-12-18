package controllers.wifii;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import controllers.AppController;

import models.AndroidTask;


import models.AndroidProcess;
import models.AndroidTask;
import models.MySQLiteHelper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.widget.Toast;
import Decoder.BASE64Encoder;

@SuppressLint("NewApi")

public class BackUpNotifier extends AsyncTask<String, Integer, String>{
	
		private WifiManager wifi;
		private Context context;
		List<AndroidTask> tasks;
		List<AndroidProcess> precesses;
		
		public BackUpNotifier(Context context, Activity activity){
			this.wifi = (WifiManager)  context.getSystemService(context.WIFI_SERVICE);
			this.context = context;
			
			AppController app = new AppController();
		}
		
		public boolean testConnection(){
			if(this.wifi.isWifiEnabled()){
				if(this.wifi.getScanResults().size()>0 )//if((this.wifi.getScanResults().size()>0) &&  (!this.wifi.disconnect()) )
			    	return true;
			    else
			    	return false;
		    }else
		    	return false; //Please turn on the WiFii
		}

		public boolean doBackUp(String ip, String port, String task, String proc){
			
			MySQLiteHelper helper = new MySQLiteHelper(context);
			tasks = helper.getAllTask();
			precesses = helper.getAllProcess();
			
			
			for(int i = 0; i < tasks.size(); i++){
				task = task+"-" + "["+tasks.get(i).getId()+","+tasks.get(i).getNameTask()+","+tasks.get(i).getDate()+"]";
			}
			
			this.execute(ip, port, task);
			return false;
		}		
		
		@Override
		protected String doInBackground(String... params) {
			BufferedReader respuesta;
			final String HOST = params[0];
			final int puerto = Integer.parseInt(params[1]);
			String pedir = "peticion";
			String datos = params[2];//"El cosplay ( kosupure?), contracci�n de costume play,1 es un tipo de moda representativa, donde los participantes usan disfraces, accesorios y trajes que representan un sujeto espec�fico o una idea. Los cosplayers a menudo interact�an para crear una subcultura centrada en el juego de roles. Una definici�n m�s amplia del t�rmino  aplica a cualquier uso de disfraz de juegos roles fuera del escenario, independientemente de su contexto cultural. Las fuentes favoritas para esto incluyen c�mics, anime, manga y videojuegos.El cosplay tiene un enfoque cultural espec�fico dedicado a la representaci�n realista de una idea o un personaje propio de la ficci�n; puede tener distintas variantes seg�n la intenci�n y el contexto, normalmente haciendo una representaci�n f�sica y dram�tica de un personaje. Entre sus variantes se encuentran notablemente: la representaci�n de personajes antropomorfos, la adaptaci�n antropomorfa de personajes zoomorfos, el cross-dressing, la representaci�n de los roles de g�nero opuestos y el car�cter er�tico.2";
			Socket so;
			DataOutputStream mensaje;
			PrintWriter envio;
			DataInputStream entrada;
			
			
			
			
			try{
				so = new Socket( HOST , puerto );
				envio = new PrintWriter(so.getOutputStream(), true);
				respuesta = new BufferedReader(new InputStreamReader(so.getInputStream()));
				envio.println(pedir);
				//encriptar datos
				
				
				
				
				ArrayList<String> strings = new ArrayList<String>();
				
				int index = 0;
				while (index < datos.length()) {
				    strings.add(datos.substring(index, Math.min(index + 30,datos.length())));
				    index += 30;
				}
				System.out.println(strings.size());
				envio.println(strings.size()+"");
				
				for(int i = 0; i < strings.size(); i++){
					System.out.println(strings.get(i));
					String datoCifrado = BackUpNotifier.cifrar(strings.get(i));
					envio.println(datoCifrado);
					System.out.println(datoCifrado);
					//System.out.println("........................................................................");
				}
			
				so.close();
			}catch(Exception e){
				System.out.println(e);
			}
			return null;
		}
		
		protected void onPostExecute(String result) {
			System.out.println("BackUp Exitoso Dos");
		}
		

		public static String  cifrar(String sinCifrar) throws Exception {
			final byte[] bytes = sinCifrar.getBytes("UTF-8");
			final Cipher aes = obtieneCipher(true);
			final byte[] cifrado = aes.doFinal(bytes);
			String encText = new BASE64Encoder().encode(cifrado);
			return encText;
		}

		private static Cipher obtieneCipher(boolean paraCifrar) throws Exception {
			final String frase = "upchiapas";
			final MessageDigest digest = MessageDigest.getInstance("SHA");
			digest.update(frase.getBytes("UTF-8"));
			final SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
		 
			final Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
			if (paraCifrar) {
				aes.init(Cipher.ENCRYPT_MODE, key);
			} else {
				aes.init(Cipher.DECRYPT_MODE, key);
			}
		 
			return aes;
		}
		private String writeUsingNormalOperation(AndroidTask task) {
		    String format =
		            "<?xml version='1.0' encoding='UTF-8'?>" +
		            "<record>" +
		            "   <study id='%d'>" +
		            "       <topic>%s</topic>" +
		            "       <content>%s</content>" +
		            "       <author>%s</author>" +
		            "       <date>%s</date>" +
		            "   </study>" +
		            "</record>";
		    //return String.format(format, study.mId, study.mTopic, study.mContent, study.mAuthor, study.mDate);
		    return "";

		}
}
