package com.example.android.helloactivity;


import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

//@SuppressLint("NewApi")
public class Imageview1 extends Activity {
	private static final String NAMESPACE = "http://tempuri.org/";
	private static String URL;
	private static String METHOD_NAME;
	private Button submitButton;
	private ImageView imgview;
	private byte [] imagebytes;

    @Override
    @SuppressLint("NewApi")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepic);
        submitButton = (Button) findViewById(R.id.button1);
        imgview = (ImageView) findViewById(R.id.imageView1);
       String image = (String) getIntent().getSerializableExtra("image");
       
        byte [] imagebytes = image.getBytes();
       

	 			imagebytes = Base64.decode(imagebytes, Base64.DEFAULT);
	 			Bitmap bmp = BitmapFactory.decodeByteArray(imagebytes, 0, imagebytes.length);
	 			imgview.setImageBitmap(bmp); 

				//new Thr, "Download Thread").start();
		
        
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
   // @SuppressLint("NewApi")
	/*public void image()
    {
    	 Imageview1.URL = "http://170.224.167.217/aspnet_client/LoginService/imageUpload.asmx";
    	 Imageview1.METHOD_NAME = "HelloWorld1";
         
         HttpTransportSE transport = new HttpTransportSE(Imageview1.URL);
 		transport.debug = true;

 		// *********** soapobject
 		SoapObject soapObject = new SoapObject(Imageview1.NAMESPACE,
 				Imageview1.METHOD_NAME);
 		soapObject.addProperty("Id", 20005);
 		
 		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
 				SoapEnvelope.VER11);
 		envelope.dotNet = true;

 		envelope.bodyOut = soapObject;// transport;
 		try {
 			transport.call("http://tempuri.org/HelloWorld1", envelope);
 			String img = envelope.getResponse().toString();
 			byte[] imagebytes = img.getBytes();
 			byte [] Bits = new byte[imagebytes.length*4]; //That's where the RGBA array goes.
 			int i;
 			for(i=0;i<imagebytes.length;i++)
 			{
 			    Bits[i*4] =
 			        Bits[i*4+1] =
 			        Bits[i*4+2] = (byte) ~imagebytes[i]; //Invert the source bits
 			    Bits[i*4+3] = -1;//0xff, that's the alpha.
 			}
 			Bitmap bm = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
 			bm.copyPixelsFromBuffer(ByteBuffer.wrap(Bits));
 			imgview.setImageBitmap(bm);

 			imagebytes = Base64.decode(imagebytes, Base64.DEFAULT);
 			Bitmap bmp = BitmapFactory.decodeByteArray(imagebytes, 0, imagebytes.length);
 			imgview.setImageBitmap(bmp); 
 			//ByteArrayInputStream stream = new ByteArrayInputStream(img.getBytes());
			 //int x = imgarray.length;
			 
			 Bitmap bmp=BitmapFactory.decodeByteArray(img.getBytes(), 0,imagebytes.length );
			 imgview.setImageBitmap(bmp);
			 
			 //bmp.createBitmap(bmp);
			// imgview.setImageBitmap(bmp);
			 ImageView image=new ImageView(this);
			 image.setImageBitmap(bmp);

 		} catch (IOException e) {
 			e.printStackTrace();
 		} catch (XmlPullParserException e) {
 			e.printStackTrace();
 		}
	}*/
}
