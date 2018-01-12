package com.brian.shop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brian.shop.databases.ShopDbHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by omachi on 1/31/16.
 */
public class BidActivity extends ActionBarActivity {

    ImageView image;
    Button manual_bid, auto_bid;
    TextView description, lowest_price;
    EditText amount;
    ItemInfo cust;
    Date convertedDate;
    String bid_amount;
    Context c;
    static final long ONE_MINUTE_IN_MILLIS=60000;

    ShopDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid);

        Intent intent = getIntent();
        cust = (ItemInfo) intent.getSerializableExtra("bundle");

        c = this;

        image = (ImageView) findViewById(R.id.imageView);
        manual_bid = (Button) findViewById(R.id.btn_man);
        lowest_price = (TextView) findViewById(R.id.txt_amount);
        auto_bid = (Button) findViewById(R.id.btn_auto);
        amount = (EditText) findViewById(R.id.edt_bid_amount);
        description = (TextView) findViewById(R.id.text_desc);

        image.setImageResource(R.drawable.phone2);
        description.setText(String.valueOf(cust.getDetails()));
        lowest_price.setText("Lowest bidding price: " + cust.getAmount());

//        bid_amount = amount.getText().toString();

        dbHelper = new ShopDbHelper(this);

        String dateInString = cust.getPostingTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateInString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final Date t= addHoursToDate(Integer.parseInt(cust.getPeriod()),convertedDate);


        manual_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bid_amount = amount.getText().toString();
                if (Integer.parseInt(cust.getAmount()) < Integer.parseInt(bid_amount) ) {
                    if (convertedDate.before(t)||convertedDate.equals(t)){
                        if(dbHelper.insertBids(bid_amount, cust.getProfilePic(), String.valueOf(cust.getId()),cust.getDetails(),null, null )) {
                            dbHelper.updateItem(Integer.parseInt(String.valueOf(cust.getId())), cust.getDetails(),cust.getProfilePic() ,String.valueOf(bid_amount), cust.getPeriod() );
                            Toast.makeText(getApplicationContext(), "Bid completed", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Could not complete bid", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Bidding has expired for this item", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Enter an amount higher than: "+cust.getAmount(), Toast.LENGTH_SHORT).show();
                }

            }

        });


        auto_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bid_amount = amount.getText().toString();
                if (Integer.parseInt(cust.getAmount()) < Integer.parseInt(bid_amount)) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(BidActivity.this);
                    LinearLayout layout = new LinearLayout(c);
                    layout.setOrientation(LinearLayout.VERTICAL);

                    final EditText increment = new EditText(c);
                    increment.setHint("Increment value");
                    layout.addView(increment);

                    final EditText max = new EditText(c);
                    max.setHint("Maximamum value");
                    layout.addView(max);

                    alert.setView(layout);

                    alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //What ever you want to do with the value
                            final String incre = increment.getText().toString();
                            final String maxi = max.getText().toString();
                            int amount = Integer.parseInt(cust.getAmount());
                            for (int i = Integer.parseInt(incre); i < Integer.parseInt(maxi) ; i++) {
                                amount = amount + i;
                                if (amount < Integer.parseInt(maxi) ){
                                    if(dbHelper.insertBids(String.valueOf(amount), cust.getProfilePic(), String.valueOf(cust.getId()),cust.getDetails(),incre, maxi )) {
                                        dbHelper.updateItem(Integer.parseInt(String.valueOf(cust.getId())), cust.getDetails(),cust.getProfilePic() ,String.valueOf(amount), cust.getPeriod() );
                                        Toast.makeText(getApplicationContext(), "Bid completed", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Could not complete bid", Toast.LENGTH_SHORT).show();
                                    }
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                                }


                        }
                    });

                    alert.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                                             }
                    });

                    alert.show();

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter amount greater than the minimum price", Toast.LENGTH_SHORT).show();
                }

            }
        });




}
    private static Date addHoursToDate(int hours, Date beforeTime){
        final long ONE_HOUR_IN_MILLIS = 60000*60;//millisecs

        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (hours * ONE_HOUR_IN_MILLIS));
        return afterAddingMins;
    }

}
