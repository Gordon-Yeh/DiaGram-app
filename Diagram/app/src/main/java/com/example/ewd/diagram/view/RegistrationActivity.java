package com.example.ewd.diagram.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ewd.diagram.R;
import com.example.ewd.diagram.model.local.AuthResponse;
import com.example.ewd.diagram.model.local.SignUpCredentials;
import com.example.ewd.diagram.model.local.database.UserDatabase;
import com.example.ewd.diagram.model.local.entities.User;
import com.example.ewd.diagram.model.remote.retrofit.ApiService;
import com.example.ewd.diagram.model.remote.retrofit.RetrofitClientInstance;
import com.example.ewd.diagram.utils.AppExecutors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.username)
    public EditText usernameEditText;

    @BindView(R.id.password)
    public EditText passwordEditText;

    @BindView(R.id.first_name)
    public EditText firstNameEditText;

    @BindView(R.id.last_name)
    public EditText lastNameEditText;

    @BindView(R.id.access_code)
    public EditText accessCodeEditText;

    @BindView(R.id.sign_up)
    public Button signUpButton;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String accessCode;

    private UserDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ButterKnife.bind(this);
        //Get db instance
        mDb = UserDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        setUpSignUpButton();

    }

    /**
     * Method to setup listener for Login Button
     */
    public void setUpSignUpButton() {

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                readEditTexts();

                //Checking if fields are empty
                if (TextUtils.isEmpty(firstName)) {

                    firstNameEditText.setError("Can't leave field empty.");
                    return;
                }


                if (TextUtils.isEmpty(lastName)) {

                    lastNameEditText.setError("Can't leave field empty.");
                    return;
                }

                if (TextUtils.isEmpty(username)) {

                    usernameEditText.setError("Can't leave field empty.");
                    return;
                }


                if (TextUtils.isEmpty(password)) {

                    passwordEditText.setError("Can't leave field empty.");
                    return;
                }


                if (TextUtils.isEmpty(accessCode)) {

                    accessCodeEditText.setError("Can't leave field empty.");
                    return;
                }


                SignUpCredentials signUpCredentials = new SignUpCredentials();
                signUpCredentials.setFirstName(firstName);
                signUpCredentials.setLastName(lastName);
                signUpCredentials.setUsername(username);
                signUpCredentials.setPassword(password);
                signUpCredentials.setAccessCode(accessCode);

                /*Create handle for the RetrofitInstance interface*/
                final ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                Call<AuthResponse> call = service.getSignUpResponse(signUpCredentials);
                call.enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                        if (response.isSuccessful()) {


                            AuthResponse authResponse = response.body();

                            //Adding user to local db
                            addUserToDb(authResponse.getUser());

                            goToNavigationActivity(authResponse.getJwt(), authResponse.getUser().getId(), authResponse.getUser().getUserType());


                        } else {

                            /*
                            JSONObject jsonObject;
                            JSONArray jsonArray;
                            try {
                                jsonObject = new JSONObject(response.errorBody().toString());
                                jsonArray = jsonObject.getJSONArray("errors");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            */

                            Toast.makeText(RegistrationActivity.this, "Sign up failed.", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {

                        Toast.makeText(RegistrationActivity.this, "Check your internet connection", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


    /**
     * Method that reads editText from the sign up form
     */
    public void readEditTexts() {

        username = usernameEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();
        firstName = firstNameEditText.getText().toString().trim();
        lastName = lastNameEditText.getText().toString().trim();
        accessCode = accessCodeEditText.getText().toString().trim();


    }
    /**
     * Method that navigates to the navigation Activity
     *
     * @param token
     * @param userId
     */
    public void goToNavigationActivity(String token, String userId, String userType) {

        Intent navigationIntent = new Intent(RegistrationActivity.this, NavigationActivity.class);
        navigationIntent.putExtra("token", token);
        navigationIntent.putExtra("userId", userId);
        navigationIntent.putExtra("userType", userType);

        startActivity(navigationIntent);

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
