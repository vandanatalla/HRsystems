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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class EmpList extends Activity {
	
	private static final String NAMESPACE = "http://tempuri.org/";
	private static String URL;
	private static String METHOD_NAME;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.employeenamelist);
		ListView lv = (ListView) findViewById(R.id.empList);
		ArrayList employeeList = new ArrayList();
		employeeList = getIntent().getExtras().getStringArrayList("employeeList");

		List nameList = new ArrayList();
		final List idList = new ArrayList();

		for (int i = 0; i < employeeList.size(); i++) {
			String list = (String) employeeList.get(i);
			String[] buff = list.split("-");

			nameList.add(buff[0]);
			idList.add(buff[1]);
		}
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, nameList));

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int empid = Integer.parseInt( idList.get(position).toString());
				
				ArrayList al =	searchEmp(empid);
				String image1 = (String) al.get(0);
				byte[] image = image1.getBytes();
				ArrayList empdetailsnoimage = new ArrayList();
				for (int j = 1; j <= al.size() - 1; j++) {
					empdetailsnoimage.add(al.get(j));

				}

				// String itemsToParse = ((TextView)
				// view).getText().toString();
				Intent i = new Intent(EmpList.this, SearchedEmpDetails.class);
				// sending data to new activity
				i.putExtra("empdetails", empdetailsnoimage);
				Bundle b = new Bundle();
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
	
	public ArrayList searchEmp(int empid)
	{
		
		
		EmpList.URL = "http://170.225.97.68/aspnet_client/LoginService/EmployeService.asmx";
		// http://localhost/aspnet_client/wwwww/EmployeeLoginService.asmx
		EmpList.METHOD_NAME = "RetriveEmployee";
		HttpTransportSE transport = new HttpTransportSE(URL);
		transport.debug = true;
		SoapObject soapObject = new SoapObject(EmpList.NAMESPACE, EmpList.METHOD_NAME);
		soapObject.addProperty("id", empid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;

		envelope.bodyOut = soapObject;

		try {

			transport.call("http://tempuri.org/RetriveEmployee", envelope);

			SoapObject response = (SoapObject) envelope.getResponse();
			int i = response.getPropertyCount();
			/*
			 * Stri6ng name = response.getProperty(0).toString(); String s =
			 * response.getProperty(1).toString();
			 */
			int j = 0;
			ArrayList<String> al = new ArrayList<String>();
			while (j <= i - 1) {

				if (response.getProperty(j).toString() != null) {
					al.add(response.getProperty(j).toString());
				}
				j++;
			}

			return al;

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

		new AlertDialog.Builder(EmpList.this).setTitle("Message")

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
