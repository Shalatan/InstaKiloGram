/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

  Boolean signUpModeActive = true;
  TextView logInTextView;
  EditText passwordEditText;
  EditText userNameEditText;


  public void showUserList()
  {
    Intent intent = new Intent(getApplicationContext(),userListActivity.class);
    startActivity(intent);
  }

  @Override
  public boolean onKey(View view, int keyCode, KeyEvent keyEvent)
  {
    if(keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == keyEvent.ACTION_DOWN)
    {
      signUpClicked(view);
    }
    return false;
  }

  @Override
  public void onClick(View v) {
    if(v.getId() == R.id.logInTextView)
    {
      Button signUpButton = findViewById(R.id.signUpButton);
      if(signUpModeActive)
      {
        signUpModeActive = false;
        signUpButton.setText("Log In");
        logInTextView.setText("or Sign Up");
      }
      else
      {
        signUpModeActive = true;
        signUpButton.setText("Sign Up");
        logInTextView.setText("or Log In");
      }
    }
    else if(v.getId() == R.id.logoImageView || v.getId() == R.id.backgroundLayout)
    {
      InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }
  }

  public void signUpClicked(View view)
  {
    if(userNameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches(""))
    {
      Toast.makeText(this,"A Username and Password required",Toast.LENGTH_SHORT).show();
    }
    else {
      if (signUpModeActive)
      {
        ParseUser user = new ParseUser();
        user.setUsername(userNameEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e)
          {
            if (e == null)
            {
              showUserList();
            }
            else
              {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
              }
          }
        });
      }
      else
      {
        ParseUser.logInInBackground(userNameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e)
          {
            if(user != null)
            {
              showUserList();
            }
            else
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
          }
        });
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setTitle("InstaKilogram");

    userNameEditText = findViewById(R.id.usernameTextView);
    passwordEditText = findViewById(R.id.passwordTextView);
    logInTextView = findViewById(R.id.logInTextView);
    logInTextView.setOnClickListener(this);
    passwordEditText.setOnKeyListener(this);
    ImageView logoImageView = findViewById(R.id.logoImageView);
    RelativeLayout backgroundLayout = findViewById(R.id.backgroundLayout);
    logoImageView.setOnClickListener(this);
    backgroundLayout.setOnClickListener(this);

    if(ParseUser.getCurrentUser() != null)
    {
      showUserList();
    }

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}