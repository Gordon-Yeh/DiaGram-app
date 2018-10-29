package com.example.ewd.diagram.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ewd.diagram.R;
import com.example.ewd.diagram.model.local.AuthResponse;
import com.example.ewd.diagram.model.local.SignUpCredentials;
import com.example.ewd.diagram.model.remote.retrofit.ApiService;
import com.example.ewd.diagram.model.remote.retrofit.RetrofitClientInstance;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ButterKnife.bind(this);

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
                            goToNAvigationActivity(authResponse.getJwt(), authResponse.getUser().getId());


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

                        Toast.makeText(RegistrationActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    /**
     * Method that reads editText from the sign up form
     */
    public void readEditTexts() {

        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        firstName = firstNameEditText.getText().toString();
        lastName = lastNameEditText.getText().toString();
        accessCode = accessCodeEditText.getText().toString();


    }
    /**
     * Method that navigates to the navigation Activity
     *
     * @param token
     * @param userId
     */
    public void goToNAvigationActivity(String token, String userId) {

        Intent navigationIntent = new Intent(RegistrationActivity.this, NavigationActivity.class);
        navigationIntent.putExtra("token", token);
        navigationIntent.putExtra("userId", userId);

        startActivity(navigationIntent);

    }


}
