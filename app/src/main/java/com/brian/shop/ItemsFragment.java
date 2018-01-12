package com.brian.shop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.brian.shop.databases.ShopDbContract;
import com.brian.shop.databases.ShopDbHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by omachi on 1/31/16.
 */
public class ItemsFragment extends Fragment {

    public static ItemsFragment newInstance() {
        ItemsFragment fragment = new ItemsFragment();
        return fragment;
    }

    public ItemsFragment() {
        // Required empty public constructor
    }
    public final static String KEY_EXTRA_CONTACT_ID = "KEY_EXTRA_ITEM_ID";
//    private ProgressBar pbLoading;
    Context context;
    ShopDbHelper dbHelper;
    ImageView image;
    TextView details;
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
        final View view = inflater.inflate(R.layout.fragment_items, container, false);
        image = (ImageView) view.findViewById(R.id.icon);
        details = (TextView) view.findViewById(R.id.label);

//        pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);
//        pbLoading.setVisibility(View.VISIBLE);
        context = getActivity().getApplicationContext();
        final ListView listview = (ListView) view.findViewById(R.id.LIST_VIEW_ITEMS);
        listview.setVisibility(View.VISIBLE);
        dbHelper = new ShopDbHelper(context);
        final StableArrayAdapter adapter2 = new StableArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, dbHelper.getAllItems());
        listview.setAdapter(adapter2);
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view,
//                                    int position, long id) {
//                final ItemInfo item = (ItemInfo) parent.getItemAtPosition(position);
//                onItemSelectedListener.itemSelected(item);
//            }
//
//        });
return view;
    }
    public interface OnItemSelectedListener{
        void itemSelected(ItemInfo itemInfo);
    }
    private class StableArrayAdapter extends ArrayAdapter<ItemInfo> {

        private HashMap<ItemInfo, Integer> mIdMap = new HashMap<ItemInfo, Integer>();
        private final Context context;

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<ItemInfo> objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            ItemInfo item = getItem(position);
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
            View rowView = inflater.inflate(R.layout.items_row_layout, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.label);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            final ItemInfo u = getItem(position);
            Button btnbid = (Button) rowView.findViewById(R.id.btn_bidder);
            textView.setText(u.getDetails());
            // change the icon for Windows and iPhone
            imageView.setImageResource(R.drawable.phone2);
            //If the user has set a profile pic set it

           btnbid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bid(u);
                }
            });

            return rowView;
        }

    }
    private void bid(ItemInfo itemInfo){
        Intent intent = new Intent(getActivity(), BidActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bundle", itemInfo);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}



