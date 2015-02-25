package io.ohalloran.urdining;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import io.ohalloran.urdining.data.DataUtils;
import io.ohalloran.urdining.data.DiningHall;
import io.ohalloran.urdining.data.Review;

/**
 * Created by Ben on 1/29/2015.
 */
public class ReviewList extends ListFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String WHICH_KEY = "hall";


    private DiningHall which;
    private Adapter listAdapter;
    private SwipeRefreshLayout refreshLayout;

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
        View root = inflater.inflate(R.layout.fragment_review, null);

        refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(WHICH_KEY, which.toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setAdapter(listAdapter = new Adapter());
        DataUtils.addBaseAdapter(listAdapter);
        if (which == null && savedInstanceState != null) {
            which = DiningHall.valueOf(savedInstanceState.getString(WHICH_KEY));
        }
    }


    public DiningHall which() {
        return which;
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        DataUtils.refreshReviews(new DataUtils.OnRefreshCallback() {
            @Override
            public void onRefreshComplete() {
                listAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    public void updateMode(Mode mode) {
        listAdapter.updateMode(mode);
    }

    public static enum Mode {
        RECENT, POPULAR
    }

    private class Adapter extends BaseAdapter {
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View.OnTouchListener trueListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        };


        Mode mode = Mode.RECENT;
        List<Review> reviews = DataUtils.getReviews(which);


        public void updateMode(Mode m) {
            if (mode == m)
                return;
            mode = m;
            fetchReviews();
            notifyDataSetChanged();
        }

        void fetchReviews() {
            switch (mode) {
                case RECENT:
                    reviews = DataUtils.getReviews(which);
                    break;
                case POPULAR:
                    reviews = DataUtils.getReviewsHot(which);
                    break;
            }
        }

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View root = convertView;
            if (convertView == null) {
                root = inflater.inflate(R.layout.review_layout, parent, false);
            }
            TextView textReview = (TextView) root.findViewById(R.id.text_review);
            RatingBar ratingBar = (RatingBar) root.findViewById(R.id.rating_display);
            TextView scoreDisplay = (TextView) root.findViewById(R.id.vote_display);

            final Review data = getItem(position);

            textReview.setText(data.getTextReview());
            ratingBar.setRating(data.getStartsReview());
            scoreDisplay.setText(data.getVotes() + "");

            ratingBar.setOnTouchListener(trueListener);

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.vote_up:
                            DataUtils.upVote(data);
                            break;
                        case R.id.vote_down:
                            DataUtils.downVote(data);
                    }
                }
            };

            //up-down votes
            root.findViewById(R.id.vote_up).setOnClickListener(listener);
            root.findViewById(R.id.vote_down).setOnClickListener(listener);
            return root;
        }

        @Override
        public void notifyDataSetChanged() {
            fetchReviews();
            super.notifyDataSetChanged();
        }

    }
}
