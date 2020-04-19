package com.example.viewpagerdemo.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class EventDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "eventBase.db";

    public EventDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(EventDBSchema.CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EventDBSchema.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Long createEvent(Event event) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EventDBSchema.Cols.TITLE, event.getTitle());
        values.put(EventDBSchema.Cols.EVENT_DATE, event.getEventDate());
        values.put(EventDBSchema.Cols.VENUE, event.getVenue());
        values.put(EventDBSchema.Cols.DATE_CREATED, event.getDateCreated());

        long id = sqLiteDatabase.insert(EventDBSchema.TABLE_NAME, null, values);
        sqLiteDatabase.close();
        return id;
    }


    public Event getEventByID(long id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(EventDBSchema.TABLE_NAME, new String[]{
                        EventDBSchema.Cols.ID,
                        EventDBSchema.Cols.TITLE,
                        EventDBSchema.Cols.EVENT_DATE,
                        EventDBSchema.Cols.VENUE,
                        EventDBSchema.Cols.DATE_CREATED},

                EventDBSchema.Cols.ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Event event = new Event(
                Long.parseLong(cursor.getString(cursor.getColumnIndex(EventDBSchema.Cols.ID))),
                cursor.getString(cursor.getColumnIndex(EventDBSchema.Cols.TITLE)),
                cursor.getString(cursor.getColumnIndex(EventDBSchema.Cols.EVENT_DATE)),
                cursor.getString(cursor.getColumnIndex(EventDBSchema.Cols.VENUE)),
                cursor.getString(cursor.getColumnIndex(EventDBSchema.Cols.DATE_CREATED)));

        cursor.close();
        return event;
    }


    public ArrayList<Event> getAllEvents(){
        ArrayList<Event> noteArrayList = new ArrayList<>();

        String selectQuery = "SELECT * FROM "
                +EventDBSchema.TABLE_NAME
                +" ORDER BY "
                +EventDBSchema.Cols.EVENT_DATE
                +" DESC";

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                Event event = new Event();
                event.setId(Long.parseLong(cursor.getString(cursor.getColumnIndex(EventDBSchema.Cols.ID))));
                event.setTitle(cursor.getString(cursor.getColumnIndex(EventDBSchema.Cols.TITLE)));
                event.setEventDate(cursor.getString(cursor.getColumnIndex(EventDBSchema.Cols.EVENT_DATE)));
                event.setVenue(cursor.getString(cursor.getColumnIndex(EventDBSchema.Cols.VENUE)));
                event.setDateCreated(cursor.getString(cursor.getColumnIndex(EventDBSchema.Cols.DATE_CREATED)));
                noteArrayList.add(event);
                event.toString();
            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return noteArrayList;
    }

    public void deleteEvent(Event event){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(EventDBSchema.TABLE_NAME, EventDBSchema.Cols.ID+" = ?",
                new String[]{String.valueOf(event.getId())});
        sqLiteDatabase.close();
    }
}
