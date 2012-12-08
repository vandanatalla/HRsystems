package com.example.android.helloactivity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchMenu extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.searchmenu);
		ListView lv = (ListView) findViewById(R.id.searchmenuList);
		ArrayList al = new ArrayList();
		al.add("Search By Name");
		al.add("Search By Id");
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, al));
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if(position ==1)
				{
					Intent i = new Intent(SearchMenu.this, SearchById.class);
					startActivity(i);
				}
				if(position ==0)
				{
					Intent i = new Intent(SearchMenu.this, SearchByName.class);
					startActivity(i);
				}
			}
		});
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
