package io.ohalloran.urdining;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class ReviewActivity extends ActionBarActivity {
    public static final String KEY = "clicked";
    public static final int DANFORTH_POS = 0;
    public static final int DOUGLAS_POS = 1;

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        pager = (ViewPager) findViewById(R.id.list_pager);
        //set up the fragments & pager
        List<Fragment> frags = new ArrayList<>();
        frags.add(ReviewList.newInstance(DANFORTH_POS));
        frags.add(ReviewList.newInstance(DOUGLAS_POS));
        pager.setAdapter(new ReviewFragAdapter(getSupportFragmentManager(), frags));

        //load the right frag
        Bundle args = getIntent().getExtras();
        showFrag(args.getInt(KEY, 0));
    }

    private void showFrag(int frag) {
        pager.setCurrentItem(frag);
    }

}
