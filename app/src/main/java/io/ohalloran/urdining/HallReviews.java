package io.ohalloran.urdining;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.Menu;


public class HallReviews extends ExpandableListActivity {

    public static final String KEY_HALL = "dinning hall";
    public static final String DOUGLASS = "Douglass";
    public static final String DFO = "Danforth";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reviews, menu);
        return false; //TODO menu?
    }


}
