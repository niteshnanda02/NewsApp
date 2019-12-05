package com.example.citispotter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.citispotter.Fragments.Fragmentnews;
import com.example.citispotter.Fragments.FragmentHistory;
import com.example.citispotter.Fragments.Fragmentvideo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tabLayout=(TabLayout) findViewById(R.id.tabs);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigate();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragmentnews()).commit();
        tablanav();

    }

    private void tablanav() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        Toast.makeText(HomeActivity.this,"You clicked home",Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        Intent intent=new Intent(HomeActivity.this,Buisness_Activity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        Toast.makeText(HomeActivity.this,"You clicked the wild",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(HomeActivity.this,"You clicked world",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(HomeActivity.this,"You clicked technology",Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(HomeActivity.this,"You clicked blutton",Toast.LENGTH_SHORT).show();
                        break;




                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void bottomNavigate() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment=null;
                switch(menuItem.getItemId()){

                    case R.id.newsID:
                        selectedFragment=new Fragmentnews();
                        break;
                    case R.id.videoID:
                        selectedFragment=new Fragmentvideo();
                        break;
                    case R.id.historyID:
                        selectedFragment=new FragmentHistory();
                        break;
                    case R.id.graphID:
                        selectedFragment=new Fragmentnews();
                        Toast.makeText(HomeActivity.this, "You cliked graph", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_userID:
                        Intent intent=new Intent(HomeActivity.this,UserActivity.class);
                        startActivity(intent);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                return true;
            }
        });
    }


}
