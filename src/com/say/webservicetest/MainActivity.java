package com.say.webservicetest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.say.webservicetest.db.DBHelper;
import com.say.webservicetest.util.JSONFunctions;
import com.say.webservicetest.util.ListViewAdapter;

public class MainActivity extends Activity {
	
    // Progress Dialog
    ProgressDialog mProgressDialog;
 
    // Declare Variables
    JSONObject jsonobject;
    JSONArray jsonarray;
    ListView listview;
    ListViewAdapter adapter;
    ArrayList<HashMap<String, String>> arraylist;
    
    DBHelper dbHelper;
   
    JSONObject jb;
    
    // JSON Node names
    public static String TAG_ID = "salesorderingcard_id";
    public static String TAG_CODE = "salesordercard_code"; 
    public static final String TAG_LOCATION_TO = "location_to";
    public static final String TAG_LOCATION_FROM = "location_from";
    public final static String TAG_CUSTOMER = "customername";
    public final static String TAG_SALESMAN = "salesmanname";
    public final String URL = "http://www.shoppersgroup.net/vanmanagement/results.php";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		
		dbConnect();
		
		
		
		// DownloadJSON execute
        new DownloadJSON().execute();
	}
	
	// DownloadJSON AsyncTask
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {
 
    @Override
    protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Sales Order Details");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
    }
    
    @Override
    protected Void doInBackground(Void... params) {
            // Create the array 
            arraylist = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given website URL in JSONfunctions.class
            String result = JSONFunctions.getJSONfromURL(URL);

            try {
            	JSONArray jr = new JSONArray(result);
            	for(int i=0;i<jr.length();i++)
                 {	
            			HashMap<String, String> map = new HashMap<String, String>();
                          jb = (JSONObject)jr.get(i);
                          map.put(TAG_ID, jb.getString(TAG_ID));
                          map.put(TAG_CODE, jb.getString(TAG_CODE));
                          map.put(TAG_LOCATION_FROM, jb.getString(TAG_LOCATION_FROM));
                          map.put(TAG_LOCATION_TO, jb.getString(TAG_LOCATION_TO));
                          map.put(TAG_CUSTOMER, jb.getString(TAG_CUSTOMER));
                          map.put(TAG_SALESMAN, jb.getString(TAG_SALESMAN));
                          arraylist.add(map);
                          
                        String strCode, strFrom, strTo, strCustomer, strSalesman;
              		
              			strCode = jb.getString(TAG_CODE);
              			strFrom = jb.getString(TAG_LOCATION_FROM);
              	    	strTo = jb.getString(TAG_LOCATION_TO);
              	    	strCustomer = jb.getString(TAG_CUSTOMER);
              	    	strSalesman = jb.getString(TAG_SALESMAN);
              	    		
              	    	dbHelper.createEntry(strCode, strFrom, strTo, strCustomer, strSalesman);
              	    		
        
                 }
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
    } 
 
    @Override
    protected void onPostExecute(Void args) {
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listView1);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(MainActivity.this, arraylist);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);

    		//TextView tv = (TextView) findViewById (R.id.textView1);
    		//tv.setText("Successfully processed");
    	
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    private void dbConnect() {
		// TODO dbConnect
		dbHelper = new DBHelper(this);
		
		try {
				
		        dbHelper.createDataBase();
		
		} catch (IOException ioe) {
		
		        throw new Error("Unable to create database");
		
		}
		
		try {
		
		        dbHelper.openDataBase();
		
		} catch (SQLException sqle) {
		
		        throw sqle;
		
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		dbHelper.close();
	}
	
	@Override
	 public boolean onCreateOptionsMenu(Menu menu)
	 {
	        MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.menu.main, menu);
	        return true;
	 }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, AddNewRecord.class);
		startActivity(i);
		finish();
		return super.onOptionsItemSelected(item);
	} 
    
	 

}
