package io.ohalloran.urdining.data;

/**
 * Created by Ben on 1/31/2015.
 */
public class Review implements Comparable<Review> {
    private String textReview;
    private float startsReview;
    private int votes;
    private int userId; //TODO may not need
    private DiningHall hall;

    public Review(String textReview, float startsReview, int votes, DiningHall hall, int userId) {
        this.textReview = textReview;
        this.startsReview = startsReview;
        this.votes = votes;
        this.hall = hall;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public float getStartsReview() {
        return startsReview;
    }

    public void setStartsReview(float startsReview) {
        this.startsReview = startsReview;
    }

    public String getTextReview() {
        return textReview;
    }

    public void setTextReview(String textReview) {
        this.textReview = textReview;
    }

    public DiningHall getHall() {
        return hall;
    }

    public void setHall(DiningHall hall) {
        this.hall = hall;
    }

    private Float score() {
        return startsReview;
    }

    @Override
    public int compareTo(Review another) {
        return score().compareTo(another.score());
    }
}
