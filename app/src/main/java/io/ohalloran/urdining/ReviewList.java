package io.ohalloran.urdining;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import io.ohalloran.urdining.data.DataUtils;
import io.ohalloran.urdining.data.DiningHall;
import io.ohalloran.urdining.data.Review;

/**
 * Created by Ben on 1/29/2015.
 */
public class ReviewList extends ListFragment {
    private static final String WHICH_KEY = "hall";


    private View header;
    private DiningHall which;
    private Adapter listAdapter;

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
        header = new TextView(list_root.getContext());
        if (which != null)
            ((TextView) header).setText(which.titleCase());
        return list_root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setAdapter(listAdapter = new Adapter());
    }

    private class Adapter extends BaseAdapter {

        List<Review> reviews = DataUtils.getReviews(which);
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        @Override
        public int getCount() {
            return reviews.size();
        }

        @Override
        public Object getItem(int position) {
            return reviews.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(convertView == null){
                v = inflater.inflate(R.layout.review_layout, parent, false);
            }

            return v;
        }
    }
}
