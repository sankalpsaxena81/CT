package com.ct;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class StopTimingsActivity extends ListActivity {
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.stoptimings);
//	        TextView tv=(TextView)this.findViewById(R.id.busStopTxtField);
	       
	        Intent thisIntent=this.getIntent();
	        Bundle bu=thisIntent.getExtras();
//	        tv.setText((String)bu.get("BusStop"));
	        ArrayList<String> timings=(ArrayList<String>)bu.get("BusStopTimings");
	        String[] contents= new String[2];
//	        contents[0]="sdasd";
//	        contents[1]="sdasfddfd";
	        String[] timingsArr=timings.toArray(contents);
	        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,timingsArr));
	        
	    }

}
