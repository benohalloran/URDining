package io.ohalloran.urdining;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.ohalloran.urdining.data.DiningHall;
import io.ohalloran.urdining.data.ReviewFragAdapter;

import static io.ohalloran.urdining.ReviewList.Mode;

public class ReviewActivity extends Activity implements View.OnClickListener {
    public static final String KEY = "clicked";

    private ViewPager pager;

    ReviewFragAdapter fragAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        pager = (ViewPager) findViewById(R.id.list_pager);

        findViewById(R.id.footer_pop).setOnClickListener(this);
        findViewById(R.id.footer_recent).setOnClickListener(this);

        List<ReviewList> frags = new ArrayList<>();
        if (savedInstanceState == null) {
            //set up the fragments & pager
            for (DiningHall hall : DiningHall.values())
                frags.add(ReviewList.newInstance(hall));
        } else {
            //reload saved states
            for (DiningHall hall : DiningHall.values()) {
                Fragment f = getFragmentManager().getFragment(savedInstanceState,
                        hall.toString());
                frags.add((ReviewList) f);
            }
        }
        pager.setAdapter(fragAdapter = new ReviewFragAdapter(getFragmentManager(), frags));

        //load the right frag
        Bundle args = getIntent().getExtras();
        DiningHall hallEnum = DiningHall.getEnum(args.getString(KEY));
        showFrag(hallEnum.getIndex());
    }

    @Override
    public void onClick(View view) {
        ReviewList frag = fragAdapter.getItem(pager.getCurrentItem());
        switch (view.getId()) {
            case R.id.footer_pop:
                frag.updateMode(Mode.POPULAR);
                break;
            case R.id.footer_recent:
                frag.updateMode(Mode.RECENT);
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        for (ReviewList f : fragAdapter) {
            getFragmentManager().putFragment(outState, f.which().toString(), f);
        }
    }

    private void showFrag(int frag) {
        pager.setCurrentItem(frag);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.review_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.write_review:
                Intent intent = new Intent(this, WriteActivity.class);
                intent.putExtra(WriteActivity.FRAG_ID, pager.getCurrentItem());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
