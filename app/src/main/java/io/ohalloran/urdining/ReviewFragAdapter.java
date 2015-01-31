/****************************
 * deStress
 * Updated 4/28/14 by Ben O'Halloran
 ***************************/
package io.ohalloran.urdining;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import io.ohalloran.urdining.data.DiningHall;

/**
 * Standard fragment adapter for the main page
 */
public class ReviewFragAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;

    public ReviewFragAdapter(FragmentManager fm, List<Fragment> frags) {
        super(fm);
        list = frags;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return DiningHall.values()[position].titleCase();
    }
}