package com.hobby.jayant.activitytracker.adapters;

/**
 * Created by I329687 on 12/16/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.hobby.jayant.activitytracker.activities.MainActivity;
import com.hobby.jayant.activitytracker.fragments.PlaceholderFragment;
import com.hobby.jayant.activitytracker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "SectionsPagerAdapter";
    private FragmentManager mFragmentManager;
    private ArrayList<Utils.DummyItem> mDummyItems;
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        List<Fragment> fragmentsList = mFragmentManager.getFragments();
        if (fragmentsList != null && position <= (fragmentsList.size() - 1) && mDummyItems !=null) {
            PlaceholderFragment placeholderFragment1 = (PlaceholderFragment) fragmentsList.get(position);
            PlaceholderFragment placeholderFragment = (PlaceholderFragment) fragmentsList.get(position);

            // myDummyItems is null if it goes out of view
            Utils.DummyItem dummyItem = mDummyItems.get(position);
            //If the current data of the fragment changed, set the new data
            if (!dummyItem.equals(placeholderFragment.getDummyItem())) {
                placeholderFragment.setDummyItem(dummyItem);
                Log.i(TAG, "********instantiateItem position:" + position + " FragmentDataChanged");
            }
        } else {
            //No fragment instance available for this index, create a new fragment by calling getItem() and show the data.
            Log.i(TAG, "********instantiateItem position:" + position + " NewFragmentCreated");
        }
        return super.instantiateItem(container, position);
    }
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //Create a new instance of the fragment and return it.
        //PlaceholderFragment sampleFragment = (PlaceholderFragment)PlaceholderFragment.newInstance(position + 1);
        PlaceholderFragment sampleFragment = (PlaceholderFragment)PlaceholderFragment.newInstance();
        if(mDummyItems!=null){
            Utils.DummyItem dummyItem = mDummyItems.get(position);
            sampleFragment.setDummyItem(dummyItem);
        }
        return sampleFragment;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return MainActivity.PAGETYPE.getPageTitle(position);
    }

    public void  setDummyItems(ArrayList<Utils.DummyItem> dummyItems){
        mDummyItems = dummyItems;
    }
}