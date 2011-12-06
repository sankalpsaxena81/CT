package com.ct;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CTActivity extends Activity implements OnClickListener  {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btn1=(Button)this.findViewById(R.id.button1);
        Button btn2=(Button)this.findViewById(R.id.button2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        // run query
        
        //set result
    }


	private void CreateDB() {
		DataBaseHelper myDbHelper;
        myDbHelper = new DataBaseHelper(this);
 
        try {
 
        	myDbHelper.createDataBase();
 
 	} catch (IOException ioe) {
 
 		throw new Error("Unable to create database");
 
 	}
 
 	try {
 
 		myDbHelper.openDataBase();
 
 	}catch(SQLException sqle){
 
 		throw sqle;
 
 	}
		
	}

	private void searchByStopCode() {
		TextView tv=(TextView) this.findViewById(R.id.editText1);
		String busStop=tv.getText().toString();
		busStop= busStop.trim();
		String serviceId="1";
		int dayOfWeek=Calendar.DAY_OF_WEEK;
//		if(dayOfWeek==1)
//			serviceId=3;
//		else if(dayOfWeek==7)
//			serviceId=2;
		
		
        SQLiteDatabase db=openOrCreateDatabase("CalgaryTransit", MODE_PRIVATE, null);
        
        ArrayList<String>routes=DatabaseQueries.searchRoutesByStopCode(busStop, serviceId, db);
		//tv.setText(result);
		Intent intent = new Intent(getBaseContext(), RoutesAtAStopActivity.class);
		intent.putExtra("Routes", routes);
		intent.putExtra("BusStop", busStop);
		startActivity(intent);
		
		
	}
	
	private void searchByRouteNo() {
		TextView tv=(TextView) this.findViewById(R.id.editText1);
		String routeNo=tv.getText().toString();
		routeNo= routeNo.trim();
		String serviceId="1";
		int dayOfWeek=Calendar.DAY_OF_WEEK;
//		if(dayOfWeek==1)
//			serviceId=3;
//		else if(dayOfWeek==7)
//			serviceId=2;
		
//		
		SQLiteDatabase db=openOrCreateDatabase("CalgaryTransit", MODE_PRIVATE , null);  	
		ArrayList<String>busStops=DatabaseQueries.searchBusStopsByRouteNo(routeNo, serviceId,db);
		
		Intent intent = new Intent(getBaseContext(), StopsOnARouteActivity.class);
		intent.putExtra("BusStops", busStops);
		intent.putExtra("Route", routeNo);
		startActivity(intent);
	}


	public void onClick(View v) {
		if(v.getId()==R.id.button1){
			CreateDB();
			
		}
		
		if(v.getId()==R.id.button2){
			TextView tv=(TextView) this.findViewById(R.id.editText1);
			if(tv.getText().toString().length()>3){
			searchByStopCode();
			}
			else{
				searchByRouteNo();
			}
			
		}
		
	}
}