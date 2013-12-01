package com.say.webservicetest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.say.webservicetest.util.JSONParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNewRecord extends Activity {
 
    // Progress Dialog
    private ProgressDialog pDialog;
 
    JSONParser jsonParser = new JSONParser();
    EditText etCode, etFrom, etTo, etCustomer, etSalesman;
    Button btAdd;
    
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    public static String TAG_CODE = "salesordercard_code";
    public static final String TAG_LOCATION_TO = "location_to";
    public static final String TAG_LOCATION_FROM = "location_from";
    public final static String TAG_CUSTOMER = "customercode";
    public final static String TAG_SALESMAN = "salesmancode";
    
    // url to create new product
    private static String url_create_product = 
    		"http://www.shoppersgroup.net/vanmanagement/create_salesorder.php";
 
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewrecord);
 
        // Edit Text
        etCode = (EditText) findViewById(R.id.etSalesOrderCode);
        etFrom = (EditText) findViewById(R.id.etLocationFrom);
        etTo = (EditText) findViewById(R.id.etLocationTo);
        etCustomer = (EditText) findViewById(R.id.etCustomerCode); 
        etSalesman = (EditText) findViewById(R.id.etSalesManCode);
 
        // Create button
        btAdd = (Button) findViewById(R.id.btAddNewRecord);
 
        // button click event
        btAdd.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new CreateNewRecord().execute();
            }
        });
    }
    
    /**
     * Background Async Task to Create new record
     * */
    class CreateNewRecord extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddNewRecord.this);
            pDialog.setMessage("Creating Sales Order..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String code = etCode.getText().toString();
            String from = etFrom.getText().toString();
            String to = etTo.getText().toString();
            String customer = etCustomer.getText().toString();
            String salesman = etSalesman.getText().toString();
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_CODE, code));
            params.add(new BasicNameValuePair(TAG_LOCATION_FROM, from));
            params.add(new BasicNameValuePair(TAG_LOCATION_TO, to));
            params.add(new BasicNameValuePair(TAG_CUSTOMER, customer));
            params.add(new BasicNameValuePair(TAG_SALESMAN, salesman));
 
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);
 
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    
                    // closing this screen
                    finish();
                } else {
                    // failed to create product
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
            // dismiss the dialog once done
            pDialog.dismiss();
        }
 
    }
    
    

}
