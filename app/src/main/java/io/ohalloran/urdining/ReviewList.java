package io.ohalloran.urdining;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import static io.ohalloran.urdining.ReviewActivity.DANFORTH_POS;

/**
 * Created by Ben on 1/29/2015.
 */
public class ReviewList extends ListFragment {
    private static final String WHICH_KEY = "hall";


    private View header;
    private int which;
    private Adapter listAdapter;

    public static final ReviewList newInstance(int which) {
        ReviewList rl = new ReviewList();
        Bundle bun = new Bundle();
        bun.putInt(WHICH_KEY, which);
        rl.setArguments(bun);
        return rl;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        which = getArguments().getInt(WHICH_KEY, -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View list_root = inflater.inflate(R.layout.fragment_review, null);
        header = new TextView(list_root.getContext());
        ((TextView) header).setText(which == DANFORTH_POS ? "Danforth" : "Douglass");
        return list_root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setAdapter(listAdapter = new Adapter());
    }

    private class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
