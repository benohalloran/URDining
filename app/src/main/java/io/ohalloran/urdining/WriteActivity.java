package io.ohalloran.urdining;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import io.ohalloran.urdining.data.DataUtils;
import io.ohalloran.urdining.data.DiningHall;
import io.ohalloran.urdining.data.Review;


public class WriteActivity extends ActionBarActivity {

    private EditText textReviewEditText;
    private RadioGroup hallRadio;
    private RatingBar starRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        textReviewEditText = (EditText)findViewById(R.id.text_review);
        hallRadio = (RadioGroup)findViewById(R.id.hall);
        starRatingBar = (RatingBar)findViewById(R.id.rating);
        textReviewEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    submit();
                    return true;
                }
                return false;
            }
        });

    }



    private void submit() {
        String textReview = textReviewEditText.getText().toString();
        float startsReview = starRatingBar.getRating();
        int votes = 0;
        DiningHall hall = hallRadio.getCheckedRadioButtonId() == R.id.Douglass ? DiningHall.DOUGLASS : DiningHall.DANFORTH;
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        int userId = androidId.hashCode();
        Review review = new Review(textReview, startsReview, votes, hall, userId);
        DataUtils.sendReview(review);
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_write, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.send_review) {
            submit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
