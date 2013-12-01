package com.say.webservicetest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.say.webservicetest.util.JSONParser;

public class SingleMenuItemActivity extends Activity implements OnClickListener {
	// Declare Variables 
    String code, locationFrom, locationTo, customer, salesman;
    String url, salesorderingcard_id;
    ProgressDialog mProgressDialog;
    Bitmap bmImg = null;
    
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    Button btUpdate;
    EditText etCode, etFrom, etTo, etCustomer, etSalesman;
    
    // JSON Node names
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_ID = "salesorderingcard_id";
    public static String TAG_CODE = "salesordercard_code";
    public static final String TAG_LOCATION_TO = "location_to";
    public static final String TAG_LOCATION_FROM = "location_from";
    public final static String TAG_CUSTOMER = "customercode";
    public final static String TAG_SALESMAN = "salesmancode";
    
    // url to update salesorder
    public static final String url_update_salesorder = 
    		"http://www.shoppersgroup.net/vanmanagement/update_salesorder.php";
  
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_sales_order);
        
        btUpdate = (Button) findViewById (R.id.btUpdate);
        
        etCode = (EditText) findViewById (R.id.code_label);
        etFrom = (EditText) findViewById (R.id.locationFrom_label); 
        etTo = (EditText) findViewById (R.id.locationTo_label); 
        etCustomer = (EditText) findViewById (R.id.customer_label);
        etSalesman = (EditText) findViewById (R.id.salesman_label);
        
        btUpdate.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        salesorderingcard_id = extras.getString("salesorderingcard_id");
        Log.d("ID is", "" + salesorderingcard_id);
        
        // Execute loadSingleView AsyncTask
        new loadSingleView().execute();
        
    }
        
        public class loadSingleView extends AsyncTask<String, String, String> {
                 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(SingleMenuItemActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Sales Order");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
 
        }
 
        @Override
        protected String doInBackground(String... args) {
                // Retrieve data from ListViewAdapter on click event
                Intent i = getIntent();
                // Get the result
                code = i.getStringExtra("salesordercard_code");
                // Get the result
                locationFrom = i.getStringExtra("location_from");
                // Get the result
                locationTo = i.getStringExtra("location_to");
                customer = i.getStringExtra("customername");
                salesman = i.getStringExtra("salesmanname");

            return null;
        }
 
        @Override
        protected void onPostExecute(String args) {
            // Locate the EditTexts in singleitemview.xml
            EditText txtcode = (EditText) findViewById(R.id.code_label);
            EditText txtlocationFrom = (EditText) findViewById(R.id.locationFrom_label);
            EditText txtlocationTo = (EditText) findViewById(R.id.locationTo_label);
            EditText txtcustomer = (EditText) findViewById (R.id.customer_label);
            EditText txtsalesman = (EditText) findViewById (R.id.salesman_label);
            
            // Set results to the EditTexts
            txtcode.setText(code);
            txtlocationFrom.setText(locationFrom);
            txtlocationTo.setText(locationTo);
            txtcustomer.setText(customer);
            txtsalesman.setText(salesman);
 
            // Close the progressdialog
            mProgressDialog.dismiss();
 
        }
        
    }

        /**
	     * Background Async Task to  Save SalesOrder Details
	     * */
	    class SaveSalesOrderDetails extends AsyncTask<String, String, String> {
	 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            /*
	            mProgressDialog = new ProgressDialog(SingleMenuItemActivity.this);
	            mProgressDialog.setMessage("Updating sales order ...");
	            mProgressDialog.setIndeterminate(false);
	            mProgressDialog.setCancelable(true);
	            mProgressDialog.show(); */
	        }
	 
	        /**
	         * Saving salesorder
	         * */
	        protected String doInBackground(String... args) {
	 
	            // getting updated data from EditTexts
	            String salesordercard_code = etCode.getText().toString();
	            String location_from = etFrom.getText().toString();
	            String location_to = etTo.getText().toString();
	            
	            String customername = etCustomer.getText().toString();
	            String salesmanname = etSalesman.getText().toString();
	            
	            // Building Parameters
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair(TAG_ID, salesorderingcard_id));
	            params.add(new BasicNameValuePair(TAG_CODE, salesordercard_code));
	            params.add(new BasicNameValuePair(TAG_LOCATION_FROM, location_from));
	            params.add(new BasicNameValuePair(TAG_LOCATION_TO, location_to));
	            params.add(new BasicNameValuePair(TAG_CUSTOMER, customername));
	            params.add(new BasicNameValuePair(TAG_SALESMAN, salesmanname));
	            
	            Log.d("Salesman", salesmanname);
	 
	            // sending modified data through http request
	            // Notice that update salesorder url accepts POST method
	            JSONObject json = jsonParser.makeHttpRequest(url_update_salesorder,
	                    "POST", params);

	            // check json success tag
	            try {
	            	int success = json.getInt(TAG_SUCCESS);
	            	
	            	// full json response
		            Log.d("Post Comment attempt", json.toString());
		            Log.d("Is Successful?", "" + String.valueOf(success));
	 
	                if (success == 1) {
	                    // successfully updated
	                    Intent i = getIntent();
	                    // send result code 100 to notify about Sales Order update
	                    setResult(100, i);
	                    //finish();
	                	Log.d("Update Successful", "Update Successful");
	                } else {
	                    // failed to update Sales Order
	                	Log.d("Update failed", "Update failed");
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	 
	            return null;
	        }
	 
	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog once salesorder updated
	            mProgressDialog.dismiss();
	        }
	    }
	    
	    @Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new SaveSalesOrderDetails().execute();
		}
		
}