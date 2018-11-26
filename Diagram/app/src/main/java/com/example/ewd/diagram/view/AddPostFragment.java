package com.example.ewd.diagram.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ewd.diagram.R;
import com.example.ewd.diagram.model.local.Post;
import com.example.ewd.diagram.model.local.PostBody;
import com.example.ewd.diagram.model.remote.retrofit.ApiService;
import com.example.ewd.diagram.model.remote.retrofit.RetrofitClientInstance;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPostFragment extends Fragment {

    // The fragment initialization parameters
    private static final String USER_ID = "USER_ID";
    private static final String SESSION_KEY = "SESSION_KEY";

    @BindView(R.id.fab)
    FloatingActionButton fabButton;

    @BindView(R.id.title)
    public EditText titleEditText;

    @BindView(R.id.post)
    public EditText postEditText;

    private String userId;
    private String sessionKey;

    private String title;
    private String post;

    public AddPostFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId Parameter 1.
     * @param sessionKey Parameter 2.
     * @return A new instance of fragment AddPostFragment.
     */
    public static AddPostFragment newInstance(String userId, String sessionKey) {
        AddPostFragment fragment = new AddPostFragment();
        Bundle args = new Bundle();
        args.putString(USER_ID, userId);
        args.putString(SESSION_KEY, sessionKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(USER_ID);
            sessionKey = getArguments().getString(SESSION_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_post, container, false);

        ButterKnife.bind(this, view);
        setupFabButton();

        return view;
    }


    /**
     * Method to setup FAB
     */
    public void setupFabButton() {

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title = titleEditText.getText().toString();
                post = postEditText.getText().toString();

                //Checking if fields are empty
                if (TextUtils.isEmpty(title)) {

                    titleEditText.setError("Can't leave field empty.");
                    return;
                }

                if (TextUtils.isEmpty(post)) {

                    postEditText.setError("Can't leave field empty.");
                    return;
                }


                /*Create handle for the RetrofitInstance interface*/
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);

                String token = "Bearer " + sessionKey;
                Log.d("SESSION KEY", token);

                PostBody postBody = new PostBody(title, post);
                Call<Post> call = service.addNewPost(token, postBody);

                call.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {

                        if (response.isSuccessful()) {

                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            titleEditText.setText("");
                            postEditText.setText("");

                        } else {

                            Toast.makeText(getActivity(), "Post Failed", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                        Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }



    /**
     * Hiding keyboard when pressed anywhere else on the screen
     */



}
