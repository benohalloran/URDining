package io.ohalloran.urdining.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ben on 1/31/2015.
 */
public class DataUtils {

    //Get the reviews for <hall> in most recent orders
    public static List<Review> getReviews(DiningHall hall) {
        List<Review> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Review r = new Review(i + "", i, i, hall, i);
            list.add(r);
        }
        return list;
    }

    //return reviews highest score first
    public static List<Review> getReviewsHot(DiningHall hall) {
        List<Review> list = getReviews(hall);
        Collections.sort(list);
        return list;
    }

    //submit a review to the cloud
    public static void sendReview(Review review) {

    }
}
