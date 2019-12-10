package com.example.citispotter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.citispotter.Fragments.AllFragment;
import com.example.citispotter.Fragments.FragmentAdd;
import com.example.citispotter.Fragments.FragmentHistory;
import com.example.citispotter.Fragments.Fragmentvideo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TabLayout tabLayout;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    androidx.fragment.app.Fragment selectedFragment=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tabLayout=(TabLayout) findViewById(R.id.tabs);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        drawer();
        bottomNavigate();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AllFragment(null)).commit();
        tablanav();

    }

    private void tablanav() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){
                    case 0:
                        selectedFragment=new AllFragment(null);
                        break;

                    case 1:
                        selectedFragment=new AllFragment("business");
                        break;
                    case 2:
                        selectedFragment=new AllFragment("entertainment");
                        break;
                    case 3:
                        selectedFragment=new AllFragment("health");
                        break;
                    case 4:
                        selectedFragment=new AllFragment("science");
                        break;
                    case 5:
                        selectedFragment=new AllFragment("sports");
                        break;
                    case 6:
                        selectedFragment=new AllFragment("technology");




                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
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
                switch(menuItem.getItemId()){

                    case R.id.newsID:
                        selectedFragment=new AllFragment(null);
                        break;
                    case R.id.videoID:
                        selectedFragment=new Fragmentvideo();
                        break;
                    case R.id.historyID:
                        selectedFragment=new FragmentHistory();
                        break;
                    case R.id.addID:
                        selectedFragment=new FragmentAdd();
                        Toast.makeText(HomeActivity.this, "You clicked add", Toast.LENGTH_SHORT).show();
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
    // for drawer
    private void drawer(){
        dl = (DrawerLayout)findViewById(R.id.drawerlayout);
        t = new ActionBarDrawerToggle(this, dl,null,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch(id)
                {
                    case R.id.home_menu:
                        selectedFragment=new AllFragment(null);
                        break;
                    case R.id.business_menu:
                        selectedFragment=new AllFragment("business");
                        break;
                    case R.id.entertainment_menu:
                        selectedFragment=new AllFragment("entertainment");
                        break;
                    case R.id.health_menu:
                        selectedFragment=new AllFragment("health");
                        break;
                    case R.id.science_menu:
                        selectedFragment=new AllFragment("science");
                        break;
                    case R.id.sports_menu:
                        selectedFragment=new AllFragment("sports");
                        break;
                    case R.id.technology_menu:

                        selectedFragment=new AllFragment("technology");
                        break;
                    case R.id.addedNews:
                        selectedFragment=new AllFragment(null);
                        Intent intent=new Intent(HomeActivity.this,addActivity.class);
                        startActivity(intent);
                    default:
                        return true;
                }
                dl.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

                return true;

            }
        });



    }
//for opening the drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

}
