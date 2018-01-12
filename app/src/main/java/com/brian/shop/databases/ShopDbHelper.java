package com.brian.shop.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.brian.shop.BidInfo;
import com.brian.shop.ItemInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by john on 7/24/15.
 */
public class ShopDbHelper extends SQLiteOpenHelper {


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION =10;
    public static final String DATABASE_NAME = "Shop.db";
    private SQLiteDatabase database;
    public ShopDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        database = db;
        db.execSQL(ShopDbContract.User.SQL_CREATE_TABLE);
        db.execSQL(ShopDbContract.Bids.SQL_CREATE_TABLE);
        db.execSQL(ShopDbContract.Items.SQL_CREATE_TABLE);
        db.execSQL(ShopDbContract.Userbot.SQL_CREATE_TABLE);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(ShopDbContract.User.SQL_DELETE_TABLE);
        db.execSQL(ShopDbContract.Bids.SQL_DELETE_TABLE);
        db.execSQL(ShopDbContract.Items.SQL_CREATE_TABLE);
        db.execSQL(ShopDbContract.Userbot.SQL_CREATE_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public boolean insertUser(String name, String country, String email, String phoneNumber, String profPic, String password
             ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShopDbContract.User.COLUMN_NAME_NAME, name);
        contentValues.put(ShopDbContract.User.COLUMN_NAME_PROFILE_PICTURE, profPic);
        contentValues.put(ShopDbContract.User.COLUMN_NAME_EMAIL, email);
        contentValues.put(ShopDbContract.User.COLUMN_NAME_PHONENUMBER, phoneNumber);
        contentValues.put(ShopDbContract.User.COLUMN_NAME_PASSWORD, password);
        contentValues.put(ShopDbContract.User.COLUMN_NAME_COUNTRY, country);
        db.insert(ShopDbContract.User.TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updatePerson(Integer id,String name, String country, String email, String phoneNumber, String profPic, String password
            ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShopDbContract.User.COLUMN_NAME_NAME, name);
        contentValues.put(ShopDbContract.User.COLUMN_NAME_PROFILE_PICTURE, profPic);
        contentValues.put(ShopDbContract.User.COLUMN_NAME_EMAIL, email);
        contentValues.put(ShopDbContract.User.COLUMN_NAME_PHONENUMBER,phoneNumber);
        contentValues.put(ShopDbContract.User.COLUMN_NAME_PASSWORD, password);
        contentValues.put(ShopDbContract.User.COLUMN_NAME_COUNTRY, country);
        db.update(ShopDbContract.User.TABLE_NAME, contentValues, ShopDbContract.User.COLUMN_NAME_USER_ID + " = ? ", new String[]{Integer.toString(id)});
        return true;
    }
    public Cursor getPerson(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + ShopDbContract.User.TABLE_NAME + " WHERE " +
                ShopDbContract.User.COLUMN_NAME_EMAIL + "=?", new String[] { email } );
        return res;
    }
    public Cursor getAllPersons() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + ShopDbContract.User.TABLE_NAME, null);
        return res;
    }
    public String getSingleEntry(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ShopDbContract.User.TABLE_NAME + " WHERE " +
                ShopDbContract.User.COLUMN_NAME_EMAIL + "=?", new String[]{email});
        if (cursor.getCount() < 1) {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex(ShopDbContract.User.COLUMN_NAME_PASSWORD));
        cursor.close();
        return password;
    }
    public boolean insertItem(String descripiton, String profPic, String min_price, String active_time) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShopDbContract.Items.COLUMN_NAME_DESCRIPTION, descripiton);
        contentValues.put(ShopDbContract.Items.COLUMN_NAME_PICTURE, profPic);
        contentValues.put(ShopDbContract.Items.COLUMN_NAME_MINPRICE, min_price);
        contentValues.put(ShopDbContract.Items.COLUMN_NAME_STAYTIME, active_time);

        System.out.println(contentValues);

        db.insert(ShopDbContract.Items.TABLE_NAME, null, contentValues);
        return true;
    }
    public boolean insertBids(String amount, String picture, String itemId, String description, String increment, String max_amount) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShopDbContract.Bids.COLUMN_NAME_AMOUNT, amount);
        contentValues.put(ShopDbContract.Bids.COLUMN_NAME_PICTURE, picture);
        contentValues.put(ShopDbContract.Bids.COLUMN_NAME_ITEM_ID, itemId);
        contentValues.put(ShopDbContract.Bids.COLUMN_NAME_DESCRIPTION, description);
        contentValues.put(ShopDbContract.Bids.COLUMN_NAME_INCREMENT, increment);
        contentValues.put(ShopDbContract.Bids.COLUMN_NAME_MAX_AMOUNT, max_amount);

        db.insert(ShopDbContract.Bids.TABLE_NAME, null, contentValues);
        return true;
    }
    public ArrayList<ItemInfo> getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ItemInfo> arrContacts = new ArrayList<ItemInfo>();
        ItemInfo itemInfo=null;
        Cursor res = db.rawQuery("SELECT * FROM " + ShopDbContract.Items.TABLE_NAME, null);
       res.moveToFirst();
        while (res.isAfterLast() == false)
        {
        String details = res.getString(res.getColumnIndex(ShopDbContract.Items.COLUMN_NAME_DESCRIPTION));
        String picture = res.getString(res.getColumnIndex(ShopDbContract.Items.COLUMN_NAME_PICTURE));
        String min_amount = res.getString(res.getColumnIndex(ShopDbContract.Items.COLUMN_NAME_MINPRICE));
        String active_time = res.getString(res.getColumnIndex(ShopDbContract.Items.COLUMN_NAME_STAYTIME));
        String id = res.getString(res.getColumnIndex(ShopDbContract.Items._ID));

        ItemInfo items  = new ItemInfo();
        items.setDetails(details);
        items.setProfilePic(picture);
        items.setAmount(min_amount);
        items.setPeriod(active_time);
        items.setId(id);
//        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String formattedDate = df.format(c.getTime());
        items.setPostingTime(formattedDate);


        if (items != null)
        {

            arrContacts.add(items);

              }
           res.moveToNext();
        }
        res.close();
        return arrContacts;
    }
    public Cursor getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + ShopDbContract.Items.TABLE_NAME + " WHERE " +
                ShopDbContract.Items._ID + "=?", new String[]{Integer.toString(id)});

        return res;
    }
    public ArrayList<BidInfo> getAllBids() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<BidInfo> arrContacts = new ArrayList<BidInfo>();
        BidInfo bidInfo=null;
        Cursor res = db.rawQuery( "SELECT * FROM " + ShopDbContract.Bids.TABLE_NAME, null );
        res.moveToFirst();
        while (res.isAfterLast() == false)
        {
            String details = res.getString(res.getColumnIndex(ShopDbContract.Bids.COLUMN_NAME_DESCRIPTION));
            String picture = res.getString(res.getColumnIndex(ShopDbContract.Bids.COLUMN_NAME_PICTURE));
            String min_amount = res.getString(res.getColumnIndex(ShopDbContract.Bids.COLUMN_NAME_AMOUNT));
            String item_id = res.getString(res.getColumnIndex(ShopDbContract.Bids.COLUMN_NAME_ITEM_ID));
            String id = res.getString(res.getColumnIndex(ShopDbContract.Bids._ID));
            BidInfo items  = new BidInfo();
            items.setDetails(details);
            items.setProfilePic(picture);
            items.setAmount(min_amount);
            items.setItemId(item_id);
            items.setId(Long.valueOf(id));
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String formattedDate = df.format(c.getTime());
            items.setPostingTime(formattedDate);

            if (items != null)
            {
                arrContacts.add(items);
            }
            res.moveToNext();
        }
        res.close();
        return arrContacts;
    }
    public boolean updateItem(Integer id, String descripiton, String profPic, String min_price, String active_time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ShopDbContract.Items.COLUMN_NAME_DESCRIPTION, descripiton);
        contentValues.put(ShopDbContract.Items.COLUMN_NAME_PICTURE, profPic);
        contentValues.put(ShopDbContract.Items.COLUMN_NAME_MINPRICE, min_price);
        contentValues.put(ShopDbContract.Items.COLUMN_NAME_STAYTIME, active_time);

        db.update(ShopDbContract.Items.TABLE_NAME, contentValues, ShopDbContract.Items._ID  + " = ? ", new String[]{Integer.toString(id)} );
        return true;
    }

}
