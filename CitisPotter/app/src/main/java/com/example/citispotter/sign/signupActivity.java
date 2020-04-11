package com.example.citispotter.sign;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.citispotter.HomeActivity;
import com.example.citispotter.PreferenceActivity;
import com.example.citispotter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.NotNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.citispotter.UpdateInterface.updateUI;

public class signupActivity extends AppCompatActivity {
    private EditText email,pass,fname,lname,mobileno;
    private Button signupButton;
    String Email,Pass,Fname,Lname,Mobileno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email=(EditText)findViewById(R.id.signup_email);
        pass=(EditText)findViewById(R.id.signup_pass);
        fname=(EditText)findViewById(R.id.signup_firstname);
        lname=(EditText)findViewById(R.id.signup_lastname);
        mobileno=(EditText)findViewById(R.id.signup_mobile);
        signupButton=(Button)findViewById(R.id.sign_up);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email=email.getText().toString();
                Pass=pass.getText().toString();
                Fname=fname.getText().toString();
                Lname=lname.getText().toString();
                Mobileno=mobileno.getText().toString();
                new upload().execute(Email,Pass,Fname,Lname,Mobileno);
            }
        });


    }


    private class upload extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... strings) {
            String em=strings[0];
            String pa=strings[1];
            String fname=strings[2];
            String lname=strings[3];
            String mobileno=strings[4];
            JSONObject obj=new JSONObject();
            JSONObject info=new JSONObject();
            try {
                info.put("email",em);
                info.put("password",pa);
                info.put("firstname",fname);
                info.put("lastname",lname);
                info.put("mobileno",mobileno);
                obj.put("data",info);
                obj.put("method","user");
                Log.i("Object",obj.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, obj.toString());
            Request request = new Request.Builder()
                    .url("http://test.citispotter.com/shriprakash/dashboard/api/users/create")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull okhttp3.Call call, @NotNull Response response) throws IOException {
                    final String res=response.body().string();
                    Log.i("response", "onResponse: "+res);
                    startActivity(new Intent(signupActivity.this,loginActivity.class));

                }
            });
            return null;
        }
    }
}
