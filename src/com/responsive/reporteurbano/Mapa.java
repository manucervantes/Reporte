package com.responsive.reporteurbano;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseObject;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class Mapa extends FragmentActivity{
	private LocationManager locManager;
	private LocationListener locListener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		//a partir de aqui comenzar a llamar a los layouts 		
		current_location();
		
		
		
		return true;
	}
	
	public  void  current_location()
	{
		
		
		comenzarLocalizacion();
	//	double location;
		GoogleMap mapa= ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap(); mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		locManager =  (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location loc =  locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       
    	LatLng lugar = new LatLng(loc.getLatitude(),loc.getLongitude());
    	CameraPosition camPos = new CameraPosition.Builder().target(lugar)   
    	        .zoom(18)          //Establecemos el zoom en 18
    	        .bearing(0)       //Establecemos la orientación con el noreste arriba
    	        .tilt(0)         //Bajamos el punto de vista de la cámara 70 grados
    	        .build();
    	mapa.addMarker(new MarkerOptions().position(lugar).title("Posición Actual"));
   	  	CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
    	mapa.animateCamera(camUpd3);
    	double latitud = loc.getLatitude();
    	double longitud = loc.getLongitude();
    	
    	String param_latitud =  String.valueOf(latitud);
    	String param_longitud =  String.valueOf(longitud);
    	//Construccion del objeto para parseo
		ParseObject ReporteUrbano_location  = new ParseObject("ReporteUrbano_location");
		//insercion en parse
		ReporteUrbano_location.put("Latitud",param_latitud);
		ReporteUrbano_location.put("longitud",param_longitud);
    	ReporteUrbano_location.saveInBackground();
    	
    	String a=ReporteUrbano_location.getString("Latitud");
    	String b=ReporteUrbano_location.getString("longitud");
    	
    	
    	double x = Double.valueOf(a);
    	double p= Double.valueOf(b);
    	
    	LatLng lugar2 = new LatLng(x,p);
     	mapa.addMarker(new MarkerOptions().position(lugar2).title(String.valueOf("Oposition")));
   
    	
		
	}
	
	private void comenzarLocalizacion()
    {
    	//Obtiene referencia de locacion
    	locManager =  (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	
    	//Obtiene Ultima Ubicacion
    	//Location loc =  locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	   	
    	//Nos registramos para recibir actualizaciones de la posici�n
    	locListener = new LocationListener() {
	    	public void onLocationChanged(Location location) {
	    		
	    	}
	    	public void onProviderDisabled(String provider){
	    		
	    	}
	    	public void onProviderEnabled(String provider){
	    	
	    	}
	    	public void onStatusChanged(String provider, int status, Bundle extras){
	    	
	    		
	    	}
    	};
    	
    	locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, locListener);
    }


}
