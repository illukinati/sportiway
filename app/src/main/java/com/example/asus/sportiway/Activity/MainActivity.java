package com.example.asus.sportiway.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.asus.sportiway.Adapter.ViewPagerAdapter;
import com.example.asus.sportiway.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        mContext = MainActivity.this;

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            if(intent.getExtras().get("type").equals("join")){
                String uid = intent.getStringExtra("uid");
                intent = new Intent(this, ProfileActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
            } else if(intent.getExtras().get("type").equals("requestMerge")){
                String eventRequestId = intent.getStringExtra("eventRequestId");
                String eventTargetId = intent.getStringExtra("eventTargetId");
                intent = new Intent(this, MergeEventActivity.class);
                intent.putExtra("eventRequestId", eventRequestId);
                intent.putExtra("eventTargetId", eventTargetId);
                startActivity(intent);
            }
        }

        if (user == null) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_location:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_event:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_profile:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return true;
                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic("test");
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }
}
