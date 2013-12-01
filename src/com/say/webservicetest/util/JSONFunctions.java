package com.say.webservicetest.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class JSONFunctions {
	
	public static String getJSONfromURL(String url){

        String _response = "";

        // Download JSON data from URL 
        try{ 
        HttpClient httpclient = new DefaultHttpClient(); 
        HttpPost httppost = new HttpPost(url); 
        HttpResponse response = httpclient.execute(httppost); 
        HttpEntity entity = response.getEntity(); 
        _response = EntityUtils.toString(entity); 
         Log.i(".......",_response); 
        }
        catch(Exception e)
        {
        	Log.e("log_tag", "Error in http connection "+e.toString());
        }
        return _response; 

    }
}
