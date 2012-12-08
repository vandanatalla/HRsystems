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
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Menu extends Activity {
	private static final String NAMESPACE = "http://tempuri.org/";
	private static String URL;
	private static String METHOD_NAME;

	// public byte[] image;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.menu);
		ListView lv = (ListView) findViewById(R.id.menuList);
		
		final int empid = (Integer) getIntent().getExtras().getSerializable("empid");
		final SharedPreferences prefs = this.getSharedPreferences("com.example.android.helloactivity", Context.MODE_PRIVATE);
		 final int empid1 =   prefs.getInt("empid", 0);
		ArrayList<String> al = new ArrayList<String>();
		al.add("view profile");
		al.add("connect and post on facebook");
		al.add("view image");
		al.add("Search for employee");
		al.add("view mails");
		al.add("contact help desk");
		al.add("Dropbox");
		al.add("Tweet on twitter");

		
		lv.setBackgroundColor(1222);
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, al));
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					ArrayList empdetails = ViewEmpDetails(empid);

					String image1 = (String) empdetails.get(0);
					byte[] image = image1.getBytes();
					ArrayList empdetailsnoimage = new ArrayList();
					for (int j = 1; j <= empdetails.size() - 1; j++) {
						empdetailsnoimage.add(empdetails.get(j));

					}

					// String itemsToParse = ((TextView)
					// view).getText().toString();
					Intent i = new Intent(Menu.this, Viewdetails.class);
					// sending data to new activity
					i.putExtra("empdetails", empdetailsnoimage);
					Bundle b = new Bundle();
					startActivity(i);
				}
				if (position == 1) {
					Intent i = new Intent(Menu.this, TestConnect.class);
					startActivity(i);
				}
				if (position == 2) {
					ArrayList empdetails = ViewEmpDetails(empid);
					String image1 = (String) empdetails.get(0);
					//byte[] image = image1.getBytes();

					Intent i = new Intent(Menu.this, Imageview1.class);
					i.putExtra("image", image1);
					startActivity(i);
				}
				/*if(position == 3)
				{
					Intent i = new Intent(Menu.this,Upload.class);
					startActivity(i);
				}*/
				if(position == 3)
				{
					Intent i = new Intent(Menu.this,SearchMenu.class);
					startActivity(i);
				}
				if(position == 4)
				{
					Intent i = new Intent(Menu.this,GmailLogin.class);
					startActivity(i);
				}
				if(position == 5)
				{
					Intent i = new Intent(Menu.this,Callinterface.class);
					startActivity(i);
				}
				if(position == 6)
				{
					Intent i = new Intent(Menu.this,DBRoulette.class);
					startActivity(i);
				}
				if(position == 7)
				{
					Intent i = new Intent(Menu.this,AndroidTwitterSample.class);
					startActivity(i);
				}
			}
		});

	}

	private ArrayList ViewEmpDetails(int id) {
		Menu.URL = "http://170.225.97.68/aspnet_client/LoginService/EmployeService.asmx";
		// http://localhost/aspnet_client/wwwww/EmployeeLoginService.asmx
		Menu.METHOD_NAME = "RetriveEmployee";
		HttpTransportSE transport = new HttpTransportSE(URL);
		transport.debug = true;
		SoapObject soapObject = new SoapObject(Menu.NAMESPACE, Menu.METHOD_NAME);
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

		new AlertDialog.Builder(Menu.this).setTitle("Message")

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
