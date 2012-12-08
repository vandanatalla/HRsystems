/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.helloactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.Socket;
import org.ksoap2.*;
import org.ksoap2.serialization.*;
import org.ksoap2.transport.*;
import org.xmlpull.v1.XmlPullParserException;
import android.text.TextUtils;
import android.util.Base64;

/**
 * A minimal "Hello, World!" application.
 */
public class HelloActivity extends Activity {

	private static final String NAMESPACE = "http://tempuri.org/";
	private static String URL;
	private static String METHOD_NAME;
	private Button submitButton;

	private EditText username;
	private EditText password;

	/**
	 * Called with the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello_activity);

		submitButton = (Button) findViewById(R.id.button01);

		username = (EditText) findViewById(R.id.username);
		username = (EditText) findViewById(R.id.password);

		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

		int empid = loginAuthentication();
		Intent intent1 = new Intent(HelloActivity.this,Menu.class);
		Bundle b = new Bundle();
		intent1.putExtra("empid",empid);
		startActivity(intent1);
		finish();

			}

		});

	}

	public int loginAuthentication() {
		// HelloActivity.URL =
		// "http://10.0.2.2:42582/WebServiceDisaster/Service.asmx";
		final SharedPreferences prefs = this.getSharedPreferences("com.example.android.helloactivity", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		HelloActivity.URL = "http://170.225.97.68/aspnet_client/LoginService/CommonLogonService.asmx";

		HelloActivity.METHOD_NAME = "LoginValidation";

		//String data = username.getText().toString();
		//String data1 = password.getText().toString();

		// ***** transport
		HttpTransportSE transport = new HttpTransportSE(URL);
		transport.debug = true;

		// *********** soapobject
		SoapObject soapObject = new SoapObject(HelloActivity.NAMESPACE,
				HelloActivity.METHOD_NAME);

		soapObject.addProperty("username", "arun");
		soapObject.addProperty("password", "arun");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;

		envelope.bodyOut = soapObject;// transport;

		String  logindetails = null;
		int id = 0;
		try {

			transport.call("http://tempuri.org/LoginValidation", envelope);
			
			logindetails = (envelope.getResponse().toString());
			
		    String[] buff =	logindetails.split("-");
			
		    id =  Integer.parseInt(buff[1]);
			editor.putInt("empid", id);
			editor.commit();
			
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
		return id;
	}

	private void showDialog(String mess)

	{

		new AlertDialog.Builder(HelloActivity.this).setTitle("Message")

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
