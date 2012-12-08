package com.example.android.helloactivity;

public class Constants {

	public static final String CONSUMER_KEY = "dmT350mu2fZv7SXTw0orUg";
	public static final String CONSUMER_SECRET= "k40kQj3RDi4w8vLGrQd4GHAD81LKBlFu8XhyAgUzZQ";
	
	public static final String REQUEST_URL = "http://api.twitter.com/oauth/request_token";
	public static final String ACCESS_URL = "http://api.twitter.com/oauth/access_token";
	public static final String AUTHORIZE_URL = "http://api.twitter.com/oauth/authorize";
	
	public static final String	OAUTH_CALLBACK_SCHEME	= "x-oauthflow-twitter";
	public static final String	OAUTH_CALLBACK_HOST		= "callback";
	public static final String	OAUTH_CALLBACK_URL		= OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;

}

