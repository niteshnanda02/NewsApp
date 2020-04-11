package com.example.citispotter.sign;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.citispotter.UpdateInterface.updateUI;
import static com.example.citispotter.sign.details.GetUserDetails;
import static com.example.citispotter.sign.details.Setuploginforfb;
import static com.example.citispotter.sign.details.Setuploginforgoogle;

public class loginActivity extends AppCompatActivity implements Serializable {
    private static final int RC_SIGN_IN = 234;
    GoogleApiClient mgoogleApiClient;
    GoogleSignInAccount maccount;
    private FirebaseAuth mauth;
    private GoogleSignInOptions gso;
    private String email, password;
    private EditText emailText, passText;
    SignInButton signInButton;
    LoginButton loginButton;
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailText = (EditText) findViewById(R.id.login_email);
        passText = (EditText) findViewById(R.id.login_pass);
        signInButton = (SignInButton) findViewById(R.id.google_sign_in);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googlesignIn();
            }
        });
        sharedPreferences = this.getSharedPreferences("com.example.citispotter", Context.MODE_PRIVATE);
        mauth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mgoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(loginActivity.this, "You get an error!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userID=loginResult.getAccessToken().getUserId();
                Log.i("userID",userID);
                String idToken = loginResult.getAccessToken().getToken();
                Log.i("token", idToken);
                try {
                    getUserDetails(idToken, "facebook",userID,null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    public void googlesignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mgoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //for login with google
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handlesignin(task);
        }
        //for login with fb
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handlesignin(Task<GoogleSignInAccount> completetask) {
        try {
            GoogleSignInAccount account = completetask.getResult(ApiException.class);

//            String idToken=account.getIdToken();
//            Log.i("token",idToken);
            updateUI(account);
        } catch (ApiException e) {
            Log.w("TAG", "signresultfail:- " + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        String idToken = account.getIdToken();
        Log.i("token", idToken);
        getUserDetails(idToken, "google",null,account);
    }

    private void check(JSONObject jsonObject) throws JSONException {
        final String verify = jsonObject.getString("verified");
        Log.i("logver", verify);
        if (verify.equals("1")) {
            Toast.makeText(this, "Successfully login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(loginActivity.this, PreferenceActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Error in login", Toast.LENGTH_SHORT).show();

        }
    }
        /*private void FirebaseAuthWithGoogle(GoogleSignInAccount account) {
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
                                finish();
                                Toast.makeText(loginActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                updateUI(null);
                                Toast.makeText(loginActivity.this, "Sign in failure", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }*/


    public void SignIn(View view) {
        email = emailText.getText().toString();
        password = passText.getText().toString();
        mauth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mauth.getCurrentUser();

                            Intent intent = new Intent(loginActivity.this, PreferenceActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(loginActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            updateUI(null);
                            Toast.makeText(loginActivity.this, "Sign in failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signupto(View view) {
        Intent intent = new Intent(loginActivity.this, signupActivity.class);
        startActivity(intent);

    }

    public void getUserDetails(String idToken, String category,String userID,GoogleSignInAccount account) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idToken", idToken);
        editor.putString("category",category);
        editor.putString("userID",userID);
        if (category.equals("google")){
            Uri uri=account.getPhotoUrl();
            editor.putString("uri",uri.toString());
        }
        editor.commit();


        Log.i("token", idToken);
        JSONObject jsonout = new JSONObject();
        try {
            jsonout.put("name", category); // for google and use "facebook" for facebook
            jsonout.put("id_token", idToken);
            Response response;
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            final Request request = new Request.Builder()
                    .url("http://test.citispotter.com/shriprakash/dashboard/api/users/login?auth=" + jsonout.toString())
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull final Call call, @NotNull Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected Code: " + response);
                    }
                    final String resData = response.body().string();
                    loginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(resData);
                                Log.i("data",jsonObject.toString());
                                if (jsonObject.getString("verified").equals("1")) {
                                    Toast.makeText(loginActivity.this, "Successful login!!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(loginActivity.this, PreferenceActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(loginActivity.this, "Error login!!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
