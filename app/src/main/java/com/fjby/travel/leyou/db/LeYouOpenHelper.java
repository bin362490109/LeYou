package com.fjby.travel.leyou.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class LeYouOpenHelper extends SQLiteOpenHelper {
    public static final String CREATE_PROVINCE="create table province(province text,province_code text)";
    public static final String CREATE_CITY="create table city(city text,city_code text,province_code integer)";
    public static final String CREATE_COUNTY="create table county(id integer primary key autoincrement,county_name text,county_code text,city_id integer)";
	
	public LeYouOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//
		db.execSQL(CREATE_PROVINCE);
		db.execSQL(CREATE_CITY);
	//	db.execSQL(CREATE_COUNTY);
        
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
