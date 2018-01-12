package com.brian.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.brian.shop.databases.ShopDbHelper;

import java.util.HashMap;
import java.util.List;

/**
 * Created by omachi on 1/31/16.
 */
public class BidFragment extends Fragment {
    public static BidFragment newInstance() {
        BidFragment fragment = new BidFragment();
        return fragment;
    }

    public BidFragment() {
        // Required empty public constructor
    }
    Context context;
    ShopDbHelper dbHelper;
    ImageView image;
    View view;
    ListView listView;
    private OnItemSelectedListener onItemSelectedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_bid, container, false);
        image = (ImageView) view.findViewById(R.id.icon12);
//        pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);
//        pbLoading.setVisibility(View.VISIBLE);
        context = getActivity().getApplicationContext();
        final ListView listview = (ListView) view.findViewById(R.id.LIST_VIEW_BIDS);
        listview.setVisibility(View.VISIBLE);
        dbHelper = new ShopDbHelper(context);
        final StableArrayAdapter adapter2 = new StableArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, dbHelper.getAllBids());
        listview.setAdapter(adapter2);
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view,
//                                    int position, long id) {
//                final BidInfo item = (BidInfo) parent.getItemAtPosition(position);
//                onItemSelectedListener.itemSelected(item);
//            }
//
//        });
        return view;
    }
    public interface OnItemSelectedListener{
        void itemSelected(BidInfo itemInfo);
    }
    private class StableArrayAdapter extends ArrayAdapter<BidInfo> {

        private HashMap<BidInfo, Integer> mIdMap = new HashMap<BidInfo, Integer>();
        private final Context context;

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<BidInfo> objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            BidInfo item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.bids_row_layout, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.label12);
            TextView textView2 = (TextView) rowView.findViewById(R.id.txt_bid_amount);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon12);
            final BidInfo u = getItem(position);
            textView.setText(String.valueOf(u.getDetails()));
            textView2.setText("Amount bidded: " + u.getAmount());
            // change the icon for Windows and iPhone
            imageView.setImageResource(R.drawable.phone2);


            //If the user has set a profile pic set it


            return rowView;
        }

    }

}
