package com.example.android.helloactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Viewdetails extends Activity {
	private static final String[] Values = null;
	ArrayList<String> empdetails = new ArrayList<String>();

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.emp_details);
		final SharedPreferences prefs = this.getSharedPreferences("com.example.android.helloactivity", Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();

		final ListView showingList = (ListView) findViewById(R.id.empDetailsList);
		ArrayList<String> ValuesRepresent = new ArrayList<String>();
		ValuesRepresent.add("First Name");
		ValuesRepresent.add("last Name");
		ValuesRepresent.add("location");
		ValuesRepresent.add("address");
		ValuesRepresent.add("designation");
		ValuesRepresent.add("manager name");
		ValuesRepresent.add("phone ");
		ValuesRepresent.add("empid");
		final HashMap hm = new HashMap();
		hm.put(0, "FIRST_NAME");
		hm.put(1, "LAST_NAME");
		hm.put(2, "EMP_LOCATION");
		hm.put(3, "EMP_ADDRESS");
		hm.put(4, "EMP_DESIGNATION");
		hm.put(5, "EMP_MANAGER");
		hm.put(6, "EMP_PHONE");
		hm.put(7, "EMP_ID");
		
		
		empdetails = getIntent().getExtras().getStringArrayList("empdetails");
		int i = 0;
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (String x : empdetails) {
			Map<String, String> datum = new HashMap<String, String>(2);
			datum.put("Item", x);
			datum.put("SubItem", ValuesRepresent.get(i++));
			data.add(datum);
		}

		// showingList.setAdapter(new
		// ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,
		// empdetails));
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_2, new String[] { "Item",
						"SubItem" }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		showingList.setAdapter(adapter);
		showingList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String message = empdetails.get(position);
				editor.putString("columname", (String) hm.get(position));
				editor.commit();
				// String product =
				// (String)(showingList.getItemAtPosition(position));
				
				if(position == 3 || position == 6)
				{
				Intent i = new Intent(Viewdetails.this, Editdetails.class);
				// sending data to new activity
				i.putExtra("product", message);
				Bundle b = new Bundle();
				startActivity(i);
				finish();
				}
				
				else
				{
					Toast.makeText(getApplicationContext(), "contact HR to edit this", Toast.LENGTH_LONG).show();
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
