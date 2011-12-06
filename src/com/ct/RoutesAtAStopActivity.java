package com.ct;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RoutesAtAStopActivity extends ListActivity  {
	 private String route;
	private String busStop;

	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.routesatabusstopview);
	        
	   
	        
	        Intent thisIntent=this.getIntent();
	        Bundle bu=thisIntent.getExtras();
	        
	        ArrayList<String> routesList=(ArrayList<String>)bu.get("Routes");
	        
	        String[] contents= new String[0];
	        String[] routesArr=routesList.toArray(contents);
	        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,routesArr));
	        
	    }
	 
	 @Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			String item = (String) getListAdapter().getItem(position);
			route=item;
			Log.d("sankalp-Route",item );
			
			Intent thisIntent=this.getIntent();
		    Bundle bu=thisIntent.getExtras();
			busStop=(String)bu.get("BusStop");
			
			SQLiteDatabase db=openOrCreateDatabase("CalgaryTransit", MODE_PRIVATE, null);
		    String serviceId="1";   
			ArrayList<String>timings=DatabaseQueries.searchRouteTimingsAtABusStop(route,serviceId,busStop,db);
			
			Intent intent = new Intent(getBaseContext(), StopTimingsActivity.class);
			intent.putExtra("BusStopTimings", timings);
			intent.putExtra("BusStop", busStop);
			intent.putExtra("Route", route);
			startActivity(intent);
			
		}

	

	

}
