package com.learn.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbOperator extends SQLiteOpenHelper{

    private static final String DBNAME = "class2.db";
    private static final int VERSION = 1;


    public DbOperator(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuffer sb = new StringBuffer();
        sb.append("create table ").append(" items ").append(" ( ")
                .append(" id ").append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(" description ").append(" TEXT, ")
                .append(" full_image_path ").append(" TEXT, ")
                .append(" thumb_img_path ").append(" TEXT ")
                .append(" );");

        db.execSQL(sb.toString());
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long saveItem(String description, String allImgPath, String thumbImgPath) {


        ContentValues contentValues = new ContentValues();
        contentValues.put("description", description);
        contentValues.put("full_image_path", allImgPath);
        contentValues.put("thumb_img_path", thumbImgPath);

        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert("items", null, contentValues);
        db.close();
        return result;


    }
}
