package com.example.viewpagerdemo.database;

public class EventDBSchema {
    public static final String TABLE_NAME = "events";

    public static final class Cols{
        public static final String ID="id";
        public static final String TITLE = "title";
        public static final String EVENT_DATE = "event_date";
        public static final String VENUE = "venue";
        public static final String DATE_CREATED = "dateCreated";
    }

    public static final String CREATE_EVENTS_TABLE =
            "CREATE TABLE"+TABLE_NAME+"("
            +Cols.ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +Cols.TITLE+" VARCHAR,"
            +Cols.EVENT_DATE+" VARCHAR"
            +Cols.VENUE+" VARCHAR,"
            +Cols.DATE_CREATED+" VARCHAR"
            +")";


}
