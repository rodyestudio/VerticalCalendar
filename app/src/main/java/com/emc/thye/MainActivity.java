package com.emc.thye;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emc.verticalweekcalendar.VerticalWeekCalendar;
import com.emc.verticalweekcalendar.controller.VerticalWeekAdapter;
import com.emc.verticalweekcalendar.interfaces.DateWatcher;
import com.emc.verticalweekcalendar.interfaces.OnDateClickListener;
import com.emc.verticalweekcalendar.model.CalendarDay;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity {

    private GregorianCalendar selected;
    private VerticalWeekCalendar calendarView;
    private LinearLayout linear_date;
    private TextView txt_date;
    private FloatingActionButton fab_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linear_date = findViewById(R.id.linear_date);
        txt_date = findViewById(R.id.txt_date);
        fab_date = findViewById(R.id.fab_date);

        Calendar calendar = Calendar.getInstance();
        selected = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendarView = new VerticalWeekCalendar.Builder().setView(R.id.verticalCalendar).init(this);

        calendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onCalenderDayClicked(int year, int month, int day) {
                GregorianCalendar selectedDay = new GregorianCalendar(year, month, day);
                if(selected.compareTo(selectedDay) != 0) {
                    selected = selectedDay;
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, month, day);
                    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd hh:mm:ss Z yyyy");
                    Date newDate2 = null;
                    try {
                        newDate2 = format.parse(newDate.getTime().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    format = new SimpleDateFormat("dd MMM yyyy");
                    String date = format.format(newDate2);
                    Toast.makeText(getApplicationContext(), "selected date : " + date, Toast.LENGTH_SHORT).show();

                }
            }
        });

        calendarView.setDateWatcher(new DateWatcher() {
            @Override
            public int getStateForDate(int position, int year, int month, int day, VerticalWeekAdapter.DayViewHolder view) {
                Log.d("position => ", String.valueOf(position));
                return selected.compareTo(new GregorianCalendar(year, month, day)) == 0 ? CalendarDay.SELECTED : CalendarDay.DEFAULT;
            }
        });

        //note: add dialog date picker month and year
        fab_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialogDate();
            }
        });

    }

    private void showDialogDate() {
        final Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog  startTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd hh:mm:ss Z yyyy");
                Date newDate2 = null;
                try {
                    newDate2 = format.parse(newDate.getTime().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                format = new SimpleDateFormat("MMM yyyy");
                String date = format.format(newDate2);
                txt_date.setText(date);

                // note: refresh list date
                selected = new GregorianCalendar(
                        newDate.get(Calendar.YEAR),
                        newDate.get(Calendar.MONTH),
                        newDate.get(Calendar.DAY_OF_MONTH)
                );
                calendarView = new VerticalWeekCalendar.Builder().setView(R.id.verticalCalendar).init(MainActivity.this);

                // note: scroll to item selected
//                calendarView.getAdapter();


            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        startTime.show();
    }

}
