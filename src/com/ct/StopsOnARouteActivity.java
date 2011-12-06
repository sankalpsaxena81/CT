package com.ct;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StopsOnARouteActivity extends ListActivity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stopsonarouteview);
        
   
        
        Intent thisIntent=this.getIntent();
        Bundle bu=thisIntent.getExtras();
        
        ArrayList<String> stops=(ArrayList<String>)bu.get("BusStops");
        
        String[] contents= new String[0];
        String[] stopsArr=stops.toArray(contents);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,stopsArr));
        
    }
	
	 @Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			String item = (String) getListAdapter().getItem(position);
			String stopCode=item;
			Log.d("sankalp-Route",item );
			
			Intent thisIntent=this.getIntent();
		    Bundle bu=thisIntent.getExtras();
			
			String route=(String)bu.get("Route");
			
			SQLiteDatabase db=openOrCreateDatabase("CalgaryTransit", MODE_PRIVATE, null);
		    String serviceId="1";   
			ArrayList<String>timings=DatabaseQueries.searchRouteTimingsAtABusStop(route,serviceId,stopCode,db);
			
			Intent intent = new Intent(getBaseContext(), StopTimingsActivity.class);
			intent.putExtra("BusStopTimings", timings);
			intent.putExtra("BusStop", stopCode);
			intent.putExtra("Route",route );
			startActivity(intent);
			
		}

}
