package io.ohalloran.urdining.data;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.ohalloran.urdining.R;

/**
 * Created by Ben on 1/31/2015.
 */
public class DataUtils {
    private static final String TAG = "Data Utils";

    private static List<Review> danforthRecent = new ArrayList<>();
    private static List<Review> danforthPopular = new ArrayList<>();
    private static List<Review> douglassRecent = new ArrayList<>();
    private static List<Review> douglassPopular = new ArrayList<>();

    private static List<BaseAdapter> dataChangeListener = new ArrayList<>();

    public static void initialize(Context c) {
        Parse.initialize(c, c.getString(R.string.app_id), c.getString(R.string.client_key));
        refreshReviews();
    }

    public static void addBaseAdapter(BaseAdapter baseAdapter) {
        dataChangeListener.add(baseAdapter);
    }

    private static void refreshScores() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Scores");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                //scores = scoreList;
            }
        });
    }

    public static void refreshReviews() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> reviewList, ParseException e) {
                if (reviewList.isEmpty()) {
                    return;
                }
                List<Review> danforth = new ArrayList<>();
                List<Review> douglass = new ArrayList<>();
                for (ParseObject parseRev : reviewList) {
                    DiningHall hall = DiningHall.getEnum(parseRev.getString("diningHall"));
                    Review rev = createReview(parseRev, hall);
                    if (DiningHall.DANFORTH == hall) {
                        danforth.add(rev);
                    } else {
                        douglass.add(rev);
                    }
                }
                List<Review> danforthPop = new ArrayList<>(danforth.size());
                danforthPop.addAll(danforth);

                List<Review> douglassPop = new ArrayList<>(douglass.size());
                douglassPop.addAll(douglass);

                Comparator<Review> reviewComparator = new Comparator<Review>() {
                    @Override
                    public int compare(Review lhs, Review rhs) {
                        return rhs.getVotes() - lhs.getVotes();
                    }
                };
                Collections.sort(danforthPop, reviewComparator);
                Collections.sort(douglassPop, reviewComparator);

                danforthRecent = danforth;
                danforthPopular = danforthPop;
                douglassRecent = douglass;
                douglassPopular = douglassPop;
            }
        });

        for (BaseAdapter baseAdapter : dataChangeListener) {
            baseAdapter.notifyDataSetChanged();
        }
    }

    private static Review createReview(ParseObject parseRev, DiningHall hall) {
        String textReview = parseRev.getString("review");
        float startsReview = parseRev.getLong("rating");
        int votes = parseRev.getInt("score");
        int userId = parseRev.getInt("userId");
        String objectId = parseRev.getObjectId();
        return new Review(textReview, startsReview, votes, hall, userId, objectId);
    }

    //Get the reviews for <hall> in most recent order
    public static List<Review> getReviews(DiningHall hall) {
        if (hall == DiningHall.DANFORTH) {
            return danforthRecent;
        } else {
            return douglassRecent;
        }
    }

    //Return reviews highest score first
    public static List<Review> getReviewsHot(DiningHall hall) {
        if (hall == DiningHall.DANFORTH) {
            return danforthPopular;
        } else {
            return douglassPopular;
        }
    }

    //Submits a review to the cloud
    public static void sendReview(Review review) {
        String textReview = review.getTextReview();
        float startsReview = review.getStartsReview();
        int votes = review.getVotes();
        DiningHall hall = review.getHall();
        int userId = review.getUserId();

        ParseObject newReview = new ParseObject("Reviews");
        newReview.put("review", textReview);
        newReview.put("rating", startsReview);
        newReview.put("score", votes);
        newReview.put("diningHall", hall.titleCase());
        newReview.put("userId", userId);
        newReview.saveInBackground();

        //Add user to score table if not already in it
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Scores");
        query.whereEqualTo("userId", userId);
        try {
            List<ParseObject> scoreList = query.find();
            if (scoreList.isEmpty()) {
                ParseObject newEntry = new ParseObject("Scores");
                newEntry.put("userId", userId);
                newEntry.put("score", 0);
                newEntry.saveInBackground();
            }
        } catch (ParseException e) {
            Log.e(TAG, "Error submitting review", e);
        }
    }

    public static void upVote(Review review) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
        query.getInBackground(review.getObjectId(), new GetCallback<ParseObject>() {
            public void done(ParseObject parseRev, ParseException e) {
                if (e == null) {
                    int newScore = parseRev.getInt("score") + 1;
                    parseRev.put("score", newScore);
                    parseRev.saveInBackground();
                }
            }
        });

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Scores");
        query2.whereEqualTo("userId", review.getUserId());
        query2.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject parseRev, ParseException e) {
                if (e == null) {
                    int newScore = parseRev.getInt("score") + 1;
                    parseRev.put("score", newScore);
                    parseRev.saveInBackground();
                }
            }
        });
    }

    public static void downVote(Review review) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
        query.getInBackground(review.getObjectId(), new GetCallback<ParseObject>() {
            public void done(ParseObject parseRev, ParseException e) {
                if (e == null) {
                    int newScore = parseRev.getInt("score") - 1;
                    parseRev.put("score", newScore);
                    parseRev.saveInBackground();
                }
            }
        });

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Scores");
        query2.whereEqualTo("userId", review.getUserId());
        query2.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject parseRev, ParseException e) {
                if (e == null) {
                    int newScore = parseRev.getInt("score") - 1;
                    parseRev.put("score", newScore);
                    parseRev.saveInBackground();
                }
            }
        });
    }
}
