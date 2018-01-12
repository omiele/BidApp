package com.brian.shop.databases;

import android.provider.BaseColumns;

/**
 * Created by omachi on 1/30/16.
 */
public final class ShopDbContract {
    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String REAL_TYPE = " REAL";
    public static final String COMMA_SEP = ",";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ShopDbContract(){

    }

    /* Inner class that defines the table contents */
    public static abstract class User implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_USER_ID = "userId";
        public static final String COLUMN_NAME_PROFILE_PICTURE = "profilePicture";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PHONENUMBER = "phoneNumber";
        public static final String COLUMN_NAME_PASSWORD= "password";
        public static final String COLUMN_NAME_COUNTRY= "country";





        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + User.TABLE_NAME + " (" +
                        User._ID + " INTEGER PRIMARY KEY," +
                        User.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                        User.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                        User.COLUMN_NAME_PASSWORD + TEXT_TYPE + COMMA_SEP +
                        User.COLUMN_NAME_COUNTRY + TEXT_TYPE + COMMA_SEP +
                        User.COLUMN_NAME_PHONENUMBER + TEXT_TYPE + COMMA_SEP +
                        User.COLUMN_NAME_PROFILE_PICTURE + TEXT_TYPE + COMMA_SEP +
                        User.COLUMN_NAME_USER_ID + TEXT_TYPE+
                        " )";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + User.TABLE_NAME;

        public static final String SQL_DELETE_ALL_RECORDS =
                "DELETE FROM " + User.TABLE_NAME;
    }

    public static abstract class Bids implements BaseColumns {
        public static final String TABLE_NAME = "Bids";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_MAX_AMOUNT = "max_amount";
        public static final String COLUMN_NAME_TIME_OF_BID = "timeOfTransaction";
        public static final String COLUMN_NAME_PICTURE = "picture";
        public static final String COLUMN_NAME_ITEM_ID  = "itemId";
        public static final String COLUMN_NAME_DESCRIPTION  = "description";
        public static final String COLUMN_NAME_INCREMENT  = "increment";




        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + Bids.TABLE_NAME + " (" +
                        Bids._ID + " INTEGER PRIMARY KEY," +
                        Bids.COLUMN_NAME_AMOUNT                  + REAL_TYPE     + COMMA_SEP +
                        Bids.COLUMN_NAME_MAX_AMOUNT                  + REAL_TYPE     + COMMA_SEP +
                        Bids.COLUMN_NAME_TIME_OF_BID    + INTEGER_TYPE  + COMMA_SEP +
                        Bids.COLUMN_NAME_PICTURE                    + TEXT_TYPE     + COMMA_SEP +
                        Bids.COLUMN_NAME_DESCRIPTION                    + TEXT_TYPE     + COMMA_SEP +
                        Bids.COLUMN_NAME_INCREMENT                   + TEXT_TYPE     + COMMA_SEP +
                        Bids.COLUMN_NAME_ITEM_ID                   + TEXT_TYPE     +

                       " )";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + Bids.TABLE_NAME;

        public static final String SQL_DELETE_ALL_RECORDS =
                "DELETE FROM " + Bids.TABLE_NAME;
    }
    public static abstract class Items implements BaseColumns {
        public static final String TABLE_NAME = "Items";
        public static final String COLUMN_NAME_PICTURE = "picture";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_MINPRICE = "minimum_price";
        public static final String COLUMN_NAME_STAYTIME = "active_time";




        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + Items.TABLE_NAME + " (" +
                       Items._ID + " INTEGER PRIMARY KEY," +
                        Items.COLUMN_NAME_PICTURE                   + REAL_TYPE     + COMMA_SEP +
                        Items.COLUMN_NAME_MINPRICE                   + REAL_TYPE     + COMMA_SEP +
                        Items.COLUMN_NAME_STAYTIME                  + REAL_TYPE     + COMMA_SEP +
                        Items.COLUMN_NAME_DESCRIPTION    + TEXT_TYPE  +

                        " )";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + Items.TABLE_NAME;

        public static final String SQL_DELETE_ALL_RECORDS =
                "DELETE FROM " + Items.TABLE_NAME;
    }
    public static abstract class Userbot implements BaseColumns {
        public static final String TABLE_NAME = "userbot";
        public static final String COLUMN_NAME_INCREMENT_VALUE = "increment_value";
        public static final String COLUMN_NAME_MAXVALUE = "maxvalue";
        public static final String COLUMN_NAME_START_VALUE = "start_value";




        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + Userbot.TABLE_NAME + " (" +
                        Userbot._ID + " INTEGER PRIMARY KEY," +
                        Userbot.COLUMN_NAME_INCREMENT_VALUE                  + INTEGER_TYPE     + COMMA_SEP +
                        Userbot.COLUMN_NAME_MAXVALUE                   + INTEGER_TYPE     + COMMA_SEP +
                        Userbot.COLUMN_NAME_START_VALUE    + INTEGER_TYPE  +

                        " )";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + Userbot.TABLE_NAME;

        public static final String SQL_DELETE_ALL_RECORDS =
                "DELETE FROM " + Userbot.TABLE_NAME;
    }


}
