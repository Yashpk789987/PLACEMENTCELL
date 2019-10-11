package application.nis.com.placementcell;

import android.content.Intent;
import android.content.SharedPreferences;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.os.Bundle;

import android.widget.FrameLayout;


public class StudentHome extends AppCompatActivity {
    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        SharedPreferences SP=getSharedPreferences("stsession",0);

//        if(!(SP.contains("PASSWORD"))) {
//            Intent intent=new Intent(this,StudentLoginActivity.class);
//            startActivity(intent);
//        }

        tabLayout=(TabLayout)findViewById(R.id.simpleTabLayout);
        TabLayout.Tab Recent_Visits =tabLayout.newTab();
        Recent_Visits.setText("Recent Visits");
        Recent_Visits.setIcon(R.drawable.visit);

        tabLayout.addTab(Recent_Visits);
        TabLayout.Tab Companies =tabLayout.newTab();
        Companies.setIcon(R.drawable.comapany);
        Companies.setText("Recruiters");

        tabLayout.addTab(Companies);
        TabLayout.Tab Profile =tabLayout.newTab();
        Profile.setText("Profile");
        Profile.setIcon(R.drawable.user);
        Companies.setIcon(R.drawable.office);

        tabLayout.addTab(Profile);
        Fragment f=new RecentVisitFragement();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, f);

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment f=new RecentVisitFragement();
                switch (tab.getPosition()) {
                    case 0:
                        f=new RecentVisitFragement();
                        break;
                    case 1:
                        f=new RecruiterFragement();
                        break;
                    case 2:
                        f=new ProfileFragement();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, f);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
