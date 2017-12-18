package com.hobby.jayant.activitytracker.fragments;

/**
 * Created by I329687 on 12/16/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hobby.jayant.activitytracker.R;
import com.hobby.jayant.activitytracker.utils.DownloadImageTask;
import com.hobby.jayant.activitytracker.utils.Utils;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    Utils.DummyItem dummyItem;
    private String TAG = "PlaceholderFragment";

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public static PlaceholderFragment newInstance() {
        PlaceholderFragment fragment = new PlaceholderFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //String MY_URL_STRING = "https://www.biggerbolderbaking.com/wp-content/uploads/2016/05/BBB124-Top-5-Homemade-Ice-Cream-Flavors-Thumbnail-FINAL-2-1024x576.jpg";
        String MY_URL_STRING = "https://gdurl.com/0YBQ";
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
       // TextView textView = (TextView) rootView.findViewById(R.id.section_label);

        if(dummyItem!=null ){
            MY_URL_STRING = dummyItem.getImageUrl();
        }
        new DownloadImageTask((ImageView) rootView.findViewById(R.id.imageView))
                .execute(MY_URL_STRING);
        return rootView;
    }

    public Utils.DummyItem getDummyItem() {
        return dummyItem;
    }

    public void setDummyItem(Utils.DummyItem dummyItem) {
        this.dummyItem = dummyItem;
        //Log.i(TAG,"setDummyItem:Title:"+dummyItem.getImageTitle());
    }
}