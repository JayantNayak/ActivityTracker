package com.hobby.jayant.activitytracker.activities;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hobby.jayant.activitytracker.R;
import com.hobby.jayant.activitytracker.fragments.NoteDialogFragment;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateSaveActivity extends AppCompatActivity implements View.OnClickListener,NoteDialogFragment.NoticeDialogListener {
    private PopupWindow calendarPopupWindow;
    private Button btnClosePopup;
    private LinearLayout btnAddNote;
    private LinearLayout calendarBtn;
    private LinearLayout noteImprovView;
    private TextView noteImprovText;
    private Calendar myCalendar;
    private EditText timeInput;
    private InputFilter timeFilter;
    private TimePicker timePicker;
    private ScrollView scrollView;
    private boolean doneOnce ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String pageTitle = intent.getStringExtra("pagetitle");
        if(MainActivity.PAGETYPE.SHOOTING.toString().equals(pageTitle)){
            setContentView(R.layout.shooting_create_save);
        }else if(MainActivity.PAGETYPE.YOGA.toString().equals(pageTitle)){
            setContentView(R.layout.yoga_create_save);
            initYogaAndExerciseTypeActivity();
        }else if(MainActivity.PAGETYPE.EXERCISE.toString().equals(pageTitle)){
            setContentView(R.layout.exercise_create_save);
            initYogaAndExerciseTypeActivity();
        }
        setTitle("Add "+pageTitle+" Activity");

        initActivity();
    }

    private void initActivity() {
        calendarBtn = (LinearLayout) findViewById(R.id.calendar);
        calendarBtn.setOnClickListener(this);
        btnAddNote = (LinearLayout) findViewById(R.id.addNote);
        noteImprovView = (LinearLayout) findViewById(R.id.improvementView);
        noteImprovText = (TextView)noteImprovView.findViewById(R.id.improvementText);
        noteImprovView.setVisibility(View.INVISIBLE);
        btnAddNote.setOnClickListener(this);

        scrollView = (ScrollView) findViewById(R.id.scrollContent);
    }
    private void initYogaAndExerciseTypeActivity(){
        doneOnce = false;

        timePicker=(TimePicker)findViewById(R.id.timeInputText);

        //Uncomment the below line of code for 24 hour view
        timePicker.setIs24HourView(true);

    }

    private void initPopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) CreateSaveActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.note_popup_window,
                    (ViewGroup) findViewById(R.id.activity_note_detail_popup_element));
            View parentView = (View) findViewById(R.id.activity_create_save);
            calendarPopupWindow = new PopupWindow(layout, parentView.getWidth(), 500, true);
            calendarPopupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

            //calendarPopupWindow.setOverlapAnchor(true);


            // btnClosePopup = (Button) layout.findViewById(R.id.dateSelectedDone);

            // btnClosePopup.setOnClickListener(this);


        } catch (Exception e) {
            e.printStackTrace();
        }


        //Read more: http://www.androidhub4you.com/2012/07/how-to-create-popup-window-in-android.html#ixzz3y9oMOuOl
    }

    private void initCalendarDialog() {
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        new DatePickerDialog(CreateSaveActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel() {

        Calendar currentCal = Calendar.getInstance();
        TextView calendarText = (TextView) findViewById(R.id.calendarText);
        if (isSameDay(currentCal, myCalendar)) {
            calendarText.setText("Today");
        } else {
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            calendarText.setText(sdf.format(myCalendar.getTime()));
        }

    }

    public boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null)
            return false;
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.calendar:
                initCalendarDialog();
                break;

            case R.id.addNote:
                FragmentManager manager = getFragmentManager();
                Fragment frag = manager.findFragmentByTag("new note");
                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }
                NoteDialogFragment noteDialog = new NoteDialogFragment();
                noteDialog.show(manager, "new note");
                break;
        }
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String noteTextValue) {
       /* Context context = getApplicationContext();
        CharSequence text = noteTextValue;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();*/
        if(noteTextValue == null || "".equals(noteTextValue) ){
            noteImprovView.setVisibility(View.INVISIBLE);
        }else{
            noteImprovView.setVisibility(View.VISIBLE);
        }
        noteImprovText.setText(noteTextValue);
        Runnable r = new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(View.FOCUS_DOWN);
                }
            };
        Handler handler = new Handler();
        handler.postDelayed(r,1000);

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        int j;
    }
}


