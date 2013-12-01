package com.say.webservicetest.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.say.webservicetest.MainActivity;
import com.say.webservicetest.R;
import com.say.webservicetest.SingleMenuItemActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
 
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
 
    public ListViewAdapter(Context context,
            ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
 
    }
 
    @Override
    public int getCount() {
        return data.size();
    }
 
    @Override
    public Object getItem(int position) {
        return null;
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView code, locationFrom, locationTo, customer, salesman;
 
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        // Get the position from the results
        HashMap<String, String> resultp = new HashMap<String, String>();
        resultp = data.get(position);
 
        // Locate the TextViews in list_item.xml
        code = (TextView) itemView.findViewById(R.id.listitem_code); 
        locationFrom = (TextView) itemView.findViewById(R.id.listitem_locationFrom); 
        locationTo = (TextView) itemView.findViewById(R.id.listitem_locationTo); 
        customer = (TextView) itemView.findViewById(R.id.listitem_customer);
        salesman = (TextView) itemView.findViewById(R.id.listitem_salesman);
 
        // Capture position and set results to the TextViews
        code.setText(resultp.get(MainActivity.TAG_CODE));
        locationFrom.setText(resultp.get(MainActivity.TAG_LOCATION_FROM));
        locationTo.setText(resultp.get(MainActivity.TAG_LOCATION_TO));
        customer.setText(resultp.get(MainActivity.TAG_CUSTOMER));
        salesman.setText(resultp.get(MainActivity.TAG_SALESMAN));
        // Capture position and set results to the ImageView
        // Passes movie images URL into ImageLoader.class to download and cache
        // images
        // Capture button clicks on ListView items
        itemView.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
            	// Get the position from the results
                HashMap<String, String> resultp = new HashMap<String, String>();
                resultp = data.get(position);
                // Send single item click data to SingleMenuItemView Class
                Intent intent = new Intent(context, SingleMenuItemActivity.class);
                // Pass all data 
                intent.putExtra("salesorderingcard_id", resultp.get(MainActivity.TAG_ID));
                // Pass all data 
                intent.putExtra("salesordercard_code", resultp.get(MainActivity.TAG_CODE));
                // Pass all data 
                intent.putExtra("location_from", resultp.get(MainActivity.TAG_LOCATION_FROM));
                // Pass all data 
                intent.putExtra("location_to",
                        resultp.get(MainActivity.TAG_LOCATION_TO));
                // Pass all data 
                intent.putExtra("customername", resultp.get(MainActivity.TAG_CUSTOMER));
                intent.putExtra("salesmanname",
                        resultp.get(MainActivity.TAG_SALESMAN));
                // Start SingleItemView Class
                context.startActivity(intent);
            }
        });
 
        return itemView;
    }
    
}