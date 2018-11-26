package com.example.ewd.diagram.view;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ewd.diagram.R;
import com.example.ewd.diagram.model.local.AuthResponse;
import com.example.ewd.diagram.model.local.LoginCredentials;
import com.example.ewd.diagram.model.local.database.UserDatabase;
import com.example.ewd.diagram.model.local.entities.User;
import com.example.ewd.diagram.model.remote.retrofit.ApiService;
import com.example.ewd.diagram.model.remote.retrofit.RetrofitClientInstance;
import com.example.ewd.diagram.utils.AppExecutors;
import com.example.ewd.diagram.utils.FragmentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.username)
    public EditText usernameEditText;

    @BindView(R.id.password)
    public EditText passwordEditText;

    @BindView(R.id.log_in)
    public Button loginButton;

    @BindView(R.id.sign_up)
    public TextView signUpTextView;

    private String username;
    private String password;

    private UserDatabase mDb;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Data Binding using butterknife
        ButterKnife.bind(this);

        sp = getSharedPreferences("login",MODE_PRIVATE);
        //If logged in , go to Navigation Activity Directly
        if(sp.getBoolean("logged",false)){
            goToNavigationActivity(
                    sp.getString("jwt",""),
                    sp.getString("userId",""),
                    sp.getString("userType","")
            );
        }

        //Get db instance
        mDb = UserDatabase.getInstance(getApplicationContext());
        //Initialize Views
        initViews();


    }


    /**
     * Method to initialize Views
     */
    public void initViews() {

        setUpLoginButton();
        setUpSignUpButton();


    }

    /**
     * Method to setup listener for Login Button
     */
    public void setUpLoginButton() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                username = usernameEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();

                //Checking if fields are empty
                if (TextUtils.isEmpty(username)) {

                    usernameEditText.setError("Can't leave field empty.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {

                    passwordEditText.setError("Can't leave field empty.");
                    return;
                }

                //Body for API call
                LoginCredentials loginCredentials = new LoginCredentials(username, password);

                /*Create handle for the RetrofitInstance interface*/
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                Call<AuthResponse> call = service.getLoginResponse(loginCredentials);
                call.enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                        if (response.isSuccessful()) {

                            AuthResponse authResponse = response.body();

                            //Adding user to local db
                            addUserToDb(authResponse.getUser());

                            //Saving Login session info
                            sp.edit().putBoolean("logged",true).apply();
                            sp.edit().putString("jwt",authResponse.getJwt()).apply();
                            sp.edit().putString("userId",authResponse.getUser().getId()).apply();
                            sp.edit().putString("userType",authResponse.getUser().getUserType()).apply();

                            // Going to Navigation Activity
                            goToNavigationActivity(authResponse.getJwt(), authResponse.getUser().getId(),
                                    authResponse.getUser().getUserType());

                        } else {

                            Toast.makeText(MainActivity.this, "Login Failed.", Toast.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {

                        Toast.makeText(MainActivity.this, "Check your internet connection", Toast.LENGTH_LONG).show();
                    }
                });
            }

        });
    }


    /**
     * Method to setup listener for sign up TextView
     */
    public void setUpSignUpButton() {

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Going to RegistrationActivity

                Intent registrationIntent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(registrationIntent);


            }
        });
    }

    /**
     * Method that user to DB
     *
     * */
    public void addUserToDb(final User user){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                mDb.userDao().insertUser(user);
            }
        });

    }


    /**
     * Method that navigates to the navigation Activity
     *
     * @param token
     * @param userId
     */
    public void goToNavigationActivity(String token, String userId, String userType) {

        Intent navigationIntent = new Intent(MainActivity.this, NavigationActivity.class);
        navigationIntent.putExtra("token", token);
        navigationIntent.putExtra("userId", userId);
        navigationIntent.putExtra("userType", userType);

        startActivity(navigationIntent);

    }

    /**
     * Hiding keyboard when pressed anywhere else on the screen
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }


}

