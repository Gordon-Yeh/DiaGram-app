package com.example.ewd.diagram.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.ewd.diagram.R;
import com.example.ewd.diagram.model.local.AuthResponse;
import com.example.ewd.diagram.model.local.Post;
import com.example.ewd.diagram.model.local.PostsListResponse;
import com.example.ewd.diagram.model.remote.retrofit.ApiService;
import com.example.ewd.diagram.model.remote.retrofit.RetrofitClientInstance;
import com.example.ewd.diagram.utils.PostAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment implements PostAdapter.ListItemClickListener,  SwipeRefreshLayout.OnRefreshListener {

    // The fragment initialization parameters
    private static final String USER_ID = "USER_ID";
    private static final String SESSION_KEY = "SESSION_KEY";
    private static final String USER_TYPE = "USER_TYPE";


    @BindView(R.id.recycler_view_posts)
    public RecyclerView mRecyclerView;

    @BindView(R.id.swipe_to_refresh)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    private PostAdapter mPostAdapter;

    private String userId;
    private String sessionKey;
    private String userType;

    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId
     * @param sessionKey
     * @return A new instance of fragment FeedFragment.
     */

    public static FeedFragment newInstance(String userId, String sessionKey, String userType) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(USER_ID, userId);
        args.putString(SESSION_KEY, sessionKey);
        args.putString(USER_TYPE, userType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(USER_ID);
            sessionKey = getArguments().getString(SESSION_KEY);
            userType = getArguments().getString(USER_TYPE);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);

        initRecyclerView();


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        //Improving performance
        mRecyclerView.setHasFixedSize(true);

        mPostAdapter = new PostAdapter(this, getActivity());
        mRecyclerView.setAdapter(mPostAdapter);

    }

    public void loadDataToRecyclerView() {

        // Showing refresh animation before making http call
        mSwipeRefreshLayout.setRefreshing(true);

        /*Create handle for the RetrofitInstance interface*/
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);

        String token = "Bearer " + sessionKey;
        Log.d("SESSION KEY", token);

        Call<List<Post>> call = service.getAllPosts(token);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (response.isSuccessful()) {

                    mSwipeRefreshLayout.setRefreshing(false);
                    mPostAdapter.setPostsData(response.body());

                } else {

                    Toast.makeText(getActivity(), "Session Expired", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_LONG).show();
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




    @Override
    public void onListItemClick(Post post) {


        // Launch CommentsActivity adding the itemId as an extra in the intent
        Intent intent = new Intent(getActivity(), CommentsActivity.class);
        intent.putExtra("postId", post.getId());
        intent.putExtra("postUserId", post.getUserId());
        intent.putExtra("userId", userId);
        intent.putExtra("userType", userType);
        intent.putExtra("postUserType", post.getUserType());
        intent.putExtra("token", sessionKey);

        startActivity(intent);

    }

    @Override
    public void onProfileClick(String userId, String userType){

        // Launch CommentsActivity adding the itemId as an extra in the intent
        Intent intent = null;

        if(userType.equals("patient"))
            intent = new Intent(getActivity(), OtherPatientActivity.class);
        else
            intent = new Intent(getActivity(), OtherDoctorActivity.class);

        intent.putExtra("userId", userId);
        intent.putExtra("token", sessionKey);
        startActivity(intent);

    }

}
