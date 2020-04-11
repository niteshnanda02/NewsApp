package com.example.citispotter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.citispotter.Fragments.AllFragment;
import com.example.citispotter.Fragments.FragmentAdd;
import com.example.citispotter.Fragments.FragmentHistory;
import com.example.citispotter.Fragments.Fragmentvideo;
import com.example.citispotter.Model.HistoryClass;
import com.example.citispotter.sqlLiteDatabase.PreferenceDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {
    PreferenceDatabase pdatabase;
    BottomNavigationView bottomNavigationView;
    TabLayout tabLayout;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    androidx.fragment.app.Fragment selectedFragment=null;
    //for dialog;
    boolean val=false;
    AlertDialog dialog;
    Intent intent;
    String pref;
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tabLayout=(TabLayout) findViewById(R.id.tabs);
//        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        drawer();
//        bottomNavigate();
        pdatabase=new PreferenceDatabase(this);
        pref=pdatabase.getData();
        System.out.println("pref " +pref);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AllFragment(null)).commit();
        tablanav();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.app_bar_menu,menu);
        return true;
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
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

                        break;
                    case R.id.submission_menu:
                        intent=new Intent(HomeActivity.this, AddedActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.history_menu:
                        intent=new Intent(HomeActivity.this, HistoryActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.preference_menu:
                        intent=new Intent(HomeActivity.this,PreferenceActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.profile:
                        intent=new Intent(HomeActivity.this,UserActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.aboutus:
                        intent=new Intent(HomeActivity.this,AboutusActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        return true;
                }
                dl.closeDrawer(GravityCompat.START);

                return true;

            }
        });



    }
//for opening the drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //for drawer
        if(t.onOptionsItemSelected(item))
            return true;

        //for app bar menu
        switch (item.getItemId()){
            case R.id.add_menu:
                Toast.makeText(this, "Clicked add", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, AddNewsActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int count=getSupportFragmentManager().getBackStackEntryCount();
        if (count==0){

            if (val) {
                val=false;
                dialog=new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.closing_app))
                        .setMessage(getString(R.string.closing_app_message))

                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(HomeActivity.this, "Exit successfully!!", Toast.LENGTH_SHORT).show();
                                ActivityCompat.finishAffinity(HomeActivity.this);
                            }
                        })
                        .setNegativeButton(getString(R.string.no), null)
                        .show();
                //for bluring
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
            }
            else {
                val=true;
            }
        }
    }
}
