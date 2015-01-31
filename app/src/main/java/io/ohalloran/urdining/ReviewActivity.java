package io.ohalloran.urdining;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.List;

import io.ohalloran.urdining.data.DiningHall;


public class ReviewActivity extends ActionBarActivity {
    public static final String KEY = "clicked";

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        pager = (ViewPager) findViewById(R.id.list_pager);
        //set up the fragments & pager
        List<Fragment> frags = new ArrayList<>();
        for (DiningHall hall : DiningHall.values())
            frags.add(ReviewList.newInstance(hall));

        pager.setAdapter(new ReviewFragAdapter(getSupportFragmentManager(), frags));

        //load the right frag
        Bundle args = getIntent().getExtras();
        DiningHall hallEnum = DiningHall.getEnum(args.getString(KEY));
        showFrag(hallEnum.getIndex());
    }

    private void showFrag(int frag) {
        pager.setCurrentItem(frag);
    }

}
