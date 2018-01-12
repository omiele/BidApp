package com.brian.shop;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.brian.shop.databases.ShopDbHelper;

/**
 * Created by omachi on 1/30/16.
 */
public class PostActivity extends ActionBarActivity {
    private static final int PICK_IMAGE = 1;
    EditText description, min_price, active_time;
    private Bitmap bitmap;
    private Button btnPost;
    ImageView image;
//    String desc, picture, amount, period;

    ShopDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        description = (EditText) findViewById(R.id.edt_desc);
        active_time = (EditText) findViewById(R.id.edt_period);
        btnPost = (Button) findViewById(R.id.btn_post);
        min_price = (EditText) findViewById(R.id.edt_min_amt);
        image = (ImageView) findViewById(R.id.imageView12);
        image.setImageResource(R.drawable.phone2);

//          desc = description.getText().toString();
//         picture = null;
//         amount = min_price.getText().toString();
//         period = active_time.getText().toString();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Photo"), PICK_IMAGE);
            }
        });

        dbHelper = new ShopDbHelper(this);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d("Post button clicked");
                String desc = description.getText().toString();
                String picture = null;
                String amount = min_price.getText().toString();
                String period = active_time.getText().toString();
                if(dbHelper.insertItem(desc, picture, amount, period)) {
                    Toast.makeText(getApplicationContext(), "Item Inserted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Could not Insert item", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    String filePath = null;

                    try {
                        // OI FILE Manager
                        String filemanagerstring = selectedImageUri.getPath();

                        // MEDIA GALLERY
                        String selectedImagePath = getPath(selectedImageUri);

                        if (selectedImagePath != null) {
                            filePath = selectedImagePath;
                        } else if (filemanagerstring != null) {
                            filePath = filemanagerstring;
                        } else {
                            Toast.makeText(getApplicationContext(), "Unknown path",
                                    Toast.LENGTH_LONG).show();
                            Log.e("Bitmap", "Unknown path");
                        }

                        if (filePath != null) {
                            Toast.makeText(getApplicationContext(), "Internal function, the file path requires to be decoded",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            bitmap = null;
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Internal error",
                                Toast.LENGTH_LONG).show();
                        Log.e(e.getClass().getName(), e.getMessage(), e);
                    }
                }
                break;
            default:
        }
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }



    private void d(String s){
        Log.d("PostActivity", s);
    }


}
