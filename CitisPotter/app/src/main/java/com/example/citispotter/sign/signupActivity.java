package com.example.citispotter.sign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.citispotter.HomeActivity;
import com.example.citispotter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.citispotter.UpdateInterface.updateUI;

public class signupActivity extends AppCompatActivity {
    private FirebaseAuth mauth;
    private String email,password;
    private EditText emailText,passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mauth=FirebaseAuth.getInstance();
        emailText=(EditText)findViewById(R.id.login_email);
        passText=(EditText)findViewById(R.id.login_pass);

    }

    public void signUp(View view) {
        email=emailText.getText().toString();
        password=passText.getText().toString();
        mauth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user=mauth.getCurrentUser();
                            updateUI(user);
                            Intent intent=new Intent(signupActivity.this, HomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(signupActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(signupActivity.this, "Sign in failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
