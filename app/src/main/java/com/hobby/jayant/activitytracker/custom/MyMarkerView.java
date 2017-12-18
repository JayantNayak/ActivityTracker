
package com.hobby.jayant.activitytracker.custom;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.hobby.jayant.activitytracker.R;


/**
 * Custom implementation of the MarkerView.
 * 
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        String markerText = "";
        CustomEntry customEntry = (CustomEntry)e;
        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;


            markerText = "" + Utils.formatNumber(ce.getHigh(), 0, true);
        } else {
            markerText = "" + Utils.formatNumber(e.getY(), 0, true);
        }
        int rating  = customEntry.getRating();

        markerText= customEntry.toString();
        tvContent.setText(markerText);
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
