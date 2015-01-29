package io.ohalloran.urdining;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;


public class HallReviews extends ActionBarActivity {

    public static final String KEY_HALL = "dinning hall";
    public static final String DOUGLASS = "Douglass";
    public static final String DFO = "Danforth";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(KEY_HALL)) {
            //do stuff!
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reviews, menu);
        return false; //TODO menu?
    }


}
