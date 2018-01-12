package com.brian.shop;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.brian.shop.databases.ShopDbHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by omachi on 1/31/16.
 */
public class Won_BidsFragment extends Fragment {
    public static Won_BidsFragment newInstance() {
        Won_BidsFragment fragment = new Won_BidsFragment();
        return fragment;
    }

    public Won_BidsFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_won_bid, container, false);
        image = (ImageView) view.findViewById(R.id.icon1);
        context = getActivity().getApplicationContext();
        final ListView listview = (ListView) view.findViewById(R.id.LIST_VIEW_WON);
        listview.setVisibility(View.VISIBLE);
        dbHelper = new ShopDbHelper(context);
        List<BidInfo> bids = dbHelper.getAllBids();
        ArrayList<WinInfo> wins= new ArrayList<>();
        Iterator<BidInfo> secondIt = dbHelper.getAllBids().iterator();
        Iterator<ItemInfo> firstIt = dbHelper.getAllItems().iterator();
        while (firstIt.hasNext()) {
            String str1 = (String) String.valueOf(firstIt.next().getId());
            while (secondIt.hasNext()){
                String str2 = (String) secondIt.next().getItemId();
                for (BidInfo bu:bids
                        ) {

                    WinInfo wonInfo = new WinInfo();
                    wonInfo.setDetails(bu.getDetails());
                    wonInfo.setProfilePic(bu.getProfilePic());
                    wonInfo.setId(bu.getId());
                    wonInfo.setAmount(bu.getAmount());

                if (str2.equalsIgnoreCase(str1)){
                    ArrayList amounts = new ArrayList();
                    amounts.add(Integer.parseInt(bu.getAmount()));
                    Object obj = Collections.max(amounts);
                    if(Integer.valueOf(wonInfo.getAmount())== obj){
                        wins.add(wonInfo);
                    }
                  }
                }
            }
        }
        final StableArrayAdapter adapter2 = new StableArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, wins);
        listview.setAdapter(adapter2);

        return view;
    }
    public interface OnItemSelectedListener{
        void itemSelected(WinInfo itemInfo);
    }
    private class StableArrayAdapter extends ArrayAdapter<WinInfo> {

        private HashMap<WinInfo, Integer> mIdMap = new HashMap<WinInfo, Integer>();
        private final Context context;

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<WinInfo> objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            WinInfo item = getItem(position);
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
            View rowView = inflater.inflate(R.layout.won_row_layout, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.label1);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon1);
            final WinInfo u = getItem(position);
            textView.setText(String.valueOf(u.getDetails()));

            // change the icon for Windows and iPhone
            imageView.setImageResource(R.drawable.phone2);


            //If the user has set a profile pic set it


            return rowView;
        }

    }
}
