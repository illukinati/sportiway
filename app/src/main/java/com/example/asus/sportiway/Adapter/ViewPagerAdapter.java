package com.example.asus.sportiway.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.asus.sportiway.Fragment.EventFragment;
import com.example.asus.sportiway.Fragment.MapFragment;
import com.example.asus.sportiway.Fragment.ProfileFragment;

/**
 * Created by Asus on 05/09/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public ViewPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                MapFragment tab1 = new MapFragment();
                return tab1;
            case 1:
                EventFragment tab2 = new EventFragment();
                return tab2;
            case 2:
                ProfileFragment tab3 = new ProfileFragment();
                return tab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
