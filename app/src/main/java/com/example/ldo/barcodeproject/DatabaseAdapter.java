package com.example.ldo.barcodeproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }
    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_CODE, DatabaseHelper.COLUMN_USERNAME, DatabaseHelper.COLUMN_DATE};
        return  database.query(DatabaseHelper.TABLE, columns, null, null, null, null, null);
    }

    public List<ScanBarCode> getScanBarCodes(){
        ArrayList<ScanBarCode> scanBarCodes = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do{
                String code = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CODE));
                String nameUser = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME));
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                String date_str=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
                Date dtCreate=null;
                try {
                    dtCreate = ft.parse(date_str);
                }
                catch (ParseException e){;}
                scanBarCodes.add(new ScanBarCode(code, nameUser, dtCreate));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  scanBarCodes;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE);
    }
    public ScanBarCode getScanBarCode(String code){
        ScanBarCode scanBarCode = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE, DatabaseHelper.COLUMN_CODE);
        Cursor cursor = database.rawQuery(query, new String[]{code});
        if(cursor.moveToFirst()){
            String nameUser = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME));
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
            String date_str=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
            Date dtCreate=null;
            try {
                dtCreate = ft.parse(date_str);
            }
            catch (ParseException e){;}
            scanBarCode = new ScanBarCode(code, nameUser, dtCreate);
        }
        cursor.close();
        return  scanBarCode;
    }

    public long insert(ScanBarCode scanBarCode){

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_CODE, scanBarCode.getCode());
        cv.put(DatabaseHelper.COLUMN_USERNAME, scanBarCode.getNameUser());
        Date date=scanBarCode.getDtCreate();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        String date_str=ft.format(date);
        cv.put(DatabaseHelper.COLUMN_DATE, date_str);
        return  database.insert(DatabaseHelper.TABLE, null, cv);
    }
    public long delete(String code){

        String whereClause = "code = ?";
        String[] whereArgs = new String[]{code};
        return database.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
    }

    public long update(ScanBarCode scanBarCode){

        String whereClause = DatabaseHelper.COLUMN_CODE + "=" + scanBarCode.getCode();
        ContentValues cv = new ContentValues();
        Date date=scanBarCode.getDtCreate();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        String date_str=ft.format(date);
        cv.put(DatabaseHelper.COLUMN_USERNAME, scanBarCode.getNameUser());
        cv.put(DatabaseHelper.COLUMN_DATE, date_str);
        return database.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }

}