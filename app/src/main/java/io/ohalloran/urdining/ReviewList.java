package io.ohalloran.urdining;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import io.ohalloran.urdining.data.DataUtils;
import io.ohalloran.urdining.data.DiningHall;
import io.ohalloran.urdining.data.Review;

/**
 * Created by Ben on 1/29/2015.
 */
public class ReviewList extends ListFragment implements View.OnClickListener {
    private static final String WHICH_KEY = "hall";


    private DiningHall which;
    private Adapter listAdapter;
    private View footer; //hot/cold view

    public static ReviewList newInstance(DiningHall which) {
        ReviewList rl = new ReviewList();
        Bundle bun = new Bundle();
        bun.putString(WHICH_KEY, which.toString());
        rl.setArguments(bun);
        return rl;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        which = DiningHall.getEnum(getArguments().getString(WHICH_KEY));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View list_root = inflater.inflate(R.layout.fragment_review, null);
        footer = inflater.inflate(R.layout.recent_pop, null, false);
        footer.findViewById(R.id.footer_recent).setOnClickListener(this);
        footer.findViewById(R.id.footer_pop).setOnClickListener(this);
        ListView lv = (ListView) list_root.findViewById(android.R.id.list);
        lv.addFooterView(footer);
        return list_root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setAdapter(listAdapter = new Adapter());
        DataUtils.addBaseAdapter(listAdapter);
    }

    @Override
    public void onClick(View view) {
        Log.d("Review List", view.toString());
    }

    private class Adapter extends BaseAdapter {
        final LayoutInflater inflater = LayoutInflater.from(getActivity());

        List<Review> reviews = DataUtils.getReviews(which);

        @Override
        public int getCount() {
            return reviews.size();
        }

        @Override
        public Review getItem(int position) {
            return reviews.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View root = convertView;
            if (convertView == null) {
                root = inflater.inflate(R.layout.review_layout, parent, false);
            }
            TextView textReview = (TextView) root.findViewById(R.id.text_review);
            RatingBar ratingBar = (RatingBar) root.findViewById(R.id.rating_display);
            TextView scoreDisplay = (TextView) root.findViewById(R.id.vote_display);

            Review data = getItem(position);

            textReview.setText(data.getTextReview());
            ratingBar.setRating(data.getStartsReview());
            scoreDisplay.setText(data.getVotes() + "");
            return root;
        }

        @Override
        public void notifyDataSetChanged() {
            reviews = DataUtils.getReviews(which);
            super.notifyDataSetChanged();
        }
    }
}
