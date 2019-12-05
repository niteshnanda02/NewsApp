package com.example.citispotter;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateInterface {
    public static void updateUI(FirebaseUser user){
        if(user!=null){


            String name=user.getDisplayName();
            String email=user.getEmail();
            String UID=user.getUid();
            String phoneno=user.getPhoneNumber();
            String image=String.valueOf(user.getPhotoUrl());
            HashMap<Object,String> map=new HashMap<>();
            map.put("email",email);
            map.put("UID",UID);
            map.put("name",name);
            map.put("phoneno",phoneno);
            map.put("image",image);
            //firebase Database Instance
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            //path to stored user database name 'Users'
            DatabaseReference reference=database.getReference("Users");
            reference.child(UID).setValue(map);


        }
    }
}
