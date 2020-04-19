package com.example.viewpagerdemo.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.viewpagerdemo.database.EventDBSchema;

public class EventCursorWrapper extends CursorWrapper {

    public EventCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Event getEvent(){
        long id = getLong(getColumnIndex(EventDBSchema.Cols.ID));
        String title = getString(getColumnIndex(EventDBSchema.Cols.TITLE));
        String eventDate = getString(getColumnIndex(EventDBSchema.Cols.EVENT_DATE));
        String venue = getString(getColumnIndex(EventDBSchema.Cols.VENUE));
        String dateCreated = getString(getColumnIndex(EventDBSchema.Cols.DATE_CREATED));
        return null;
    }
}
