package com.example.android.helloactivity;

import java.io.IOException;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.security.auth.PrivateCredentialPermission;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class GmailLogin extends Activity {

	private EditText username;
	private EditText password;
	private Button submit;
	private ProgressBar pb;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gmaillogin);

		username = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);
		pb.bringToFront();
		
		
		final String Gmailid = username.getText().toString();
		final String GmailPass = password.getText().toString();

		submit = (Button) findViewById(R.id.btnGmailLogin);

		submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				pb.setVisibility(View.VISIBLE);
				username.setVisibility(View.INVISIBLE);
				password.setVisibility(View.INVISIBLE);
				submit.setVisibility(View.INVISIBLE);
				EmailManager gmail = new EmailManager("avinash.a569@gmail.com", "yerrala1990", "gmail.com",
						"smtp.gmail.com", "imap.gmail.com");	
				
				HashMap<String,String> hm =	new HashMap<String,String>();
				try {
					hm =	gmail.getMails();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*ArrayList subject = new ArrayList();
				subject = (ArrayList) hm.keySet();
				ArrayList mailBody = (ArrayList) hm.values();*/
				
				Intent i = new Intent(GmailLogin.this, ViewMails.class);
				i.putExtra("mails", hm);
				startActivity(i);
			
			
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
	  }
*/
}
