package com.hobby.jayant.activitytracker.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hobby.jayant.activitytracker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateSaveActivity extends AppCompatActivity implements View.OnClickListener {
    private PopupWindow calendarPopupWindow;
    private Button btnClosePopup;
    private LinearLayout calendarBtn;
    private Calendar myCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_save);
        initActivity();
    }
    private void initActivity(){
        calendarBtn = (LinearLayout)findViewById(R.id.calendar);
        calendarBtn.setOnClickListener(this);
    }
    private void initPopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) CreateSaveActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.calendar_popup_window,
                    (ViewGroup) findViewById(R.id.activity_note_detail_popup_element));
            View parentView = (View) findViewById(R.id.activity_create_save);
            calendarPopupWindow = new PopupWindow(layout, parentView.getWidth(), 500, true);
            calendarPopupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

            calendarPopupWindow.setOverlapAnchor(true);


            btnClosePopup = (Button) layout.findViewById(R.id.dateSelectedDone);

            btnClosePopup.setOnClickListener(this);



        } catch (Exception e) {
            e.printStackTrace();
        }


        //Read more: http://www.androidhub4you.com/2012/07/how-to-create-popup-window-in-android.html#ixzz3y9oMOuOl
    }
private void initCalendarDialog(){
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
        TextView calendarText =(TextView)findViewById(R.id.calendarText);
        if(isSameDay(currentCal, myCalendar)){
            calendarText.setText("Today");
        }else{
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
                //initPopupWindow();
                initCalendarDialog();
                break;

            case R.id.dateSelectedDone:
                calendarPopupWindow.dismiss();
                break;
        }
    }
}
