package com.example.citispotter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class UserActivity extends AppCompatActivity {
    TextView nametxt,emailtxt,phonetxt;
    ImageView userImage;
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setData();
        setContentView(R.layout.activity_user);

        nametxt=(TextView)findViewById(R.id.username);
        emailtxt=(TextView)findViewById(R.id.userEmail);
        phonetxt=(TextView)findViewById(R.id.userMobile);
        userImage=(ImageView)findViewById(R.id.userImg);

    }

    private void setData() {
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("Users");
        Query query=reference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    String email=""+ds.child("email").getValue();
                    String name=""+ds.child("name").getValue();
                    String phoneno=""+ds.child("phoneno").getValue();
                    String image=""+ds.child("image").getValue();
                    URL url;
                    Uri uri= null;

                    try {
                        url=new URL(image);
                        uri=Uri.parse(url.toURI().toString());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    nametxt.setText(name);
                    emailtxt.setText(email);
                    phonetxt.setText(phoneno);
                    Picasso.get().load(uri).into(userImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
