package controllers.wifii;

import views.MainActivity;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GPS extends Service implements LocationListener{
	
	Context context;
	MainActivity mainActivity; 
	TextView textView;
	Location location;
	LocationManager locationManager;
	double longitud;
	double latitud;
	boolean gpsActivo;
	
	/*
	 * Ejemplo para instanciar esta clase y hacer uso de sus métodos (Supongase que se instancía desde una
	   clase que extiende de Activity):
	 * GPS gps = new GPS(this.getApplicationContext());
	 * Toast.makeText(getApplicationContext(), gps.getLatitude()+"", Toast.LENGTH_LONG).show();
	 * Toast.makeText(getApplicationContext(), gps.getLongitude()+"", Toast.LENGTH_LONG).show();
	 * */
	
	public GPS(){
		super();
		this.context = this.getApplicationContext();
	}
	
	public GPS(Context context){
		super();
		this.context = context;
		getLocation();
	}
	
	public void getLocation(){
			try{
				locationManager = (LocationManager)this.context.getSystemService(LOCATION_SERVICE);
				gpsActivo = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			}catch(Exception e){}
			if(gpsActivo){
				locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, this);
				location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
				latitud = location.getLatitude();
				longitud = location.getLongitude();
			}
	}
	
	public double getLatitude(){
		return latitud;
	}
	
	public double getLongitude(){
		return longitud;
	}
	
	@Override
	public void onLocationChanged(Location location) {
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(mainActivity.getApplicationContext(), "GPS Desactivado", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(mainActivity.getApplicationContext(), "GPS Activado", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
