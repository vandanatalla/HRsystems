package com.example.android.helloactivity;
public class EmailAccount {
	public String urlServer = "gmail.com";
	public String username = "avinash.a569@gmail.com";
	public String password = "yerrala1990";
	public String emailAddress;
	public EmailAccount(String username, String password, String urlServer) {
		this.username = username;
		this.password = password;
		this.urlServer = urlServer;
		this.emailAddress = username + "@" + urlServer;
	}
}
