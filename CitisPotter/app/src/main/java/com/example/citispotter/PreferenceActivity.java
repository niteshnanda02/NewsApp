package com.example.citispotter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.citispotter.Fragments.AllFragment;
import com.example.citispotter.sqlLiteDatabase.PreferenceDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PreferenceActivity extends AppCompatActivity implements View.OnClickListener {

    Button buis,heal,enter,sci,spo,tech,con;
    ArrayList<String> list=new ArrayList<>();
    boolean f1=true,f2=true,f3=true,f4=true,f5=true,f6=true;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference=database.getReference("Preference");
    //Using SQLiteDAtabase
    PreferenceDatabase pd;
    androidx.fragment.app.Fragment selectedFragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        buis=(Button)findViewById(R.id.buisness);
        heal=(Button)findViewById(R.id.health);
        enter=(Button)findViewById(R.id.entertainment);
        sci=(Button)findViewById(R.id.science);
        spo=(Button)findViewById(R.id.sports);
        tech=(Button)findViewById(R.id.technology);
        con=(Button)findViewById(R.id.confirmButton);
        buis.setOnClickListener(this);
        heal.setOnClickListener(this);
        enter.setOnClickListener(this);
        sci.setOnClickListener(this);
        spo.setOnClickListener(this);
        tech.setOnClickListener(this);
        con.setOnClickListener(this);
        pd=new PreferenceDatabase(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.buisness:
                if (f1){
                    list.add("business");
                    f1=false;
                    buis.setBackgroundResource(R.drawable.color_round_button);
                }else {
                    list.remove("business");
                    f1=true;
                    buis.setBackgroundResource(R.drawable.round_button);

                }
                break;
            case R.id.health:
                if (f2){
                    list.add("health");

                    f2=false;
                    heal.setBackgroundResource(R.drawable.color_round_button);
                }else {
                    list.remove("health");
                    f2=true;
                    heal.setBackgroundResource(R.drawable.round_button);
                }
                break;
            case R.id.entertainment:
                if (f3){
                    list.add("entertainment");
                    f3=false;
                    enter.setBackgroundResource(R.drawable.color_round_button);
                }else {
                    list.remove("entertainment");
                    f3=true;
                    enter.setBackgroundResource(R.drawable.round_button);
                }
                break;
            case R.id.science:
                if (f4){
                    list.add("science");
                    f4=false;
                    sci.setBackgroundResource(R.drawable.color_round_button);
                }else {
                    list.remove("science");
                    f4=true;
                    sci.setBackgroundResource(R.drawable.round_button);
                }
                break;
            case R.id.sports:
                if (f5){
                    list.add("sports");
                    f5=false;
                    spo.setBackgroundResource(R.drawable.color_round_button);
                }else {
                    list.remove("sports");
                    f5=true;
                    spo.setBackgroundResource(R.drawable.round_button);

                }
                break;
            case R.id.technology:
                if (f6){
                    list.add("technology");
                    f6=false;
                    tech.setBackgroundResource(R.drawable.color_round_button);
                }else {
                    list.remove("technology");
                    f6=true;
                    tech.setBackgroundResource(R.drawable.round_button);

                }
                break;
            case R.id.confirmButton:
                if(list.size()>=1) {
                    System.out.println(list);
                    String str = getString(list);
                    boolean check=pd.Insert(str);
                    if (check)
                        Toast.makeText(this, "Added succesfuly", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "ERROR!!", Toast.LENGTH_SHORT).show();
                    reference.setValue(str);
                }else {
                    reference.removeValue();
                }
                Intent intent=new Intent(this,HomeActivity.class);
                startActivity(intent);
                finish();
                break;

        }

    }

    private String getString(ArrayList<String> list) {
        String str=list.get(0);
        for (int i = 1; i <list.size() ; i++) {

            str+=","+list.get(i);
        }
        return str;
    }

}
