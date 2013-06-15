package com.responsive.reporteurbano;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class Createuser extends Activity{

	private  OnClickListener Mapa = new OnClickListener(){

		@Override
		public void onClick(View v) {
			  String n = name.getText().toString();
			  String ln = last_name.getText().toString();
			  String em = email.getText().toString();
			  String pss= pass.getText().toString();
			  
			  
			  if(n.equals("")||ln.equals("")||em.equals("")||pss.equals(""))
			  {
				  
				  blanco();
				  }
			  
			  else
			  {
			  pop(n,ln,em,pss);
			  inserta(v);
			  Intent intent = new Intent(Createuser.this,Reportes_gen.class);
			  startActivity(intent);
			
			  }
			  
			
			
		}
		
		
		
		
	};
	EditText name,last_name,email,pass;
	Button mpa;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createuser);


		name=(EditText)findViewById(R.id.et_new_name);
		last_name=(EditText)findViewById(R.id.et_new_last_name);
		email=(EditText)findViewById(R.id.et_new_email);
		pass=(EditText)findViewById(R.id.et_new_pass);
		mpa=(Button)findViewById(R.id.btn_create_user);
		mpa.setOnClickListener(Mapa);
		//start Initialization
		Parse.initialize(this, "jOqflSShoZ4BT4AaUYyObmWIXV9vaIA4dXR2qGt3", "4bx1yxpFxDZGeE0GteBKw7y9msUR2DhlpvzIzCL8");
		ParseAnalytics.trackAppOpened(getIntent());
		//end parse initial,.ization 
		//create parse object to update
		//ParseObject ReporteUrbano_location  = new ParseObject("ReporteUrbano_location");
	
	
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

public	void clean(View v)
	{
		name.setText(" ");
		last_name.setText(" ");
	    email.setText(" ");
		
		
	}
	public void inserta(View v)
	{
		  String n = name.getText().toString();
		  String ln = last_name.getText().toString();
		  String em = email.getText().toString();
		  String pss= pass.getText().toString();
		  
		  pop(n,ln,em,pss);
		
	}

	void pop(final String n,final String ln, final String em, final String pss){

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("nombre:"+n+" "+"Last Name:"+ln+" "+"Email:"+em).setTitle("Datos")
	           .setCancelable(false)
	           .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	               ParseUser Reporte_user  = new ParseUser();
	            	   Reporte_user.put("Name",n);
	            	   Reporte_user.put("Last_Name",ln);
	            	   Reporte_user.setUsername(em);
	            	   Reporte_user.setPassword(pss);
	            	//  Reporte_user.saveInBackground();
	                   
	            	   Reporte_user.signUpInBackground(new SignUpCallback() {
	            		   public void done(ParseException e) {
	            		     if (e == null) {
	            		       // Hooray! Let them use the app now.
	            		     } else {
	            		       // Sign up didn't succeed. Look at the ParseException
	            		       // to figure out what went wrong
	            		     }
	            		   }

	            		 });
	            	   
	            	   
	               }
	           }).show();
	    
	}
	/*
	public void captura(String d,String f, String g){
	ParseObject User  = new ParseObject("User");
	User.put("Name",d);
	User.put("Last Name",f);
	User.put("Email",g);
	User.saveInBackground();
	}*/
	
	
	public void blanco()
	{
		

		  AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setMessage("no se permiten datos en blanco").setTitle("Datos")
		           .setCancelable(false)
		           .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		               }
		           }).show();
		
		
	}
}
