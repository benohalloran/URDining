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

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        pager = (ViewPager) findViewById(R.id.list_pager);
        List<Fragment> frags = new ArrayList<>();
        frags.add(ReviewList.newInstance(DANFORTH_POS));
        frags.add(ReviewList.newInstance(DOUGLAS_POS));
        pager.setAdapter(new ReviewFragAdapter(getSupportFragmentManager(), frags));
        //load the right frag
        {
            Bundle args = getIntent().getExtras();
            showFrag(args.getInt(KEY, 0));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_review, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFrag(int frag) {
        pager.setCurrentItem(frag);
    }

}
