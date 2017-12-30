package com.example.application.demoinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.demoinstagram.activity.UserListActivity;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    Boolean loginModeActive = true;
    EditText passwordEditText;
    TextView changeSignUpModeTextView;

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.changeSignUpModeTextView) {
            Button button = (Button)findViewById(R.id.loginButton);

            if(loginModeActive) {
                loginModeActive = false;
                changeSignUpModeTextView.setText(R.string.loginMode);
                button.setText(R.string.signup);
            } else {
                loginModeActive = true;
                changeSignUpModeTextView.setText(R.string.signupMode);
                button.setText(R.string.login);
            }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == event.ACTION_DOWN) {
            Login(v);
        }
        return false;
    }

    public void hideKeyBoard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showUserList() {
        Intent intent = new Intent(MainActivity.this, UserListActivity.class);
        startActivity(intent);
    }

    public void Login(View view) {
        EditText usernameEditText = (EditText)findViewById(R.id.usernameEditText);

        if(usernameEditText.getText().toString().trim().isEmpty() && passwordEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Username/Password is required.", Toast.LENGTH_SHORT).show();
        } else {
            if(loginModeActive) {
                ParseUser.logInInBackground(usernameEditText.getText().toString().trim(), passwordEditText.getText().toString().trim(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user != null) {
                            Log.i("logInInBackground", " successfully");
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                ParseUser user = new ParseUser();
                user.setUsername(usernameEditText.getText().toString().trim());
                user.setPassword(passwordEditText.getText().toString().trim());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            Log.i("signup", " successfully");
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MainActivity", "onCreate");
        setContentView(R.layout.activity_main);

        changeSignUpModeTextView = (TextView)findViewById(R.id.changeSignUpModeTextView);
        changeSignUpModeTextView.setOnClickListener(this);

        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        passwordEditText.setOnKeyListener(this);

        ConstraintLayout constraintLayout = (ConstraintLayout)findViewById(R.id.mainLayout);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("coordinatorLayout", "setOnClickListener");
                hideKeyBoard(v);
            }
        });

        constraintLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyBoard(v);
            }
        });

        if(ParseUser.getCurrentUser() != null) {
            showUserList();
        }
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
