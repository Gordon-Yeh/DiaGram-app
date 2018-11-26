package com.example.ewd.diagram.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ewd.diagram.R;
import com.example.ewd.diagram.model.local.database.UserDatabase;
import com.example.ewd.diagram.model.local.entities.User;
import com.example.ewd.diagram.model.remote.retrofit.ApiService;
import com.example.ewd.diagram.model.remote.retrofit.RetrofitClientInstance;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherDoctorActivity extends AppCompatActivity {

    @BindView(R.id.user_type_image_view)
    ImageView userTypeImageView;

    @BindView(R.id.username)
    TextView userNameTextView;

    @BindView(R.id.name)
    TextView nameTextView;

    @BindView(R.id.experience_body)
    TextView experienceTextView;

    @BindView(R.id.specializations_body)
    TextView specializationsTextView;

    @BindView(R.id.department_body)
    TextView departmentTextView;


    private String userId;
    private String sessionKey;

    private int[] imgs = {R.mipmap.patient, R.mipmap.doctor};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_doctor);


        ButterKnife.bind(this);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        sessionKey = intent.getStringExtra("token");

        /*Create handle for the RetrofitInstance interface*/
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);

        String token = "Bearer " + sessionKey;
        Log.d("SESSION KEY", token);

        Call<User> call = service.getProfile(token, userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {

                    populateUI(response.body());

                } else {

                    Toast.makeText(getApplicationContext(), "Get profile failed", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();

            }
        });


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
        userTypeImageView.setImageResource(imgs[1]);
        if(user.getUsername() == null)
            userNameTextView.setText("-");
        else
            userNameTextView.setText(user.getUsername());

        if(user.getFirstName() == null)
            nameTextView.setText("-");
        else
            nameTextView.setText(user.getFirstName() + " " + user.getLastName());

        experienceTextView.setText(user.getExperience());
        specializationsTextView.setText(user.getSpecializations());
        departmentTextView.setText(user.getDepartment());


    }
}
