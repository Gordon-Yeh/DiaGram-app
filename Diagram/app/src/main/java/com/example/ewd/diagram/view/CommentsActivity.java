package com.example.ewd.diagram.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ewd.diagram.R;
import com.example.ewd.diagram.model.local.Comment;
import com.example.ewd.diagram.model.local.CommentBody;
import com.example.ewd.diagram.model.local.Post;
import com.example.ewd.diagram.model.remote.retrofit.ApiService;
import com.example.ewd.diagram.model.remote.retrofit.RetrofitClientInstance;
import com.example.ewd.diagram.utils.CommentAdapter;
import com.example.ewd.diagram.utils.PostAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity implements CommentAdapter.ListItemClickListener,  SwipeRefreshLayout.OnRefreshListener  {

    @BindView(R.id.recycler_view_comments)
    public RecyclerView mRecyclerView;

    @BindView(R.id.swipe_to_refresh)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.add_comment_layout)
    public LinearLayout addCommentLayout;

    @BindView(R.id.add_comment)
    public Button addCommentButton;

    @BindView(R.id.comment)
    public EditText commentEditText;

    private CommentAdapter mCommentAdapter;

    private String postId;
    private String sessionKey;
    private String userId;
    private String userType;
    private String postUserType;
    private String postUserId;

    //For testing
    List<Comment> commentsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");
        postUserId = intent.getStringExtra("postUserId");
        userId = intent.getStringExtra("userId");
        userType = intent.getStringExtra("userType");
        postUserType = intent.getStringExtra("postUserType");
        sessionKey = intent.getStringExtra("token");

        setupAddCommentLayout();
        setupAddCommentButton();
        initRecyclerView();


        mSwipeRefreshLayout.setOnRefreshListener(this);
        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);
                // Fetching data from server
                loadDataToRecyclerView();
            }
        });


    }


    /**
     * Method to initialize recyclerView
     */
    public void initRecyclerView() {

        //RecyclerViewDefinition
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        //Improving performance
        mRecyclerView.setHasFixedSize(true);

        mCommentAdapter = new CommentAdapter(this,this);
        mRecyclerView.setAdapter(mCommentAdapter);

    }


    public void loadDataToRecyclerView() {

        mSwipeRefreshLayout.setRefreshing(true);

        /*Create handle for the RetrofitInstance interface*/
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);

        String token = "Bearer " + sessionKey;
        Log.d("SESSION KEY", token);

        Call<List<Post>> call = service.getPost(token, postId);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (response.isSuccessful()) {

                    mSwipeRefreshLayout.setRefreshing(false);
                    mCommentAdapter.setCommentsData(response.body().get(0).getComments());
                    hideKeyboard(getParent());

                } else {

                    Toast.makeText(getApplicationContext(), "Session Expired", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {

        // Fetching data from server
        loadDataToRecyclerView();
    }

    /**
     * This method sets up the AddComment LinearLAyout
     */
    public void setupAddCommentLayout(){

        //Commenting not allowed if user type is a patient and post is not own post
        if(userType.equals("patient") && !postUserId.equals(userId)){
            addCommentLayout.setVisibility(View.GONE);
        }else{
            addCommentLayout.setVisibility(View.VISIBLE);

        }

    }

    public void setupAddCommentButton(){


        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String comment = commentEditText.getText().toString();
                if (TextUtils.isEmpty(comment)) {
                    return;
                }

                mSwipeRefreshLayout.setRefreshing(true);
                /*Create handle for the RetrofitInstance interface*/
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                String token = "Bearer " + sessionKey;

                CommentBody commentBody = new CommentBody(comment);
                Call<Post> call = service.addComment(token, postId, commentBody);

                call.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {

                        if (response.isSuccessful()) {

                            commentEditText.setText("");
                            loadDataToRecyclerView();


                        } else {

                            Toast.makeText(getApplicationContext(), "Comment failed.", Toast.LENGTH_SHORT).show();
                            mSwipeRefreshLayout.setRefreshing(false);

                        }

                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                        Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });



            }
        });

    }

    @Override
    public void onProfileClick(String userId, String userType){

        // Launch CommentsActivity adding the itemId as an extra in the intent
        Intent intent = null;

        if(userType.equals("patient"))
            intent = new Intent(getApplicationContext(), OtherPatientActivity.class);
        else
            intent = new Intent(getApplicationContext(), OtherDoctorActivity.class);

        intent.putExtra("userId", userId);
        intent.putExtra("token", sessionKey);
        startActivity(intent);

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
