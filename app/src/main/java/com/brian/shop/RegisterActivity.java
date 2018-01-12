package com.brian.shop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brian.shop.databases.ShopDbHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by omachi on 1/30/16.
 */
public class RegisterActivity extends ActionBarActivity {
    TextView tvLoginNow;
    ImageView image;
    EditText etFirstName,
            etLastName,
            etEmail,
            etPassword,
            etPhoneNumber,
            etConfirmPassword;
    EditText auResidenceCountry;
    Button bnRegister;
    Context c;
    ShopDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        d("Register activity born");


        c = this;

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        tvLoginNow = (TextView) findViewById(R.id.tvLoginNow);


        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        auResidenceCountry=(EditText)findViewById(R.id.autoResidenceCountry);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

        bnRegister = (Button) findViewById(R.id.bnRegister);

        tvLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d("Login now clicked");

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        bnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

   private void register(){



        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String password2 = etConfirmPassword.getText().toString();
        final String residenceCountry = auResidenceCountry.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String name = firstName+" "+lastName;
        String Profpic = "picture";


        d("Doing form validation");

        if(firstName.length() < 2 || lastName.length() < 2){
            Toast.makeText(c, "First name or last name too short!", Toast.LENGTH_SHORT);
            return;
        }

        if(email.isEmpty()){
            Toast.makeText(c, "Email is invalid", Toast.LENGTH_SHORT);
            return;
        }

        if(residenceCountry.isEmpty()){
            Toast.makeText(c, "Residence Country required!", Toast.LENGTH_SHORT);
            return;
        }
        if(phoneNumber.isEmpty()){
            Toast.makeText(c, "Phone Number required!",Toast.LENGTH_SHORT );
            return;
        }
        if (!isPhoneNumberCorrect(phoneNumber)) {
            Toast.makeText(c, "Invalid phone number", Toast.LENGTH_SHORT);
            return;
        }

        //Ensure that password id strong

        if(password.length() < 5){
            Toast.makeText(c, "Your password is too short! It must be at least 5 characters.", Toast.LENGTH_SHORT);
            return;
        }

        //Make sure the passwords match

        if(!password.equals(password2)){
            Toast.makeText(c, "Your passwords do not match!", Toast.LENGTH_SHORT);
            return;
        }



        d("Form validation passed");
//        Make the registration
       dbHelper = new ShopDbHelper(this);

       if(dbHelper.insertUser(name, residenceCountry, email, phoneNumber, Profpic, password)) {
           Toast.makeText(getApplicationContext(), "User Inserted", Toast.LENGTH_SHORT).show();
       }
       else{
           Toast.makeText(getApplicationContext(), "Could not Insert user", Toast.LENGTH_SHORT).show();
       }
       Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       startActivity(intent);
   }

    private boolean isPhoneNumberCorrect(String pPhoneNumber) {

        Pattern pattern = Pattern
                .compile("((\\+[1-9]{3,4}|0[1-9]{4}|00[1-9]{3})?)?\\d{8,13}");
        Matcher matcher = pattern.matcher(pPhoneNumber);

        if (matcher.matches()) return true;


        return false;
    }

    private void d(String s){
        try {
            Log.d("RegisterActivity", s);
        }catch(NullPointerException e){
            Log.d("RegisterActivity", e.getMessage());
        }
    }





}
