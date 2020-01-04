package com.example.citispotter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
        imageView=(ImageView)findViewById(R.id.citispotter_logo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(MainActivity.this, loginActivity.class);
                startActivity(intent);

                finish();
            }
        },SPLASHTIME);
        myAnim= AnimationUtils.loadAnimation(MainActivity.this,R.anim.fade_in);
        imageView.startAnimation(myAnim);

    }

}
