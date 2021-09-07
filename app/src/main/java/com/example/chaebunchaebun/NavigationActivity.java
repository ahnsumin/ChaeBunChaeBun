package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class NavigationActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment homefg;
    private CommunityFragment communityfg;
    private MypageFragment myfg;
    private LikeListFragment likefg;

    String userId = null;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation);
        //user_id 받기
//        Intent intent = getIntent();
//        this.userId = intent.getStringExtra("userId");
//        System.out.println("home user_id: " + userId);
        userId = "49";

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_home:
                        setFragment(0);
                        break;
                    case R.id.bottom_like:
                        setFragment(1);
                        break;
                    case R.id.bottom_community:
                        setFragment(2);
                        break;
                    case R.id.bottom_mypage:
                        setFragment(3);
                        break;
                }
                return true;
            }
        });

        homefg = new HomeFragment();
        likefg = new LikeListFragment();
        communityfg = new CommunityFragment();
        myfg = new MypageFragment();
        setFragment(0);
    }

    private void setFragment(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                homefg.getUserId(userId);
                ft.replace(R.id.bottom_frame, homefg).commitAllowingStateLoss();
                //ft.commit();
                break;
            case 1:
                likefg.getUserId(userId);
                ft.replace(R.id.bottom_frame, likefg).commitAllowingStateLoss();
                //ft.commit();
                break;
            case 2:
                communityfg.getUserId(userId);
                ft.replace(R.id.bottom_frame, communityfg).commitAllowingStateLoss();
                //ft.commit();
                break;
            case 3:
                myfg.getUserId(userId);
                ft.replace(R.id.bottom_frame, myfg).commitAllowingStateLoss();
                //ft.commit();
                break;
        }
    }
}
