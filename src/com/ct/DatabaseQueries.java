package com.ct;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;

public class DatabaseQueries {
	public static  ArrayList<String> searchBusStopsByRouteNo(String routeNo,String serviceId, SQLiteDatabase db) {
		
		String queryToGetStopsFoARoute="select s.stop_code from stops s where s.stop_id IN"+
			" ( select distinct(st.stop_id) from routes r,stop_times st,trips t where r.route_short_name='"+routeNo+"'"+
			" and r.route_id=t.route_id and t.trip_id=st.trip_id and t.service_id='"+serviceId+"' order by st.stop_sequence)";
				      
				        
        Cursor cursor=db.rawQuery(queryToGetStopsFoARoute, null);

        cursor.moveToFirst();
        String result="";
        ArrayList<String> busStops= new ArrayList<String>();
		 int i=0;
		while (cursor.isAfterLast() == false) {
			String s=cursor.getString(0);
			 //Time time=Time.valueOf(s);
			busStops.add(s);
			Log.d("sankalp",s );
			result+=s+"  =>  ";
//							list.add(s);
      	    cursor.moveToNext();
       }
		db.close();
		cursor.close();
		//tv.setText(result);
				
	return busStops;			
		
	}
	
	public static ArrayList<String> searchRoutesByStopCode(String busStop,String serviceId, SQLiteDatabase db){
		String queryToGetRoutesForAStop=" select  distinct(r.route_short_name) from routes r, trips t ,stops s,stop_times st " +
				" where s.stop_code='"+busStop+"' and s.stop_id=st.stop_id and st.trip_id=t.trip_id and t.route_id=r.route_id ";
				        
        Cursor cursor=db.rawQuery(queryToGetRoutesForAStop, null);

        cursor.moveToFirst();
        String result="";
        ArrayList<String> routes= new ArrayList<String>();
		 int i=0;
		while (cursor.isAfterLast() == false) {
			String s=cursor.getString(0);
			 //Time time=Time.valueOf(s);
			routes.add(s);
			Log.d("sankalp",s );
			result+=s+"  =>  ";
//							list.add(s);
      	    cursor.moveToNext();
       }
		db.close();
		cursor.close();
		return routes;
	}
	
	public static ArrayList<String> searchRouteTimingsAtABusStop(String route,String serviceId,String busStop, SQLiteDatabase db){
		String query="select s.arrival_time from stop_times s,stops st"+
				" where s.trip_id IN (  select trip_id from trips t ,routes r"+
				" where r.route_short_name='"+route+"'"+
				" and r.route_id=t.route_id and t.service_id='"+serviceId+"' )"  +      		
				" and st.stop_code='"+busStop+"' and st.stop_id=s.stop_id"+
				//" and time(s.arrival_time) > time('now','-1 hour','localtime') and time(s.arrival_time) < time('now','+2 hour','localtime')"+
				" order by arrival_time  ";
		 Cursor cursor=db.rawQuery(query, null);

	        cursor.moveToFirst();
	        String result="";
	        ArrayList<String> timings= new ArrayList<String>();
			 int i=0;
			while (cursor.isAfterLast() == false) {
				String s=cursor.getString(0);
				 //Time time=Time.valueOf(s);
				timings.add(s);
				Log.d("sankalp",s );
				result+=s+"  =>  ";
//				list.add(s);
	      	    cursor.moveToNext();
	       }
			db.close();
			cursor.close();
			return timings;
	}
	

}
