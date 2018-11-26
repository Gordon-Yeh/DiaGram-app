package com.example.ewd.diagram.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ewd.diagram.R;
import com.example.ewd.diagram.utils.CustomViewPager;
import com.example.ewd.diagram.utils.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    public BottomNavigationView navigation;

    @BindView(R.id.viewpager)
    public CustomViewPager viewPager;

    private ViewPagerAdapter viewPagerAdapter;

    private String token;
    private String userId;
    private String userType;

    SharedPreferences sp;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    viewPager.setCurrentItem(0);

                    return true;
                case R.id.navigation_timeline:

                    viewPager.setCurrentItem(1);

                    return true;
                case R.id.navigation_profile:

                    viewPager.setCurrentItem(2);

                    return true;
                case R.id.navigation_post:

                    viewPager.setCurrentItem(3);

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        ButterKnife.bind(this);
        setupToolbar();

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        userId = intent.getStringExtra("userId");
        userType = intent.getStringExtra("userType");

        setupViewPagerAdapter();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    /**
     * Method that sets up ViewPagerAdapter
     */
    private void setupViewPagerAdapter() {
        viewPagerAdapter = new ViewPagerAdapter(NavigationActivity.this.getSupportFragmentManager());

        //Creating instances of all fragments
        FeedFragment feedFragment = FeedFragment.newInstance(userId, token, userType);
        TimelineFragment timelineFragment = TimelineFragment.newInstance(userId, token, userType);
        UserFragment userFragment = UserFragment.newInstance(userId, token);
        DoctorFragment doctorFragment = DoctorFragment.newInstance(userId, token);
        AddPostFragment addPostFragment = AddPostFragment.newInstance(userId, token);


        //Add all fragments to viewpager adapter
        viewPagerAdapter.addFragment(feedFragment, "Feed");
        viewPagerAdapter.addFragment(timelineFragment, "Timeline");

        if (userType.equals("patient"))
            viewPagerAdapter.addFragment(userFragment, "User");
        else if (userType.equals("doctor"))
            viewPagerAdapter.addFragment(doctorFragment, "User");

        viewPagerAdapter.addFragment(addPostFragment, "Add post");

        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(viewPagerAdapter);

    }

    /**
     * Method to setup Toolbar
     */
    public void setupToolbar(){

        //Seeting up app logo on toolbar
        getSupportActionBar().setTitle(" Diagram");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_local_hospital_dark_24dp);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.login, menu);

        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) {

            //Setting logged in flag to false
            sp = getSharedPreferences("login",MODE_PRIVATE);
            sp.edit().putBoolean("logged",false).apply();

            Intent intent = new Intent(NavigationActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

            return true;
        }

        if (id == R.id.about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
