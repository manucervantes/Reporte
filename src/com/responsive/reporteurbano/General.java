package com.responsive.reporteurbano;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;




public class General extends Activity {

	Button cerca_mi,nuevo;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.general);
		
		cerca_mi=(Button)findViewById(R.id.btn_general_buscar);
		nuevo=(Button)findViewById(R.id.btn_general_nuevo);
		
		
		Parse.initialize(this, "jOqflSShoZ4BT4AaUYyObmWIXV9vaIA4dXR2qGt3", "4bx1yxpFxDZGeE0GteBKw7y9msUR2DhlpvzIzCL8");
		ParseAnalytics.trackAppOpened(getIntent());

		
	
	
	}
	
	
	public void cerca_de_mi(View v)
	{
		
		Intent intent = new Intent(General.this,Reportes_gen.class);
       	startActivity(intent);
		
	}
	
	public void nuevo(View v)
	{
		Intent intent = new Intent(General.this,Reports.class);
       	startActivity(intent);
	
	}
	

	public void mis_repos(View v)
	{
		
		Intent intent3 = new Intent(General.this,Mis_repors.class);
		startActivity(intent3);
		
	}
	
	
	

	
}
