package com.example.android.helloactivity;

import java.util.HashMap;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ViewMails extends Activity {

	private ListView lv;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.viewmails);
		lv = (ListView) findViewById(R.id.mailList);

		HashMap<String, String> hm = new HashMap<String, String>();

		hm = (HashMap<String, String>) getIntent().getExtras().get("mails");

		final String[] keys = new String[hm.size()];
		final String[] values = new String[hm.size()];
		int i = 0;
		for (String temp : hm.keySet()) {
			keys[i] = temp;
			values[i] = hm.get(temp);
			i++;
		}

		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, keys));
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				String mailBody = values[position];

				Intent intent = new Intent(ViewMails.this, Mailbody.class);
				intent.putExtra("mailbody", mailBody);
				startActivity(intent);
				
				Toast.makeText(getApplicationContext(), "add",Toast.LENGTH_SHORT ).show();

			}

		});
	}
	/* @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.emp_details, menu);
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
	  }*/

}
