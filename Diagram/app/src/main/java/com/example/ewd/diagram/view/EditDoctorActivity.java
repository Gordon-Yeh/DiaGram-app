package com.example.ewd.diagram.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.ewd.diagram.R;
import com.example.ewd.diagram.model.local.Post;
import com.example.ewd.diagram.model.local.PostBody;
import com.example.ewd.diagram.model.local.database.UserDatabase;
import com.example.ewd.diagram.model.local.entities.User;
import com.example.ewd.diagram.model.remote.retrofit.ApiService;
import com.example.ewd.diagram.model.remote.retrofit.RetrofitClientInstance;
import com.example.ewd.diagram.utils.AppExecutors;
import com.example.ewd.diagram.viewmodel.UserViewModel;
import com.example.ewd.diagram.viewmodel.UserViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDoctorActivity extends AppCompatActivity {


    @BindView(R.id.user_type_image_view)
    ImageView userTypeImageView;

    @BindView(R.id.first_name)
    EditText firstNameEditText;

    @BindView(R.id.last_name)
    EditText lastNameEditText;

    @BindView(R.id.experience_body)
    EditText experienceEditTextView;

    @BindView(R.id.specializations_body)
    EditText specializationsEditTextView;

    @BindView(R.id.department_body)
    EditText departmentEditTextView;

    @BindView(R.id.edit)
    Button editButton;

    private User user;
    private String firstName;
    private String lastName;
    private String experience;
    private String specializations;
    private String department;


    // Member variable for the Database
    private UserDatabase mDb;
    private String userId;
    private String sessionKey;

    private int[] imgs = {R.mipmap.patient, R.mipmap.doctor};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        sessionKey = intent.getStringExtra("token");

        //Get Database instance
        mDb = UserDatabase.getInstance(getApplicationContext());

        UserViewModelFactory factory = new UserViewModelFactory(mDb, userId);
        final UserViewModel viewModel
                = ViewModelProviders.of(this, factory).get(UserViewModel.class);

        // Observe the LiveData object in the ViewModel. Use it also when removing the observer
        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User userEntry) {

                populateUI(userEntry);
            }
        });

        setupEditButton();
    }



    /**
     * populateUI would be called to populate the UI w
     *
     * @param user the taskEntry to populate the UI
     */
    private void populateUI(User user) {
        if (user == null) {
            return;

        }
        this.user = user;
        userTypeImageView.setImageResource(imgs[1]);

        firstNameEditText.setText(user.getFirstName());
        lastNameEditText.setText(user.getLastName());
        experienceEditTextView.setText(user.getExperience());
        specializationsEditTextView.setText(user.getSpecializations());
        departmentEditTextView.setText(user.getDepartment());

    }


    /**
     * Method to setup edit button
     */
    public void setupEditButton() {

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addUserToRemoteAndDb();
            }
        });

    }

    /**
     * Method that retrieves user input and inserts put user to remote and local db if success
     */
    public void addUserToRemoteAndDb() {

        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String experience = experienceEditTextView.getText().toString();
        String specializations = specializationsEditTextView.getText().toString();
        String department = departmentEditTextView.getText().toString();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setExperience(experience);
        user.setSpecializations(specializations);
        user.setDepartment(department);


        /*Create handle for the RetrofitInstance interface*/
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);

        String token = "Bearer " + sessionKey;
        Log.d("SESSION KEY", token);

        Call<User> call = service.editProfile(token, user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {

                    addUserToDb(response.body());

                } else {

                    Toast.makeText(getApplicationContext(), "Edit Failed", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();

            }
        });

    }

    /**
     * Method that inserts user to local db and exit
     */
    public void addUserToDb(final User user) {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                    mDb.userDao().insertUser(user);

                finish();
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
