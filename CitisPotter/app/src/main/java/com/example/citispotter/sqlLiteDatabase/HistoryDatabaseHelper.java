package com.example.citispotter.sqlLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.citispotter.Model.HistoryClass;

import java.util.ArrayList;
import java.util.List;

public class HistoryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="HistoryDB.db";
    private static final String TABLE_NAME="HistoryDB_Table";
    private static final String COL_1="ID";
    private static final String COL_2="TITLE";
    private static final String COL_3="DATE";
    private static final String COL_4="LASTTIME";
    private static final String COL_5="NOOFTIMES";
    private static final String COL_6="IMAGE";
    public HistoryDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TITLE TEXT,DATE TEXT,LASTTIME TEXT,NOOFTIMES INTEGER,IMAGE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public boolean Insert(HistoryClass data ){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(COL_2,data.getTitle());
        contentValues.put(COL_3,data.getDate());
        contentValues.put(COL_4,data.getLasttime());
        contentValues.put(COL_5,data.getNooftime());
        contentValues.put(COL_6,data.getImage());
        long result=db.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;

    }
    public void delete(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("DELETE TABLE "+TABLE_NAME,null,null);
    }

    public boolean checkTitle(String title){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT TITLE FROM "+TABLE_NAME+" where TITLE=?",new String[]{title});

        if (cursor.getCount()>0)
            return false;
        else
            return true;
    }


    public boolean updateNoftimes(String title){
        SQLiteDatabase db=this.getWritableDatabase();
        int nooftimes=0;
        String Title="";
        Cursor cursor=db.rawQuery("SELECT NOOFTIMES,TITLE FROM "+TABLE_NAME+" where TITLE=?",new String[]{title});
        if (cursor.moveToNext()){
            Title=cursor.getString(cursor.getColumnIndex(COL_2));
            nooftimes=cursor.getInt(cursor.getColumnIndex(COL_5));
        }
        long result;
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_5,nooftimes+1);
        result=db.update(TABLE_NAME,contentValues,"TITLE = ?",new String[]{Title});
        if (result==-1)
            return false;
        else
            return true;

    }
    public List<HistoryClass> getAllData(){
        List<HistoryClass> list=new ArrayList<>();
        //Select all query
        String selectquery="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectquery,null);
        if (cursor.moveToNext()){
            do {
                HistoryClass data=new HistoryClass();
                data.setTitle(cursor.getString(cursor.getColumnIndex(COL_2)));
                data.setDate(cursor.getString(cursor.getColumnIndex(COL_3)));
                data.setLasttime(cursor.getString(cursor.getColumnIndex(COL_4)));
                data.setNooftime(cursor.getInt(cursor.getColumnIndex(COL_5)));
                data.setImage(cursor.getString(cursor.getColumnIndex(COL_6)));
                list.add(data);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
