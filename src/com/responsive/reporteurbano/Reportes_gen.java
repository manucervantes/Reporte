package com.responsive.reporteurbano;


import java.util.List;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Reportes_gen extends  FragmentActivity {
	private LocationManager locManager;
	private LocationListener locListener;
	Button nuevo;
	EditText coment;
	ImageView reporte;
	
	
	private  OnClickListener Reportes = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(Reportes_gen.this,Reports.class);
			startActivity(intent);

		}
	};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reportes_gen);
		nuevo=(Button)findViewById(R.id.new_repo);
		nuevo.setOnClickListener(Reportes);
		coment=(EditText)findViewById(R.id.et_recib_details);

		
		Parse.initialize(this, "jOqflSShoZ4BT4AaUYyObmWIXV9vaIA4dXR2qGt3", "4bx1yxpFxDZGeE0GteBKw7y9msUR2DhlpvzIzCL8");
		ParseAnalytics.trackAppOpened(getIntent());
	
		
		
	
		
		if (ParseFacebookUtils.getSession()==null && ParseUser.getCurrentUser()==null)
		{
			mensaje_error_login();
						
		}
		
		
		try {
			current_location();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}
	
	

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public  void  current_location() throws ParseException
	{
		
		comenzarLocalizacion();
	//	double location;
		final GoogleMap mapa= ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap(); mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		locManager =  (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location loc =  locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       
    	LatLng lugar = new LatLng(loc.getLatitude(),loc.getLongitude());
    	CameraPosition camPos = new CameraPosition.Builder().target(lugar)   
    	        .zoom(15)          //Establecemos el zoom en 18
    	        .bearing(0)       //Establecemos la orientación con el noreste arriba
    	        .tilt(0)         //Bajamos el punto de vista de la cámara 70 grados
    	        .build();
    	mapa.addMarker(new MarkerOptions().position(lugar).title("Posición Actual"));
   	  	CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
    	mapa.animateCamera(camUpd3);
    	double latitud = loc.getLatitude();
    	double longitud = loc.getLongitude();

	
		
		ParseGeoPoint point = new ParseGeoPoint(latitud,longitud);
		ParseObject locs = new ParseObject("reportes");
		
		/*locs.put("location", point);
		locs.saveInBackground();*/
    	ParseQuery<ParseObject> query = ParseQuery.getQuery("reportes");
    	query.whereNear("location", point);
    	query.setLimit(10);
    	
    	query.orderByAscending("location");
    	query.findInBackground(new FindCallback<ParseObject>() {
    	
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				
				if (e==null)
					
				{
					ParseGeoPoint ubicaciones;
					
					for(int i =0;i< objects.size();i++)
					{
						ubicaciones = objects.get(i).getParseGeoPoint("location");
						Log.d("CHEK","asdasdas "+ ubicaciones);
						double lat=ubicaciones.getLatitude();
						double lng=ubicaciones.getLongitude();
						
						LatLng algo = new LatLng(lat,lng);
						mapa.addMarker(new MarkerOptions().position(algo).title("problema"+i));
						
						
					}
					
					Log.d("CHEK","asdasdas "+ objects);
					Log.d("CHEK","Contiene info");
					Log.d("Objeto", "tamaño de la lista "+objects.size());
					//mapa.addMarker(new MarkerOptions().position().title("Posición Actual"));
				
					
					}
				else
					Log.d("ERROR","No hay naaaa");
				
				// TODO Auto-generated method stub
				
			}  });
    	
    	
    	
/*CircleOptions circleOptions = new CircleOptions()
.center(lugar2)
.radius(500).fillColor(Color.TRANSPARENT); // In meters

//Get back the mutable Circle
Circle circle = mapa.addCircle(circleOptions);

*/
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

	

	public void mensaje_error_login()
{
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("No te has loegeado  ").setTitle("Error")
           .setCancelable(false)
           .setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		}).setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
         	   Intent intent2 = new Intent(Reportes_gen.this,MainActivity.class);
    			startActivity(intent2);
            
            
            }
        }).show();		
}

	
	public void marcador(LatLng d)
	{
		
		
		GoogleMap mapa = null;
    	
mapa.addMarker(new MarkerOptions().position(d).title(String.valueOf("Oposition")).snippet("aqui se encuentra el problma bñablabla")
     			.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
     	PolylineOptions rectOptions = new PolylineOptions()
        .add(new LatLng(37.35, -122.0))
        .add(new LatLng(37.45, -122.0))  // North of the previous point, but at the same longitude
        .add(new LatLng(37.45, -122.2))  // Same latitude, and 30km to the west
        .add(new LatLng(37.35, -122.2))  // Same longitude, and 16km to the south
        .add(new LatLng(37.35, -122.0)); // Closes the polyline.
     	
     // Get back the mutable Polylinepa
     	Polyline polyline = mapa.addPolyline(rectOptions);
     	
     	
		
	}
	
	

}
