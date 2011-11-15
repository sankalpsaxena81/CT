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

	private void Search() {
		TextView tv=(TextView) this.findViewById(R.id.editText1);
		String busStop=tv.getText().toString();
		busStop= busStop.trim();
		int serviceId=1;
		int dayOfWeek=Calendar.DAY_OF_WEEK;
//		if(dayOfWeek==1)
//			serviceId=3;
//		else if(dayOfWeek==7)
//			serviceId=2;
		
		String query="select s.arrival_time from stop_times s,stops st"+
				" where s.trip_id IN (  select trip_id from trips t ,routes r"+
				" where r.route_short_name IN( select  r.route_short_name from routes r, trips t ,stops s,stop_times st " +
				" where s.stop_code='"+busStop+"' and s.stop_id=st.stop_id and st.trip_id=t.trip_id and t.route_id=r.route_id )" +
				" and r.route_id=t.route_id and t.service_id='"+serviceId+"' )"  +      		
				" and st.stop_code='"+busStop+"' and st.stop_id=s.stop_id"+
				" and time(s.arrival_time) > time('now','-1 hour','localtime') and time(s.arrival_time) < time('now','+2 hour','localtime')"+
				" order by arrival_time  ";
		
				        SQLiteDatabase db=openOrCreateDatabase("CalgaryTransit", MODE_PRIVATE, null);
				        
				        Cursor cursor=db.rawQuery(query, null);

				        cursor.moveToFirst();
				        String result="";
				        ArrayList<String> stopTimes= new ArrayList<String>();
						 int i=0;
						while (cursor.isAfterLast() == false) {
							String s=cursor.getString(0);
							 //Time time=Time.valueOf(s);
							stopTimes.add(s);
							Log.d("sankalp",s );
							result+=s+"  =>  ";
//							list.add(s);
				      	    cursor.moveToNext();
				       }
						db.close();
						cursor.close();
						//tv.setText(result);
						Intent intent = new Intent(getBaseContext(), StopTimingsActivity.class);
						intent.putExtra("BusStopTimings", stopTimes);
						intent.putExtra("BusStop", busStop);
						startActivity(intent);
						
		
	}


	public void onClick(View v) {
		if(v.getId()==R.id.button1){
			CreateDB();
			
		}
		
		if(v.getId()==R.id.button2){
			Search();
			
		}
		
	}
}