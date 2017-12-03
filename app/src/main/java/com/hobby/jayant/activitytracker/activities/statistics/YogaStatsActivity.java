package com.hobby.jayant.activitytracker.activities.statistics;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hobby.jayant.activitytracker.R;
import com.hobby.jayant.activitytracker.activities.SiginActivity;
import com.hobby.jayant.activitytracker.models.Yoga;
import com.hobby.jayant.activitytracker.services.ActivityTrackerService;
import com.hobby.jayant.activitytracker.services.YogaActService;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YogaStatsActivity extends AppCompatActivity  {
    private String basicAuthToken;
    private LineChart mChart;
    private float yMax=0f;
    private float xMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences usersettings = getSharedPreferences(SiginActivity.PREFS_NAME, 0);
        basicAuthToken = usersettings.getString(SiginActivity.BASIC_AUTH_TOKEN,"null");

        initActivity();

    }
    void initActivity(){

        YogaActService yogaActivityService  = ActivityTrackerService.getYogaActivityService(basicAuthToken);
        Call<List<Yoga>> yogas =  yogaActivityService.findAllActivities();

        yogas.enqueue(new Callback<List<Yoga>>() {
            @Override
            public void onResponse(Call<List<Yoga>> call, Response<List<Yoga>> response) {


                List<Yoga>yogaList = response.body();
                if(yogaList!=null && yogaList.size()>0){
                    String com=yogaList.toString();
                    displayToast(com);
                    // yoga list should be in ascending order by date otherwise exception is thrown
                   initGraph(yogaList);
                }else{
                    displayToast("no YogaStatsActivity to display");
                }

            }
            @Override
            public void onFailure(Call<List<Yoga>> call, Throwable t) {
                //final TextView textView = (TextView) findViewById(R.id.textView);
                //textView.setText("Something went wrong: " + t.getMessage());
            }
        });



    }
    void initGraph(List<Yoga>yogaListData){

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Calendar calendar = Calendar.getInstance();
        Date d1 = null,d2 = null,d3 = null,d4 = null,d5 = null;
        try {
            d1 = new SimpleDateFormat("dd/MM/yyyy").parse("03/12/2017");
            d2 = new SimpleDateFormat("dd/MM/yyyy").parse("04/12/2017");
            d3 = new SimpleDateFormat("dd/MM/yyyy").parse("04/12/2017");
            d4 = new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2017");
            d5 = new SimpleDateFormat("dd/MM/yyyy").parse("06/12/2017");
        } catch (ParseException e) {
            e.printStackTrace();
        }






         mChart = (LineChart) findViewById(R.id.chart);
/*
        List<Entry> valsComp1 = new ArrayList<Entry>();
        List<Entry> valsComp2 = new ArrayList<Entry>();
        Entry c1e1 = new Entry(0f, 100000f); // 0 == quarter 1
        valsComp1.add(c1e1);
        Entry c1e2 = new Entry(1f, 140000f); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        // and so on ...

        Entry c2e1 = new Entry(0f, 130000f); // 0 == quarter 1
        valsComp2.add(c2e1);
        Entry c2e2 = new Entry(1f, 115000f); // 1 == quarter 2 ...
        valsComp2.add(c2e2);

        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet setComp2 = new LineDataSet(valsComp2, "Company 2");
        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        LineData data = new LineData(dataSets);
        mLineChart.setData(data);
        mLineChart.invalidate();*/

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

        //setData(100, 30);
        setYogaData(yogaListData);
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
        xAxis.setGranularity(1f); // one hour
        //xAxis.setGranularity(24f); // one hour
        /*xAxis.setValueFormatter(new IAxisValueFormatter() {

            private SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm");

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });*/

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
        leftAxis.setAxisMaximum(yMax+5f);
        leftAxis.setYOffset(-9f);
        //leftAxis.setYOffset(-15f);
        leftAxis.setTextColor(Color.rgb(255, 192, 56));

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        mChart.animateXY(3000, 3000);
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

    private void setYogaData(List<Yoga>yogaList){
        ArrayList<Entry> values = new ArrayList<Entry>();


        // now in hours
        long now = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());
        float from = now;
        float x = from;
        // count = hours
        float to = now + yogaList.size();

        for(Yoga yoga:yogaList){
            float y = TimeUnit.MILLISECONDS.toMinutes(yoga.getDuration());
            if(y>yMax){
                yMax = y;
            }

            //x = yoga.getDate();
            x = TimeUnit.MILLISECONDS.toDays(yoga.getDate());
            //temporary hack as date x axis was 1 day behind
            // remove after finding the bug
            x++;
            Entry e = new Entry(x, y);
            values.add(e);
            // increment by 1 hour
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.line, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                List<ILineDataSet> sets = mChart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setDrawValues(!set.isDrawValuesEnabled());
                }

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if (mChart.getData() != null) {
                    mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
                    mChart.invalidate();
                }
                break;
            }
            case R.id.actionToggleFilled: {

                List<ILineDataSet> sets = mChart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    if (set.isDrawFilledEnabled())
                        set.setDrawFilled(false);
                    else
                        set.setDrawFilled(true);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleCircles: {
                List<ILineDataSet> sets = mChart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    if (set.isDrawCirclesEnabled())
                        set.setDrawCircles(false);
                    else
                        set.setDrawCircles(true);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleCubic: {
                List<ILineDataSet> sets = mChart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    if (set.getMode() == LineDataSet.Mode.CUBIC_BEZIER)
                        set.setMode(LineDataSet.Mode.LINEAR);
                    else
                        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleStepped: {
                List<ILineDataSet> sets = mChart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    if (set.getMode() == LineDataSet.Mode.STEPPED)
                        set.setMode(LineDataSet.Mode.LINEAR);
                    else
                        set.setMode(LineDataSet.Mode.STEPPED);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionTogglePinch: {
                if (mChart.isPinchZoomEnabled())
                    mChart.setPinchZoom(false);
                else
                    mChart.setPinchZoom(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
                mChart.notifyDataSetChanged();
                break;
            }
            case R.id.animateX: {
                mChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(3000);
                break;
            }
            case R.id.animateXY: {
                mChart.animateXY(3000, 3000);
                break;
            }

            case R.id.actionSave: {
               /* if (mChart.saveToPath("title" + System.currentTimeMillis(), "")) {
                    Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                            .show();*/

               displayToast("trying to save");

                // mChart.saveToGallery("title"+System.currentTimeMillis())
                break;
            }
        }
        // handle rest of the default item to the parent activity base class
        return super.onOptionsItemSelected(item);
    }


    private void displayToast(String msg){
        Toast.makeText(this, "hiii " +msg,Toast.LENGTH_LONG).show();
    }

}
