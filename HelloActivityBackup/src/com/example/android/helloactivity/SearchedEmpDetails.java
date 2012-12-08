package com.example.android.helloactivity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchedEmpDetails extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.searchedempdetails);
		ListView lv = (ListView) findViewById(R.id.searchedEmpDetails);
		ArrayList empdetails = new ArrayList();
		empdetails   = getIntent().getExtras().getStringArrayList("empdetails");
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, empdetails));
	}
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	      /*  getMenuInflater().inflate(R.menu.emp_details, menu);*/
	        menu.add("Home");
	        menu.add("Logout");
	        return true;
	    }
	  public boolean onOptionsItemSelected(MenuItem item) {
		  
		  if(item.toString() == "Home")
		  {
		  Intent i = new Intent(this, Menu.class);
		  i.setClassName("com.example.android.helloactivity", "com.example.android.helloactivity.Menu");
		  startActivity(i);
		  }
		  else if (item.toString() == "Logout")
		  {
			  Intent i = new Intent(this, HelloActivity.class);
			  i.setClassName("com.example.android.helloactivity", "com.example.android.helloactivity.HelloActivity");
			  startActivity(i);
		  
		  }
		  return true;
	  }

}
