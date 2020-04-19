package com.example.viewpagerdemo.database;

import android.util.Log;

public class Event {
    private long id;
    private String title;
    private String eventDate;
    private String venue;
    private String dateCreated;

    public Event(){

    }

    public Event(long id, String title, String eventDate, String venue, String dateCreated){
        this.id = id;
        this.title = title;
        this.eventDate = eventDate;
        this.venue = venue;
        this.dateCreated = dateCreated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String toString(){
        Log.d("response","Title: "+title);
        return super.toString();
    }
}
