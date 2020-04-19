package com.example.viewpagerdemo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.viewpagerdemo.database.Event;
import com.example.viewpagerdemo.database.EventDBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CreateEventActivity";
    private static Date eventDate;
    private String startDateStr;
    private EditText titleEditText;
    private EditText eventDateET;
    private EditText venueEditText;
    private Button createEventButton;
    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create New Event");

        titleEditText = findViewById(R.id.titleET);
        eventDateET = findViewById(R.id.eventDateET);
        venueEditText = findViewById(R.id.venueET);
        createEventButton = findViewById(R.id.createEventButton);

        createEventButton.setOnClickListener(this);
        eventDateET.setOnClickListener(this);
        createEventButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == eventDateET){
            String title = titleEditText.getText().toString().trim();
            String eventDate = eventDateET.getText().toString().trim();
            String venue = venueEditText.getText().toString().trim();

            if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(eventDate) && !TextUtils.isEmpty(venue)){
                long temp_id = 1;
                Event event = new Event(temp_id,title,eventDate,venue,Calendar.getInstance().getTime().toString());

                EventDBHelper databaseHelper = new EventDBHelper(getApplicationContext());
                databaseHelper.createEvent(event);

                Toast.makeText(getApplicationContext(),"Event Created Successfully. Total Events "+databaseHelper.getAllEvents().size(),Toast.LENGTH_LONG).show();

                titleEditText.setText("");
                eventDateET.setText("");
                venueEditText.setText("");
            }
            else{
                Toast.makeText(getApplicationContext(),"Please fill fields to continue",Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void getDate() {
        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
                try {
                    eventDate = sdf.parse(sdf.format(myCalendar.getTime()));
                    startDateStr = sdf.format(myCalendar.getTime());
                    getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        };
        new DatePickerDialog(CreateEventActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

//    private void getDate(){
//        myCalendar = Calendar.getInstance();
//        DatePickerDialog date = new DatePickerDialog(this,null, Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
//
//        new DatePickerDialog(CreateEventActivity.this, (DatePickerDialog.OnDateSetListener) date, myCalendar.get(Calendar.YEAR),
//                myCalendar.get(Calendar.MONTH),
//                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//    }
//
//    @Override
//    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//        myCalendar.set(Calendar.YEAR,year);
//        myCalendar.set(Calendar.MONTH,monthOfYear);
//        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//        String myFormat = "dd/MM/yyyy";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.KOREA);
//        try {
//            eventDate = simpleDateFormat.parse(simpleDateFormat.format(myCalendar.getTime()));
//            startDateStr = simpleDateFormat.format(myCalendar.getTime());
//            getDate();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }

    private void getTime() {
        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                boolean isPM = (hourOfDay >= 12);
                String time = " " + String.format("%02d:%02d:00 %s",
                        (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM");
                eventDateET.setText(startDateStr.concat(time));
            }
        }, hour, minute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
