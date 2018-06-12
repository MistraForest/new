package com.getingstarted.pogba.mydatetimepicker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeActivity extends AppCompatActivity {

    private Calendar myCalendar;
    private EditText dateEditText ;
    private EditText time;
    private DatePickerDialog.OnDateSetListener myDate;
    private TimePickerDialog.OnTimeSetListener myTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);

        myCalendar = Calendar.getInstance();


        dateEditText = (EditText)findViewById(R.id.date_id);
        time = (EditText) findViewById(R.id.time_id);

        // Date
        myDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //String myDateFormat = "DD/MM/YY";
                //SimpleDateFormat sdf = new SimpleDateFormat(myDateFormat, Locale.GERMAN);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //dateEditText.setText(sdf.format(myCalendar.getTime()));
               updateDate();
            }
        };

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(DateTimeActivity.this, myDate,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });



        // Time
        myTime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateTime();
            }
        };

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(DateTimeActivity.this, myTime,
                        myCalendar.get(Calendar.HOUR_OF_DAY),myCalendar.get(Calendar.MINUTE), true);
                tpd.show();
            }
        });
    }

    private void updateTime(){
        String myTimeFormat = getString(R.string.hours_minutes);
        SimpleDateFormat sdf = new SimpleDateFormat(myTimeFormat, Locale.GERMANY);
        time.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateDate(){
        String myDateFormat = getString(R.string.day_month_year);
        SimpleDateFormat sdf = new SimpleDateFormat(myDateFormat, Locale.GERMAN);
        //dateEditText.setText(sdf.format(myCalendar.getTime()));
        Toast t ;
        Calendar c = Calendar.getInstance();
        if (myCalendar.getTime().before(c.getTime())){
           // dateEditText.setText("");
            t = Toast.makeText(this, "Date is not correct: The date must not be in the pass", Toast.LENGTH_LONG);
            t.show();
        }
        else if ((myCalendar.get(Calendar.YEAR) == (c.get(Calendar.YEAR)) &&
                myCalendar.get(Calendar.MONTH) == (c.get(Calendar.MONTH)) &&
                myCalendar.get(Calendar.DAY_OF_MONTH) == (c.get(Calendar.DAY_OF_MONTH))) ||
                (myCalendar.getTime().after(c.getTime()))){

            dateEditText.setText(sdf.format(myCalendar.getTime()));
            t = Toast.makeText(this, "Date is correct ", Toast.LENGTH_LONG);
            t.show();
            String s = myCalendar.getTime().toString();
            Log.i("Time",s);
        }

    }

    public String getDateString(String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.GERMANY);

        return sdf.format(myCalendar.getTime());
    }

    public long pickDateInMillis(String dateStr, String pattern_dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern_dateStr);
        long dateMillis = 0;
        try {
            Date mydate = sdf.parse(dateStr);
            dateMillis = mydate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateMillis;
    }

}
