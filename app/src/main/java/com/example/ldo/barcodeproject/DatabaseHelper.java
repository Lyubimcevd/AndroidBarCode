package com.example.ldo.barcodeproject;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.content.Context;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH; // полный путь к базе данных
    private static final String DATABASE_NAME = "scancodestore.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "scancode"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_USERNAME = "nameUser";
    public static final String COLUMN_DATE = "dtCreate";
    private Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        this.myContext=context;
        DB_PATH =context.getFilesDir().getPath() + DATABASE_NAME;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE + " (" + COLUMN_CODE
                + " TEXT," + COLUMN_USERNAME
                + " TEXT, " + COLUMN_DATE + " TEXT);");
        // добавление начальных данных
      /*  db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_YEAR  + ") VALUES ('Том Смит', 1981);");*/
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
    public SQLiteDatabase open()throws SQLException {

        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}