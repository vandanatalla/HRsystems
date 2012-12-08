package com.example.android.helloactivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SearchByName extends Activity {

	private static final String NAMESPACE = "http://tempuri.org/";
	private static String URL;
	private static String METHOD_NAME;
	private TextView tvid; 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchbyname);
		 tvid = (TextView) findViewById(R.id.searchName);
		Button searchByNamebtn = (Button) findViewById(R.id.btnSearchName);
		searchByNamebtn.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
		ArrayList employeeList = new ArrayList();
		employeeList = employeesList();
		
		Intent i = new Intent(SearchByName.this, EmpList.class);
		
		i.putExtra("employeeList",employeeList);
		startActivity(i);
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
	
	public ArrayList employeesList()
	{
		SearchByName.URL = "http://170.225.97.68/aspnet_client/LoginService/CommonLogonService.asmx";
		// http://localhost/aspnet_client/wwwww/EmployeeLoginService.asmx
		SearchByName.METHOD_NAME = "RetriveByName";
		HttpTransportSE transport = new HttpTransportSE(URL);
		transport.debug = true;
		SoapObject soapObject = new SoapObject(SearchByName.NAMESPACE, SearchByName.METHOD_NAME);
		
		String searchName = tvid.getText().toString();
		
		soapObject.addProperty("name", searchName);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;

		envelope.bodyOut = soapObject;
		String names = null;
		try {
			
		/*	List  nameList = new ArrayList();
			List  idList  = new ArrayList();
		*/	
			transport.call("http://tempuri.org/RetriveByName", envelope);

			SoapObject response = (SoapObject) envelope.getResponse();
			int i = response.getPropertyCount();
			int j = 0;
			List al = new ArrayList();
			
			while (j <= i - 1) {

				if (response.getProperty(j).toString() != null) {
					al.add(response.getProperty(j).toString());
				}
				j++;
			}
			return (ArrayList) al;
			
		
		/*	
			for(int i = 0; i < names.length ; i++)
			{
				String[] buff =	names[i].split("-");
				nameList.add(buff[0] );
				idList.add(buff[1]);
			}
		*/
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			showDialog("E1");
		} catch (XmlPullParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			showDialog("E2");
		} catch (Exception ex) {
			ex.printStackTrace();
			showDialog("E3");
		}

		return null;
	}
	private void showDialog(String mess)

	{

		new AlertDialog.Builder(SearchByName.this).setTitle("Message")

		.setMessage(mess)

		.setNegativeButton("OK", new DialogInterface.OnClickListener()

		{

			public void onClick(DialogInterface dialog, int which)

			{

			}

		})

		.show();
	}

}
