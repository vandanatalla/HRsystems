/*
 * Copyright (c) 2010-11 Dropbox, Inc.
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */


package com.example.android.helloactivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.DropboxFileInfo;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.DropboxAPI.ThumbFormat;
import com.dropbox.client2.DropboxAPI.ThumbSize;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxIOException;
import com.dropbox.client2.exception.DropboxParseException;
import com.dropbox.client2.exception.DropboxPartialFileException;
import com.dropbox.client2.exception.DropboxServerException;
import com.dropbox.client2.exception.DropboxUnlinkedException;

/**
 * Here we show getting metadata for a directory and downloading a file in a
 * background thread, trying to show typical exception handling and flow of
 * control for an app that downloads a file from Dropbox.
 */

public class DownloadItem extends AsyncTask<Void, Long, Boolean> {


	public static Context mContext;
	private final ProgressDialog mDialog;
	private DropboxAPI<?> mApi;
	private String mPath;
	
	private String fName;
	private Drawable mDrawable;

	private FileOutputStream mFos;

	private boolean mCanceled;
	private Long mFileLen;
	private String mErrorMsg;
	private String pathToFile;
	// Note that, since we use a single file name here for simplicity, you
	// won't be able to use this code for two simultaneous downloads.
//	private final static String IMAGE_FILE_NAME = "dbroulette.png";

	public DownloadItem(Context context, DropboxAPI<?> api,
			String dropboxPath, String filename) {
		// We set the context this way so we don't accidentally leak activities
		mContext = context.getApplicationContext();

		mApi = api;
		mPath = dropboxPath;
		fName = filename;

		mDialog = new ProgressDialog(context);
		mDialog.setMessage("Downloading Item");
		mDialog.setButton("Cancel", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				mCanceled = true;
				mErrorMsg = "Canceled";

				// This will cancel the getThumbnail operation by closing
				// its stream
				if (mFos != null) {
					try {
						mFos.close();
					} catch (IOException e) {
					}
				}
			}
		});

		mDialog.show();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		if (mCanceled) {
			return false;
		} 
		String localfilepath = null;
		FileOutputStream outputStream = null;
		try {
//			localfilepath=mContext.getFilesDir().getAbsolutePath() + "/dbrou/" + fName;
			localfilepath=Environment.getExternalStorageDirectory() + "/dbrou/" ;
			
//			localfilepath="/rapchik/"+fName;
//			File calfilepath=new File("/sdcard/");
			
//			File file = mContext.getDir("/dbroulette/photos/", 0);
			File file =new File(localfilepath);
			file.mkdirs();
			file =new File(localfilepath+fName);
			outputStream = new FileOutputStream(file);
			pathToFile="/photos/"+fName;
			DropboxFileInfo info = mApi.getFile(pathToFile, null, outputStream, null);
			Log.i("DbExampleLog", "The file's rev is: " + info.getMimeType());
			local_file_Path=localfilepath;
			// /path/to/new/file.txt now has stuff in it.
		} catch (DropboxException e) {
			Log.e("DbExampleLog", "Something went wrong while downloading.");    		    
		} catch (FileNotFoundException e) {
			Log.e("DbExampleLog", "File not found."+fName+":"+localfilepath);
			return false;
		} 
		finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {return false;}
			}
		}


		if (mCanceled) {
			return false;
		}return true;
	}

	@Override
	protected void onProgressUpdate(Long... progress) {
		int percent = (int)(100.0*(double)progress[0]/mFileLen + 0.5);
		mDialog.setProgress(percent);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		mDialog.dismiss();
		if (result) {
			// Set the image now that we have it;;
			
			try {
					onFileClick(fName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			// Couldn't download it, so show an error
			showToast(mErrorMsg);
		}
	}

	private void showToast(String msg) {
		Toast error = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
		error.show();
	}
	
	private String local_file_Path;
	public void onFileClick(String file_name)
    {
    	 MimeTypeMap myMime = MimeTypeMap.getSingleton();

         Intent newIntent = new Intent(android.content.Intent.ACTION_VIEW);
         File temp = null;
         
         temp=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/dbrou/" +file_name);
         
         String mimeType = myMime.getMimeTypeFromExtension(fileExt(temp.toString()).substring(1));
         showToast(temp.getAbsolutePath()+" : "+ mimeType);
         newIntent.setDataAndType(Uri.fromFile(temp),mimeType);
         newIntent.setFlags(newIntent.FLAG_ACTIVITY_NEW_TASK);
         try {
        	 mContext.startActivity(newIntent);
         } catch (android.content.ActivityNotFoundException e) {
             Toast.makeText(mContext, "No handler for this type of file.", 4000).show();
         }
    }
	
	 private String fileExt(String url) {
	        if (url.indexOf("?")>-1) {
	            url = url.substring(0,url.indexOf("?"));
	        }
	        if (url.lastIndexOf(".") == -1) {
	            return null;
	        } else {
	            String ext = url.substring(url.lastIndexOf(".") );
	            if (ext.indexOf("%")>-1) {
	                ext = ext.substring(0,ext.indexOf("%"));
	            }
	            if (ext.indexOf("/")>-1) {
	                ext = ext.substring(0,ext.indexOf("/"));
	            }
	            return ext.toLowerCase();

	        }
	    }

}
