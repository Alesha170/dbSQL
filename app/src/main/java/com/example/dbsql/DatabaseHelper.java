package com.example.dbsql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance;

    private static final String NAME = "Article";
    private static final int VERSION = 1;

    public static void createInstance(final Context context) {
        instance = new DatabaseHelper(context);
    }

    public static DatabaseHelper getInstance() {
        return instance;
    }

    private DatabaseHelper(final Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Article.TABLE_NAME + "(" +
                "_id integer primary key autoincrement, " +
                Article.COLUMN_ARTICLE_NAME + ", " +
                Article.COLUMN_ARTICLE_TEXT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //do not do anything
    }
}
