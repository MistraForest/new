package com.getingstarted.pogba.todosqlite;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Locale.GERMAN;

public class MainActivity extends AppCompatActivity {

    private EditText todoName;
    private EditText description;
    private Switch isDone;
    private CheckBox isFavourite;
    private EditText datePicker;
    private EditText timePicker;

    private DatePickerDialog.OnDateSetListener myDate;
    private TimePickerDialog.OnTimeSetListener myTime;

    private Button putButton;
    private Button countButton;

    private static final String TAG_ = MainActivity.class.getName();
    private final Calendar myDateCalendar = Calendar.getInstance();
    private final Calendar myTimeCalendar = Calendar.getInstance();

    private long dateMillis;
    private long timeMillis;


    private DataBaseHandler db = new DataBaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        todoName = (EditText) findViewById(R.id.name_id);
        description = (EditText) findViewById(R.id.description_id);
        isDone = (Switch)findViewById(R.id.status_id);
        isFavourite = (CheckBox)findViewById(R.id.favourite_check);
        datePicker = (EditText) findViewById(R.id.date_id);
        timePicker = (EditText) findViewById(R.id.time_id);

        putButton = (Button) findViewById(R.id.button_put_id);
        countButton = (Button)findViewById(R.id.button_count_id);

        // Pick date and time
        myDatePicker();
        myTimePicker();

        // Insert some Todo


        putButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * CRUD OPERATIONS
                 *
                 * */
                //Inserting Todo
                Log.d(TAG_,"Inserting...");
                putButtonClicked();
            }
        });

        countButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                listAllTodos();
               /* putButton.setText("DELETE");
                MyTodo t =new MyTodo(todoName.getText().toString(),description.getText().toString(),isDoneValue(),isFavouriteValue(),dateMillis,timeMillis);
                db.deleteEntry(t);*/
                return true;
            }
        });

        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countAllTodos();
            }
        });



        db.close();

    }

    public int isFavouriteValue(){
        int valueOf_isFavourite;
        if (isFavourite.isChecked()){
            valueOf_isFavourite = Integer.parseInt(MyTodo.YES);
        }else {
            valueOf_isFavourite = Integer.parseInt(MyTodo.NO);
        }

        return  valueOf_isFavourite;
    }

    public int isDoneValue(){
        int valueOf_isDone;

        if (isDone.isChecked()){
            valueOf_isDone = Integer.parseInt(MyTodo.YES);
        }else {
            valueOf_isDone = Integer.parseInt(MyTodo.NO);
        }
        return valueOf_isDone;
    }

    public  void putButtonClicked(){

        Log.i(TAG_, "putButtonClicked  was pressed...");

        MyTodo newTodo = new MyTodo();

        // EditText Fields
        String name = todoName.getText().toString();
        String des = description.getText().toString();

        //Switch and Checkbox Fields
        int valueIsDone = isDoneValue();
        int valueIsFavourite = isFavouriteValue();

        // Date picker and Time picker fields
        dateMillis = pickInMillis(getDateString(getString(R.string.day_month_year),
                myDateCalendar),getString(R.string.day_month_year));
        timeMillis = pickInMillis(getDateString(getString(R.string.hours_minutes),
                myTimeCalendar),getString(R.string.hours_minutes));

        // Set todo details from UI
        if (!(name.matches("")|| des.matches("") || datePicker.getText().toString().matches("") || timePicker.getText().toString().matches(""))){

            newTodo.setName(name);
            newTodo.setDescription(des);
            newTodo.setIsDone(valueIsDone);
            newTodo.setFavourite(valueIsFavourite);
            newTodo.setDueDate(dateMillis);
            newTodo.setTime(timeMillis);

       /* Log.d(TAG_, "Creating entry details....");
        Log.i(TAG_, "ID: "+newTodo.getId());
        Log.i(TAG_, "NAME: "+newTodo.getName());
        Log.i(TAG_, "DESCRIPTION: "+newTodo.getDescription());
        Log.i(TAG_, "IS_DONE: "+newTodo.getIsDone());
        Log.i(TAG_, "IS_FAVOURITE: "+newTodo.getFavourite());
        Log.i(TAG_, "DATE: "+newTodo.getDueDate());
        Log.i(TAG_, "TIME: "+newTodo.getTime());*/

            Log.i(TAG_,"Creating was successful");
            db.addEntry(newTodo);
            //listAllContacts();

            Toast.makeText(this,"Todo "+ newTodo.getName()+" is added",Toast.LENGTH_SHORT).show();
            todoName.setText("");
            description.setText("");
            datePicker.setText("");
            timePicker.setText("");

        }else {
            Toast.makeText(this,"Fill the fields to add a Todo", Toast.LENGTH_SHORT).show();
        }



    }

    public void listAllTodos(){
        int i = 0;
        List<MyTodo> allEntries = db.getEntries();
        for (MyTodo c : allEntries){
            String all = "\tId: " + c.getId() + "||\tName: " + c.getName()
                    + "||\tDescription: " + c.getDescription()+ "||\tIs done: "+c.getIsDone()
                    +"||\tIs favourite: "+c.getFavourite()+"||\tDate: "+c.getDueDate()
                    +"||\tTime: "+c.getTime();

            i++;
            Log.d("Todo " + i +":", all);
        }
        Toast.makeText(this,"TODOs are listed in Android Monitor", Toast.LENGTH_SHORT ).show();
    }

    public void  countAllTodos(){
        db.countEntries();
        StringBuilder sb = new StringBuilder();
        sb.append(db.countEntries());
        Toast.makeText(this,"Count="+ sb,Toast.LENGTH_LONG).show();
    }


    // Pick the Date from the DatePickerDialog
    // and set it
    public void myDatePicker(){


        // Date
        myDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    myDateCalendar.set(Calendar.YEAR, year);
                    myDateCalendar.set(Calendar.MONTH, month);
                    myDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    update(datePicker, getString(R.string.day_month_year), myDateCalendar);
            }
        };

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, myDate,
                        myDateCalendar.get(Calendar.YEAR),
                        myDateCalendar.get(Calendar.MONTH),
                        myDateCalendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });
    }



    public String getDateString(String pattern, Calendar calendar){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.GERMAN);

        return sdf.format(calendar.getTime());
    }

    // Pick time from TimePickerDialog
    public void myTimePicker(){

        // Time
        myTime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myTimeCalendar.set(Calendar.MINUTE, minute);
                update(timePicker, getString(R.string.hours_minutes), myTimeCalendar);
            }
        };

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(MainActivity.this, myTime,
                        myTimeCalendar.get(Calendar.HOUR_OF_DAY),myTimeCalendar.get(Calendar.MINUTE), true);
                tpd.show();
            }
        });

    }


    public long pickInMillis(String dateStr, String pattern_dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern_dateStr, Locale.GERMANY);
        long dateMillis = 0;
        try {
            Date date = sdf.parse(dateStr);
            dateMillis = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateMillis;
    }


    /**
     * Updating date and time from an EditText-view
     * using the corresponding pattern
     *
     * @param txtField : the EditText view
     * @param format: the corresponding pattern to display the Date or Time
     **/
    public void update(EditText txtField, String format, Calendar calendar){
        SimpleDateFormat sdf = new SimpleDateFormat(format, GERMAN);
//        if (!(txtField.getText().toString().matches(""))){
            txtField.setText(sdf.format(calendar.getTime()));
//        }else {
//            Toast.makeText(this,"Time or date field is empty", Toast.LENGTH_SHORT ).show();
//
//        }

    }



}
