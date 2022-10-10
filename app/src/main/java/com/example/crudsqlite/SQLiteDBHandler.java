package com.example.crudsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "country_db";
    private static final String COUNTRY_TABLE = "tbl_country";

    public static final String KEY_ID = "country_id";
    private static final String KEY_NAME = "country_name";
    private static final String KEY_POPULATION = "country_population";

    public SQLiteDBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COUNTRY_TABLE_CMD =
                "CREATE TABLE "+COUNTRY_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_POPULATION + " INT)";
        db.execSQL(CREATE_COUNTRY_TABLE_CMD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_TABLE_CMD = "DROP TABLE IF EXISTS "+COUNTRY_TABLE;
        db.execSQL(DROP_TABLE_CMD);
    }

    void addCountry(CountryModel country){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, country.getName());
        cv.put(KEY_POPULATION, country.getPopulation());

        db.insert(COUNTRY_TABLE, null, cv);
        db.close();
    }

    CountryModel getCountry(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(COUNTRY_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_POPULATION}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) cursor.moveToFirst();
        CountryModel theCountry = new CountryModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getInt(2));
        db.close();
        return theCountry;
    }

    List<CountryModel> getAllCountries(){
        List<CountryModel> theList = new ArrayList<CountryModel>();
        String SELECT_ALL_CMD = "SELECT * FROM "+COUNTRY_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL_CMD, null);

        if (cursor.moveToFirst()){
            do {
                CountryModel theCountry = new CountryModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getInt(2));
                theList.add(theCountry);
            } while (cursor.moveToNext());
        }

        db.close();

        return theList;
    }

    public int updateCountry(CountryModel selectedCountry){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, selectedCountry.getName());
        cv.put(KEY_POPULATION, selectedCountry.getPopulation());

        return db.update(COUNTRY_TABLE, cv,KEY_ID + " = ?", new String[]{String.valueOf(selectedCountry.getId())});


    }

    public void deleteCountry(CountryModel selectedCountry){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COUNTRY_TABLE, KEY_ID + " = ?", new String[]{String.valueOf(selectedCountry.getId())});
        db.close();

    }

    public void deleteAllCountries(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COUNTRY_TABLE, null, null);
        db.close();
    }
}