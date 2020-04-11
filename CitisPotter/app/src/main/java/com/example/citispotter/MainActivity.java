package com.example.citispotter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.citispotter.sign.loginActivity;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Animation myAnim;
    public static final int SPLASHTIME=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final boolean hasLoggedIn=getBooleanFromSP(this);
        Log.i("log",""+hasLoggedIn);
        imageView=(ImageView)findViewById(R.id.citispotter_logo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (hasLoggedIn){
                    //go to home activity
                    Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);

                }else {
                    Intent intent=new Intent(MainActivity.this, loginActivity.class);
                    startActivity(intent);

                }

                finish();
            }
        },SPLASHTIME);
        myAnim= AnimationUtils.loadAnimation(MainActivity.this,R.anim.fade_in);
        imageView.startAnimation(myAnim);

    }
    public static boolean getBooleanFromSP(Context _context) {
// TODO Auto-generated method stub
        SharedPreferences preferences = _context.getSharedPreferences("PROJECTNAME", android.content.Context.MODE_PRIVATE);
        return preferences.getBoolean("ISLOGGEDIN", false);
    }//
}
