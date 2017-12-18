package com.hobby.jayant.activitytracker.activities;

import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hobby.jayant.activitytracker.R;
import com.hobby.jayant.activitytracker.fragments.NoteDialogFragment;
import com.hobby.jayant.activitytracker.models.Exercise;
import com.hobby.jayant.activitytracker.models.Shooting;
import com.hobby.jayant.activitytracker.models.User;
import com.hobby.jayant.activitytracker.models.Yoga;
import com.hobby.jayant.activitytracker.services.ActivityTrackerService;
import com.hobby.jayant.activitytracker.services.ExerciseActService;
import com.hobby.jayant.activitytracker.services.ShootingActService;
import com.hobby.jayant.activitytracker.services.UserService;
import com.hobby.jayant.activitytracker.services.YogaActService;


import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSaveActivity extends AppCompatActivity implements View.OnClickListener,NoteDialogFragment.NoticeDialogListener {
    private PopupWindow calendarPopupWindow;
    private Button btnClosePopup;
    private LinearLayout btnAddNote;
    private EditText totalShotsScore;
    private EditText totalShots;
    private TextView calendarText;
    private LinearLayout calendarBtn;
    private LinearLayout saveBtn;
    private LinearLayout noteImprovView;
    private TextView noteImprovText;
    private Calendar myCalendar;
    private EditText timeInput;
    private InputFilter timeFilter;
    private TimePicker timePicker;
    private ScrollView scrollView;
    private boolean doneOnce ;
    private String pageTitle;
    private RatingBar ratingBar;
    private String basicAuthToken;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
    static private final String TAG ="CreateSaveActivity";
    private ProgressBar progressBar;
    private ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
         pageTitle = intent.getStringExtra("pagetitle");

        SharedPreferences usersettings = getSharedPreferences(SiginActivity.PREFS_NAME, 0);
        basicAuthToken = usersettings.getString(SiginActivity.BASIC_AUTH_TOKEN,"null");

        if(MainActivity.PAGETYPE.SHOOTING.toString().equals(pageTitle)){
            setContentView(R.layout.shooting_create_save);
            initShootingTypeActivity();
        }else if(MainActivity.PAGETYPE.YOGA.toString().equals(pageTitle)){
            setContentView(R.layout.yoga_create_save);
            initYogaAndExerciseTypeActivity();
        }else if(MainActivity.PAGETYPE.EXERCISE.toString().equals(pageTitle)){
            setContentView(R.layout.exercise_create_save);
            initYogaAndExerciseTypeActivity();
        }
        setTitle("Add "+pageTitle+" Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initActivity();
    }

    private void initActivity() {
        calendarBtn = (LinearLayout) findViewById(R.id.calendar);
        calendarBtn.setOnClickListener(this);
        saveBtn = (LinearLayout) findViewById(R.id.addItem);
        saveBtn.setOnClickListener(this);
        btnAddNote = (LinearLayout) findViewById(R.id.addNote);
        noteImprovView = (LinearLayout) findViewById(R.id.improvementView);
        noteImprovText = (TextView)noteImprovView.findViewById(R.id.improvementText);
        noteImprovView.setVisibility(View.INVISIBLE);
        btnAddNote.setOnClickListener(this);
        calendarText = (TextView) findViewById(R.id.calendarText);
        scrollView = (ScrollView) findViewById(R.id.scrollContent);
        //progressBar=(ProgressBar)findViewById(R.id.indeterminateBar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
    }

    private void initShootingTypeActivity(){

        totalShotsScore=(EditText) findViewById(R.id.totalScore);
        totalShots=(EditText) findViewById(R.id.totalShot);

    }
    private void initYogaAndExerciseTypeActivity(){
        doneOnce = false;

        timePicker=(TimePicker)findViewById(R.id.timeInputText);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        //Uncomment the below line of code for 24 hour view
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(0);
        timePicker.setCurrentMinute(0);
        ratingBar.setStepSize(1);

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

        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateSaveActivity.this, date,
                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    private void updateLabel() {

        Calendar currentCal = Calendar.getInstance();

        if (isSameDay(currentCal, myCalendar)) {
            calendarText.setText("Today");
        } else {

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
            case R.id.addItem:

                if(MainActivity.PAGETYPE.SHOOTING.toString().equals(pageTitle)){
                    saveShootingActivity();
                }else if(MainActivity.PAGETYPE.YOGA.toString().equals(pageTitle)){
                    saveYogaActivity();
                }else if(MainActivity.PAGETYPE.EXERCISE.toString().equals(pageTitle)){
                    saveExerciseActivity();
                }

                break;
        }
    }

    private void saveShootingActivity(){


        Long dateTime= 0L;
        String dateText = (String) calendarText.getText();

        String comment = (String) noteImprovText.getText();

        boolean validShooting = true;
        StringBuffer msg = new StringBuffer(" Enter a valid ");
        if(dateText== null || "".equals(dateText)){
            msg.append("date, ");
            validShooting = false;
        }else if("Today".equals(dateText)){
            dateTime = System.currentTimeMillis();
        }
        else {

            Date date = null;
            try {
                date = sdf.parse(dateText);
                dateTime = date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                validShooting = false;
            }
        }

        float totalScores = Float.valueOf(totalShotsScore.getText().toString());
        int totalShotsFired = Integer.parseInt(totalShots.getText().toString());

        if(validShooting){

            Shooting shootingActivity;
            if("".equals(comment)){
                shootingActivity = new Shooting(totalScores,totalShotsFired,dateTime);
            }else{
                shootingActivity = new Shooting(totalScores,totalShotsFired,dateTime,comment);
            }



            ShootingActService shootingActService  = ActivityTrackerService.getShootingActivityService(basicAuthToken);
            Call<Void> userloginCall =  shootingActService.saveActivity(shootingActivity);

            showProgress("Creating Shooting activity...");
            userloginCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    response.headers().toString();
                    Headers header = response.headers();
                    if(response.code()==  HttpURLConnection.HTTP_CREATED){
                        displayToast("ShootingStatsActivity created");
                        goBackToMainActivity();
                        dismissProgress();

                    }else if(response.code()==  HttpURLConnection.HTTP_UNAUTHORIZED){
                        displayToast("Invalid Credentials . Try again!"+header.toString());
                        Log.d(TAG, " Invalid Credentials . Try again!  "+response.toString() +header.toString());
                    }
                    else{
                        // showProgress(false);
                        displayToast("Try again!"+header.toString());
                        Log.d(TAG, " Invalid Credentials . Try again!  "+response.toString() +header.toString());
                        dismissProgress();
                    }


                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                    displayToast("Failed " + t.getMessage());
                    dismissProgress();
                }
            });


        }else{
            displayToast(msg.toString());
        }


    }
    private void saveExerciseActivity(){


        Long dateTime= 0L;
        String dateText = (String) calendarText.getText();
        int hours = timePicker.getCurrentHour();
        int minutes = timePicker.getCurrentMinute();

        Long duration = 0L;
        if(hours !=0 || minutes != 0){

            duration = TimeUnit.SECONDS.toMillis(TimeUnit.HOURS.toSeconds(hours) + TimeUnit.MINUTES.toSeconds(minutes));
        }
        int rating = (int) ratingBar.getRating();
        String comment = (String) noteImprovText.getText();

        boolean validExercise = true;
        StringBuffer msg = new StringBuffer(" Enter a valid ");
        if(dateText== null || "".equals(dateText)){
            msg.append("date, ");
            validExercise = false;
        }else if("Today".equals(dateText)){
            dateTime = System.currentTimeMillis();
        }
        else{

            Date date = null;
            try {
                date = sdf.parse(dateText);
                dateTime = date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                validExercise = false;
            }

        }
        if(duration == 0L){
            msg.append("time spend, ");
            validExercise = false;
        }
        if(rating == 0){
            msg.append("rating.");
            validExercise = false;
        }
        if(validExercise){

            Exercise exerciseActivity;
            if("".equals(comment)){
                exerciseActivity = new Exercise(dateTime,duration,rating);
            }else{
                exerciseActivity = new Exercise(dateTime,duration,rating,comment);
            }



            ExerciseActService userService  = ActivityTrackerService.getExerciseActivityService(basicAuthToken);
            Call<Void> userloginCall =  userService.saveActivity(exerciseActivity);

            showProgress("Creating Exercise activity...");
            userloginCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    response.headers().toString();
                    Headers header = response.headers();
                    if(response.code()==  HttpURLConnection.HTTP_CREATED){
                        displayToast("ExerciseStatsActivity created");
                        goBackToMainActivity();
                        dismissProgress();

                    }else if(response.code()==  HttpURLConnection.HTTP_UNAUTHORIZED){
                        displayToast("Invalid Credentials . Try again!"+header.toString());
                        Log.d(TAG, " Invalid Credentials . Try again!  "+response.toString() +header.toString());
                    }
                    else{
                        // showProgress(false);
                        displayToast("Try again!"+header.toString());
                        Log.d(TAG, " Invalid Credentials . Try again!  "+response.toString() +header.toString());
                        dismissProgress();
                    }


                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                    displayToast("Failed " + t.getMessage());
                    dismissProgress();
                }
            });


        }else{
            displayToast(msg.toString());
        }


    }
    private void saveYogaActivity(){
        Long dateTime= 0L;
        String dateText = (String) calendarText.getText();
        int hours = timePicker.getCurrentHour();
        int minutes = timePicker.getCurrentMinute();

        Long duration = 0L;
        if(hours !=0 || minutes != 0){

            duration = TimeUnit.SECONDS.toMillis(TimeUnit.HOURS.toSeconds(hours) + TimeUnit.MINUTES.toSeconds(minutes));
        }
        int rating = (int) ratingBar.getRating();
        String comment = (String) noteImprovText.getText();

        boolean validYoga = true;
        StringBuffer msg = new StringBuffer(" Enter a valid ");
        if(dateText== null || "".equals(dateText)){
            msg.append("date, ");
            validYoga = false;
        }else if("Today".equals(dateText)){
            dateTime = System.currentTimeMillis();
        }
        else{

            Date date = null;
            try {
                date = sdf.parse(dateText);
                dateTime = date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                validYoga = false;
            }

        }
        if(duration == 0L){
            msg.append("time spend, ");
            validYoga = false;
        }
        if(rating == 0){
            msg.append("rating.");
            validYoga = false;
        }
        if(validYoga){

            Yoga yogaactivity;
            if("".equals(comment)){
                 yogaactivity = new Yoga(dateTime,duration,rating);
            }else{
                yogaactivity = new Yoga(dateTime,duration,rating,comment);
            }



            YogaActService userService  = ActivityTrackerService.getYogaActivityService(basicAuthToken);
            Call<Void> userloginCall =  userService.saveActivity(yogaactivity);

            showProgress("Creating Yoga activity...");
            userloginCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    response.headers().toString();
                    Headers header = response.headers();
                    if(response.code()==  HttpURLConnection.HTTP_CREATED){
                        //User user =  response.body();
                        //displayToast("LoginDone "+user);
                        //showMainPageAfterLogin(user);
                        displayToast("YogaStatsActivity created");
                        goBackToMainActivity();
                        dismissProgress();

                    }else if(response.code()==  HttpURLConnection.HTTP_UNAUTHORIZED){
                        displayToast("Invalid Credentials . Try again!"+header.toString());
                        Log.d(TAG, " Invalid Credentials . Try again!  "+response.toString() +header.toString());
                    }
                    else{
                        // showProgress(false);
                        displayToast("Try again!"+header.toString());
                        Log.d(TAG, " Invalid Credentials . Try again!  "+response.toString() +header.toString());
                        dismissProgress();
                    }


                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    //final TextView textView = (TextView) findViewById(R.id.textView);
                    //textView.setText("Something went wrong: " + t.getMessage());
                    displayToast("Failed " + t.getMessage());
                    //showProgress(false);
                    dismissProgress();
                }
            });


        }else{
            displayToast(msg.toString());
        }


    }
        private void showProgress(String msg){

            progressDialog.setMessage(msg);
            progressDialog.show();
        }
        private void dismissProgress(){
            progressDialog.dismiss();
        }
        private void goBackToMainActivity(){
            Intent myIntent = new Intent(CreateSaveActivity.this, MainActivity.class);
            CreateSaveActivity.this.startActivity(myIntent);
        }
    private void displayToast(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
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


