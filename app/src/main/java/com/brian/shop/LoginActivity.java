package com.brian.shop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brian.shop.databases.ShopDbHelper;

/**
 * Created by omachi on 1/30/16.
 */
public class LoginActivity extends ActionBarActivity {
    EditText etUserName;
    EditText etPassword;
    Button ibLogin;
    TextView tvSignUpNow;

    ShopDbHelper dbHelper;

    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        d("Login activity born");

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        c = this;

        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        ibLogin = (Button) findViewById(R.id.ibLogin);

        ibLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d("Login button clicked");

                login();
            }
        });


    }

    private void login() {
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        d("Validating form");

        if (userName.length() == 0 || password.length() == 0) {
            Toast.makeText(c, "Please fill in username and password", Toast.LENGTH_SHORT);
            return;
        }

        d("Form validation passed");

        dbHelper = new ShopDbHelper(this);

        String storedPassword = dbHelper
                .getSingleEntry(userName);
        if (password.equals(storedPassword)) {
            Toast.makeText(LoginActivity.this,
                    "Congrats: Login Successfull", Toast.LENGTH_LONG)
                    .show();
            Intent main = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(main);
        } else {
            Toast.makeText(LoginActivity.this,
                    "User Name or Password does not match",
                    Toast.LENGTH_LONG).show();
        }
    }

   private void d(String s){
        try {
            Log.d("LoginActivity", s);
        }catch(NullPointerException ex){
            Log.d("LoginActivity", ex.getMessage());
        }
    }
}
