package com.example.android.helloactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Mailbody extends Activity{
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.mailbody);
	 TextView tv = (TextView) findViewById(R.id.mailBody);
	 ImageView img = (ImageView) findViewById(R.id.imageView1);
	 img.setVisibility(View.INVISIBLE);
	 
	 String mail = getIntent().getExtras().getString("mailbody");
	 
	
	 
	 tv.setText(mail);
	 }
	 /*	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.emp_details, menu);
	        menu.add("Home");
	        return true;
	    }
	  public boolean onOptionsItemSelected(MenuItem item) {
		  Intent i = new Intent(this, Menu.class);
		  i.putExtra("empid", 20007);
		  i.setClassName("com.example.android.helloactivity", "com.example.android.helloactivity.Menu");
		  startActivity(i);
		  return true;
	  }
	  */
}
