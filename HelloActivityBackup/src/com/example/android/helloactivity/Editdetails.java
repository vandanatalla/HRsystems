package com.example.android.helloactivity;

import java.io.IOException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Editdetails extends Activity {

	private static final String NAMESPACE = "http://tempuri.org/";
	private static String URL;
	private static String METHOD_NAME;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editactivity);
		final SharedPreferences prefs = this.getSharedPreferences(
				"com.example.android.helloactivity", Context.MODE_PRIVATE);
		final int empid = prefs.getInt("empid", 0);
		final String columname = prefs.getString("columname", "error");
		final String tablename = "EMP_DETAILS";

		String item = getIntent().getExtras().getString("product");
		final TextView text = (TextView) findViewById(R.id.editText);
		text.setText(item);
		Button button = (Button) findViewById(R.id.save);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String editedvalue = text.getText().toString();
				String updatemesssage = updateinfo(empid, columname,
						editedvalue);
				Toast.makeText(getApplicationContext(), updatemesssage,
						Toast.LENGTH_SHORT).show();
				ArrayList updatedempinfo = updatedempinfo(empid);

				String image1 = (String) updatedempinfo.get(0);
				byte[] image = image1.getBytes();
				ArrayList empdetailsnoimage = new ArrayList();
				for (int j = 1; j <= updatedempinfo.size() - 1; j++) {
					empdetailsnoimage.add(updatedempinfo.get(j));

				}
				Intent intent1 = new Intent(Editdetails.this, Viewdetails.class);
				intent1.putExtra("empdetails", empdetailsnoimage);
				startActivity(intent1);
				finish();

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

	public String updateinfo(int empid, String columname, String editedvalue) {
		Editdetails.URL = "http://170.225.97.68/aspnet_client/LoginService/CommonLogonService.asmx";
		Editdetails.METHOD_NAME = "UpdateSinglevalue";

		HttpTransportSE transport = new HttpTransportSE(URL);
		transport.debug = true;

		// *********** soapobject
		SoapObject soapObject = new SoapObject(Editdetails.NAMESPACE,
				Editdetails.METHOD_NAME);

		soapObject.addProperty("id", empid);
		soapObject.addProperty("columnname", columname);
		soapObject.addProperty("value", editedvalue);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;

		envelope.bodyOut = soapObject;// transport;
		String message = null;

		try {

			transport.call("http://tempuri.org/UpdateSinglevalue", envelope);

			message = envelope.getResponse().toString();

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
		return message;
	}

	public ArrayList updatedempinfo(int id) {
		Editdetails.URL = "http://170.225.97.68/aspnet_client/LoginService/EmployeService.asmx";
		// http://localhost/aspnet_client/wwwww/EmployeeLoginService.asmx
		Editdetails.METHOD_NAME = "RetriveEmployee";
		HttpTransportSE transport = new HttpTransportSE(URL);
		transport.debug = true;
		SoapObject soapObject = new SoapObject(Editdetails.NAMESPACE,
				Editdetails.METHOD_NAME);
		soapObject.addProperty("id", id);
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

		new AlertDialog.Builder(Editdetails.this).setTitle("Message")

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
