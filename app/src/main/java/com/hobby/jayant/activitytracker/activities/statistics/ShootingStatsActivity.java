package com.hobby.jayant.activitytracker.activities.statistics;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hobby.jayant.activitytracker.R;
import com.hobby.jayant.activitytracker.activities.MainActivity;
import com.hobby.jayant.activitytracker.activities.SigninActivity;
import com.hobby.jayant.activitytracker.custom.CustomEntry;
import com.hobby.jayant.activitytracker.custom.MyMarkerView;
import com.hobby.jayant.activitytracker.models.Shooting;
import com.hobby.jayant.activitytracker.services.ActivityTrackerService;
import com.hobby.jayant.activitytracker.services.ShootingActService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShootingStatsActivity extends AppCompatActivity {
    private String basicAuthToken;
    private LineChart mChart;
    private float yMax=0f;
    private ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooting_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences usersettings = getSharedPreferences(SigninActivity.PREFS_NAME, 0);
        basicAuthToken = usersettings.getString(SigninActivity.BASIC_AUTH_TOKEN,null);

        initActivity();

    }
    void initActivity(){

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);

        progressDialog.setMessage("Fetching stats.");
        progressDialog.show();

        ShootingActService shootingActivityService  = ActivityTrackerService.getShootingActivityService(basicAuthToken);
        Call<List<Shooting>> shootings =  shootingActivityService.findAllActivities();

        shootings.enqueue(new Callback<List<Shooting>>() {
            @Override
            public void onResponse(Call<List<Shooting>> call, Response<List<Shooting>> response) {


                List<Shooting>shootingList = response.body();
                if(shootingList!=null && shootingList.size()>0){
                    initGraph(shootingList);
                }else{
                    displayToast("No stats to display!");
                }
                progressDialog.dismiss();

            }
            @Override
            public void onFailure(Call<List<Shooting>> call, Throwable t) {
                progressDialog.dismiss();
                displayToast("Something went wrong!");
            }
        });



    }
    void initGraph(List<Shooting>shootingListData){

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mChart = (LineChart) findViewById(R.id.chart);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setViewPortOffsets(0f, 0f, 0f, 0f);

        setShootingData(shootingListData);
        mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        l.setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        //xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        //xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // one day

        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM");

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                long millis = TimeUnit.DAYS.toMillis((long) value);
                //long millis = (long) value;
                Date date = new Date(millis);
                return mFormat.format(date);
            }
        });

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        //leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        //leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMinimum(-10f);
        //leftAxis.setAxisMaximum(170f);
        leftAxis.setAxisMaximum(yMax+200f);
        leftAxis.setYOffset(-9f);
        //leftAxis.setYOffset(-15f);
        leftAxis.setTextColor(Color.rgb(255, 192, 56));

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        mChart.animateXY(3000, 3000);



        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart
    }
    private void setData(int count, float range) {

        // now in hours
        long now = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());

        ArrayList<Entry> values = new ArrayList<Entry>();

        float from = now;

        // count = hours
        float to = now + count;

        // increment by 1 hour
        for (float x = from; x < to; x++) {

            float y = getRandom(range, 50);
            if(y>yMax){
                yMax = y;
            }
            values.add(new Entry(x, y)); // add one entry per hour
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        // create a data object with the datasets
        LineData data = new LineData(set1);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);
    }

    private void setShootingData(List<Shooting>shootingList){
        ArrayList<Entry> values = new ArrayList<Entry>();


        // now in hours
        long now = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());
        float from = now;
        float x = from;
        // count = hours
        float to = now + shootingList.size();

        for(Shooting shooting:shootingList){
            //float y = TimeUnit.MILLISECONDS.toMinutes(shooting.getDuration());
            float y = shooting.getTotalscore();
            if(y>yMax){
                yMax = y;
            }

            x = TimeUnit.MILLISECONDS.toDays(shooting.getDate());
            //temporary hack as date x axis was 1 day behind
            // remove after finding the bug
            x++;
            Entry e = new Entry(x, y);
            CustomEntry ce;
            if(shooting.getComment()!=null){
                ce = new CustomEntry(MainActivity.PAGETYPE.SHOOTING,shooting.getTotalshots(),x,y,shooting.getComment());
            }else{
                ce = new CustomEntry(MainActivity.PAGETYPE.SHOOTING,shooting.getTotalshots(),x,y);
            }

            values.add(ce);
            // increment by  1 hour
            //x++;
        }



        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(true);
        set1.setDrawValues(true);
        set1.setFillAlpha(65);
        set1.setDrawFilled(true);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
        set1.setDrawFilled(true);

        // create a data object with the datasets
        LineData data = new LineData(set1);
        data.setValueTextColor(Color.BLUE);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);



    }
    private float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }

    private void displayToast(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
    }

}
