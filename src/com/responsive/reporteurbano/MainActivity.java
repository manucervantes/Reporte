package com.responsive.reporteurbano;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.android.*;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFacebookUtils.Permissions;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.*;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


//parse Imports


public class MainActivity extends FragmentActivity 
{
	Facebook fb;
	

	
	//evento de boton, abre ventana creareuser.xml
	private  OnClickListener Createuser = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(MainActivity.this,Createuser.class);
			startActivity(intent);
		}
	};
	
	

	//metodo para validar campos y abrir bentana reportes_gen.xml 
	OnClickListener Reportes_gen = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{					
			String usuario = user.getText().toString();
			String passw = pass.getText().toString();
			ParseUser.logInInBackground(usuario, passw, new LogInCallback() 
			{
				  public void done(ParseUser user, ParseException e) {
				    if (user!= null)
				    {
				    	Intent intent2 = new Intent(MainActivity.this,General.class);
				      	startActivity(intent2);
				      	finish();
				    
				    } else 
				    	{
				    		mensaje_error_login();
						}
				  }
			});
		}
	};
	
	Button boton_new_user,boton_login;
	EditText user,pass;

	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		
		
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.user);
		
		Parse.initialize(this, "jOqflSShoZ4BT4AaUYyObmWIXV9vaIA4dXR2qGt3", "4bx1yxpFxDZGeE0GteBKw7y9msUR2DhlpvzIzCL8");
		ParseAnalytics.trackAppOpened(getIntent());
		
		ParseFacebookUtils.initialize("576441039062388");
		
		ParseTwitterUtils.initialize("H9VTb2zEVhgZnVaqNQ", "LWpSGxQuuk5y3sJOwuwMAtuHFObd9mMGTMNPFdRSE");
		user=(EditText)findViewById(R.id.et_user_log);
		pass=(EditText)findViewById(R.id.et_pass_log);
		boton_new_user=(Button)findViewById(R.id.btn_new_user);
		boton_login=(Button)findViewById(R.id.btn_login);
		boton_new_user.setOnClickListener(Createuser);
		boton_login.setOnClickListener(Reportes_gen);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);

	  ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}
	


	
	
	public void login_facebook(View v)
	{
		
		Session session = ParseFacebookUtils.getSession();
		
		
	
		ParseFacebookUtils.logIn(Arrays.asList("email", Permissions.Friends.ABOUT_ME),
		        this, new LogInCallback() {
		  @Override
		  public void done(ParseUser user, ParseException err) {
			  
			  
			  if (user == null) {
				  mensaje_cancelado();
			    } else if (user.isNew()) {
			      Log.d("MyApp", "User signed up and logged in through Facebook!");
			    } else {
			       	Intent intent2 = new Intent(MainActivity.this,General.class);
			        finish();
			       	startActivity(intent2);
					
			    }
		    // Code to handle login.
		  }
		});
			  
		
		
	
	}
	
	
		
	
	
	public void login_twitter(View v) throws ClientProtocolException, IOException
	{

		 

		ParseTwitterUtils.logIn(this, new LogInCallback() {
			  @Override
			  public void done(ParseUser user, ParseException err) {
				  
					    if (user != null) {
					    	
					    	Intent intent2 = new Intent(MainActivity.this,General.class);
					        finish();
					       	startActivity(intent2);
					    	Log.d("Error", "entro :)");

					    	
					    	 
					    } else {
					    	Log.d("Error", "no entro :(");
					    	// Signup failed. Look at the ParseException to see what happened.
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
	
	
	public void mensaje_cancelado()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Operacion Cancelada por el usuario").setTitle("Error")
	           .setCancelable(false)
	           .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	              
	               }
	           }).show();		
		
		
	}
	
		public void mensaje_error_login()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("User name or Password invalid").setTitle("Error")
	           .setCancelable(false)
	           .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	              
	               }
	           }).show();		
	}
	
}