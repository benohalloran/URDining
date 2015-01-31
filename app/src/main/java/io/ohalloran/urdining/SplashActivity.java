package io.ohalloran.urdining;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import io.ohalloran.urdining.data.DiningHall;


public class SplashActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
