package io.ohalloran.urdining.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.content.Context;
import com.parse.*;
import io.ohalloran.urdining.R;

/**
 * Created by Ben on 1/31/2015.
 */
public class DataUtils {

    private static List<ParseObject> reviews;
    private static List<ParseObject> scores;

    public static void initialize(Context c) {
        Parse.initialize(c, c.getString(R.string.app_id), c.getString(R.string.client_key));
    }

    private static void refreshScores() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Scores");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                scores = scoreList;
            }
        });
    }

    private static void refreshReviews(String order) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
        query.orderByDescending(order);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> reviewList, ParseException e) {
                reviews = reviewList;
            }
        });
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
        refreshReviews("createdAt");
        List<Review> reviewList = new ArrayList<Review>();
        for (ParseObject parseRev : reviews) {
            reviewList.add(createReview(parseRev, hall));
        }
        return reviewList;
    }

    //Return reviews highest score first
    public static List<Review> getReviewsHot(DiningHall hall) {
        refreshReviews("score");
        List<Review> reviewList = new ArrayList<Review>();
        for (ParseObject parseRev : reviews) {
            reviewList.add(createReview(parseRev, hall));
        }
        return reviewList;
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
            List<ParseObject> scoreList= query.find();
            if (scoreList.isEmpty()) {
                ParseObject newEntry = new ParseObject("Scores");
                newEntry.put("userId", userId);
                newEntry.put("score", 0);
                newEntry.saveInBackground();
            }
        } catch (ParseException e) {}
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

    public static List<ParseObject> getScores() {
        return scores;
    }
}
