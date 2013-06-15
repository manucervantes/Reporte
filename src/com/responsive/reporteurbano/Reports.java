package com.responsive.reporteurbano;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.Object;

import com.google.android.gms.maps.model.LatLng;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.appwidget.*;





public class Reports extends Activity{
	ImageView imagen;
	Button toma_foto;
	EditText coment;
	RadioButton bach,inh,cho;
	private LocationManager locManager;
	private LocationListener locListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reportes);
		Parse.initialize(this, "jOqflSShoZ4BT4AaUYyObmWIXV9vaIA4dXR2qGt3", "4bx1yxpFxDZGeE0GteBKw7y9msUR2DhlpvzIzCL8");
		ParseAnalytics.trackAppOpened(getIntent());
		imagen=(ImageView)findViewById(R.id.iv_reporte);
		toma_foto=(Button)findViewById(R.id.btn_foto);
		coment=(EditText)findViewById(R.id.et_coment);
		bach=(RadioButton)findViewById(R.id.rd_baches);
		inh=(RadioButton)findViewById(R.id.rd_inh);
		cho=(RadioButton)findViewById(R.id.rd_cho);
		toma_foto.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent tomaf = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(tomaf,0);
            
			}
			
		});
		
		
		
		
		
	}
	
	protected  void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		Bitmap foto = (Bitmap)data.getExtras().get("data");
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		foto.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		//crea_reporte(foto);
		
		
		//Bitmap foto = (Bitmap)data.getExtras().get("data");
		raro(data.toString());
		imagen.setImageBitmap(foto);
	
	}
	ParseObject reportes = new ParseObject("reportes");
	ParseObject soporte = new ParseObject("soporte");
	public void crea_reporte(View v)
	{
		String tipo = null;

	//	imagen.buildDrawingCache();
		//Bitmap bmap = imagen.getDrawingCache();
		BitmapDrawable drawable = (BitmapDrawable) imagen.getDrawable();
		Bitmap bitmap = drawable.getBitmap();
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] datas = stream.toByteArray();
		
				
		   
if(inh.isChecked())
{
	 tipo = inh.getText().toString();
}
else 
	if(bach.isChecked())
	{
		tipo = bach.getText().toString();
		
	}
	else
		if(cho.isChecked())
		{
			tipo = cho.getText().toString();
		}
		else
			error_check();
	
		
locManager =  (LocationManager)getSystemService(Context.LOCATION_SERVICE);
Location loc =  locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	double latitud = loc.getLatitude();
	double longitud = loc.getLongitude();
	ParseGeoPoint point = new ParseGeoPoint(latitud,longitud);
	

	 
		ParseFile file = new ParseFile("resume.jpg", datas);
		
		reportes.put("descripcion", coment.getText().toString());
		reportes.put("foto", file);
		reportes.put("location", point);
		
		reportes.put("usuario_fk",ParseUser.getCurrentUser());
		
		reportes.put("tipo_reporte", tipo);
		reportes.put("status", "abierto ");
		
		quer(coment, reportes);
		soporte.put("likes", 0);
		
		//soporte.put("usuario_fk",ParseUser.getCurrentUser());
		soporte.put("reportes_fk",reportes);
		reportes.put("soporte_fk", soporte);
		//soporte.put("reportes_fk", reportes);
		
		

		
		
		mensaje(coment.getText().toString());
		reportes.saveInBackground();
		soporte.saveInBackground();
		
		
		
		
	}
	
public void quer(View v, ParseObject anotherApplication)
{

    ParseFile applicantResume = (ParseFile)anotherApplication.get("foto");
	applicantResume.getDataInBackground(new GetDataCallback() {
	  public void done(byte[] data, ParseException e) {
	    if (e == null) {
	    	
	    	Log.d("test",data.toString());
	    	Log.d("test","Entro");
			imagen.setImageBitmap(BitmapFactory.decodeByteArray(data,0,data.length) );
	    } else {
	      // something went wrong
	    }
	  }
	});

	
}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public void raro(String s)
	{
		
		Log.d("test",s);
		/*

    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setMessage(s).setTitle("Datos")
		           .setCancelable(false)
		           .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		               }
		           }).show();
    	
		
		*/
	}
	
	
	public void mensaje(String s)
	{
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("tu mensaje:"+s).setTitle("Reporte")
	           .setCancelable(false)
	           .setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).setPositiveButton("enviar", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	         	   
	            
	            
	            }
	        }).show();		
		
		
	}
	
	public void error_check()
	{

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("Selecciona un tipo de  problema").setTitle("error")
	           .setCancelable(false)
	           .setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).setPositiveButton("enviar", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	         	   
	            
	            
	            }
	        }).show();		
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
