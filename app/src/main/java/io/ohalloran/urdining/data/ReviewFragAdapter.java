/****************************
 * deStress
 * Updated 4/28/14 by Ben O'Halloran
 ***************************/
package io.ohalloran.urdining.data;

import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.Iterator;
import java.util.List;

import io.ohalloran.urdining.ReviewList;

/**
 * Standard fragment adapter for the main page
 */
public class ReviewFragAdapter extends FragmentPagerAdapter implements Iterable<ReviewList> {
    private List<ReviewList> list;

    public ReviewFragAdapter(FragmentManager fm, List<ReviewList> frags) {
        super(fm);
        list = frags;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ReviewList getItem(int i) {
        return list.get(i);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return DiningHall.values()[position].titleCase();
    }

    @Override
    public Iterator<ReviewList> iterator() {
        return list.iterator();
    }
}