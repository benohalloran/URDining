package io.ohalloran.urdining;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RatingBar;

import java.util.List;

import io.ohalloran.urdining.data.DataUtils;
import io.ohalloran.urdining.data.DiningHall;
import io.ohalloran.urdining.data.Review;


public class SplashActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        DataUtils.initialize(getApplicationContext(), new DataUtils.OnRefreshCallback() {
            @Override
            public void onRefreshComplete() {
                float danforthAvg = getAvgRating(DiningHall.DANFORTH);
                float douglassAvg = getAvgRating(DiningHall.DOUGLASS);
                RatingBar dfoRating = (RatingBar) findViewById(R.id.dfo_ratting);
                dfoRating.setRating(danforthAvg);
                RatingBar douglassRating = (RatingBar) findViewById(R.id.douglass_rating);
                douglassRating.setRating(douglassAvg);
            }
        });
    }

    private float getAvgRating(DiningHall hall) {
        List<Review> reviews = DataUtils.getReviews(hall);
        float sum = 0;
        for (Review rev : reviews) {
            sum += rev.getStartsReview();
        }
        return sum / (float) reviews.size();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.dfo_button || id == R.id.douglass_button) {
            DiningHall val = id == R.id.dfo_button ? DiningHall.DANFORTH : DiningHall.DOUGLASS;
            Intent intent = new Intent(this, ReviewActivity.class);
            intent.putExtra(ReviewActivity.KEY, val.toString());
            startActivity(intent);
        }
    }
}
