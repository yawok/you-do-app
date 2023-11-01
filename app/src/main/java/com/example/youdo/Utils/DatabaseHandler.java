package com.example.youdo.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.youdo.Model.YouDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NAME = "youdodb";
    private static final String YOUDO_TABLE = "youdo";
    private static final String TASK = "task";
    private static final String ID = "id";
    private static final String STATUS = "status";
    private static final String CREATE_YOUDO_TABLE = "CREATE TABLE " + YOUDO_TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TASK + " TEXT, "
            + STATUS + " INTEGER)";
    private SQLiteDatabase db;


    public DatabaseHandler(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_YOUDO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop old table
        db.execSQL("DROP TABLE IF EXISTS " + YOUDO_TABLE);
        // Create new table
        onCreate(db);
    }

    public void openDB(){
        db = this.getWritableDatabase();
    }

    public void insertTask(YouDoModel task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0);
        db.insert(YOUDO_TABLE, null, cv);
    }

    @SuppressLint("Range")
    public List<YouDoModel> getAllTask() {
        List<YouDoModel> tasksList = new ArrayList<>();
        Cursor c = null;
        db.beginTransaction();
        try {
            c = db.query(YOUDO_TABLE, null, null, null, null, null, null);
            if (c != null) {
                if(c.moveToFirst()) {
                    do {
                        YouDoModel task = new YouDoModel();
                        task.setId(c.getInt(c.getColumnIndex(ID)));
                        task.setTask(c.getString(c.getColumnIndex(TASK)));
                        task.setStatus(c.getInt(c.getColumnIndex(STATUS)));
                        tasksList.add(task);
                    } while(c.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            c.close();
        }

        return tasksList;
    }

    public void updateStatus (int id, int status) {
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(YOUDO_TABLE, cv, ID + " =? ", new String[] {String.valueOf(id)});

    }

    public void updateTask (int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(YOUDO_TABLE, cv, ID + " =? ", new String[] {String.valueOf(id)});
    }

    public void deleteTask (int id) {
        db.delete(YOUDO_TABLE, ID + " =? ", new String[]{String.valueOf(id)});
    }
}
