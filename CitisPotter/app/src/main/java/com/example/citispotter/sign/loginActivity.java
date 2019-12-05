package com.example.citispotter.sign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.citispotter.HomeActivity;
import com.example.citispotter.PreferenceActivity;
import com.example.citispotter.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.Serializable;
import java.util.Arrays;

import static com.example.citispotter.UpdateInterface.updateUI;

public class loginActivity extends AppCompatActivity implements Serializable{
    private static final int RC_SIGN_IN=234;
    GoogleApiClient mgoogleApiClient;
    GoogleSignInAccount maccount;
    private FirebaseAuth mauth;
    private GoogleSignInOptions gso;
    private String email,password;
    private EditText emailText,passText;
    SignInButton signInButton;
    LoginButton loginButton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailText=(EditText)findViewById(R.id.login_email);
        passText=(EditText)findViewById(R.id.login_pass);
        signInButton=(SignInButton)findViewById(R.id.google_sign_in);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        mauth=FirebaseAuth.getInstance();
        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mgoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(loginActivity.this, "You get an error!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager=CallbackManager.Factory.create();
        loginButton=(LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));

    }
    public void signIn(){
        Intent signInIntent=Auth.GoogleSignInApi.getSignInIntent(mgoogleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //for login with fb
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
        //for login with google
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account=task.getResult(ApiException.class);
                FirebaseAuthWithGoogle(account);
            }catch (ApiException e){
                Log.e("Login","Google sign is failed"+e);
            }
        }
    }

    private void FirebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("LoginActivity","Firebasewithgoogle"+account.getId());
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user=mauth.getCurrentUser();
                            updateUI(user);
                            Intent intent=new Intent(loginActivity.this, PreferenceActivity.class);
                            startActivity(intent);
                            Toast.makeText(loginActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            updateUI(null);
                            Toast.makeText(loginActivity.this, "Sign in failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void SignIn(View view) {
        email=emailText.getText().toString();
        password=passText.getText().toString();
        mauth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user=mauth.getCurrentUser();
                            updateUI(user);

                            Intent intent=new Intent(loginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(loginActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            updateUI(null);
                            Toast.makeText(loginActivity.this, "Sign in failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signupto(View view) {
        Intent intent=new Intent(loginActivity.this, signupActivity.class);
        startActivity(intent);

    }


    public void loginwithfb(View view) {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(loginActivity.this, "User Cancel it", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(loginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential credential= FacebookAuthProvider.getCredential(accessToken.getToken());
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user=mauth.getCurrentUser();
                            updateUI(user);
                            Intent intent=new Intent(loginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(loginActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            updateUI(null);
                            Toast.makeText(loginActivity.this, "Sign in failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
