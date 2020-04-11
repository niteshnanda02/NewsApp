package com.example.citispotter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.citispotter.sign.loginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserActivity extends AppCompatActivity {
    TextView nametxt, emailtxt, fullnametxt, joiningdatetxt, lastaccesstxt;
    ImageView userImage;
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    SharedPreferences sharedPreferences;

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = this.getSharedPreferences("com.example.citispotter", Context.MODE_PRIVATE);
        String idToken = sharedPreferences.getString("idToken", "value");
        String category = sharedPreferences.getString("category", "value");
        String userID=sharedPreferences.getString("userID","value");
        String imageUri=sharedPreferences.getString("uri","value");
        Log.i("idToken", idToken + "\n" + category);
        getUserDetails(idToken, category,userID,imageUri);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setData();
        setContentView(R.layout.activity_user);

        nametxt = (TextView) findViewById(R.id.username);
        emailtxt = (TextView) findViewById(R.id.userEmail);
        fullnametxt = (TextView) findViewById(R.id.userfullname);
        joiningdatetxt = (TextView) findViewById(R.id.joiningdate);
        lastaccesstxt = (TextView) findViewById(R.id.lastaccess);
        userImage = (ImageView) findViewById(R.id.userImg);

    }

    public void getUserDetails(String idToken, final String category, final String userId, final String imageUri) {



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
                    UserActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(resData);
                                nametxt.setText(jsonObject.getString("firstname"));
                                emailtxt.setText(jsonObject.getString("email"));
                                fullnametxt.setText(jsonObject.getString("firstname") + " " + jsonObject.getString("lastname"));
                                joiningdatetxt.setText(jsonObject.getString("joiningdate"));
                                lastaccesstxt.setText(jsonObject.getString("lastaccess"));
//                                String image = "http://test.citispotter.com/shriprakash/dashboard/" + jsonObject.getString("dp");
//                                Log.i("image", image);

                                if (category.equals("google")){
                                    Picasso.get().load(uploadimage(imageUri)).into(userImage);

                                }else if(category.equals("facebook")){
//                                    URL img_value = new URL("https://graph.facebook.com/" + userId + "/picture?type=large");
//
//                                    Bitmap mIcon = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
//                                    userImage.setImageBitmap(mIcon);
                                    Picasso.get().load("https://graph.facebook.com/" + userId + "/picture?type=normal").into(userImage);
//                                    uploadimage("https://graph.facebook.com/" + userId + "/picture?type=large");
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
    private static Uri uploadimage(String image){
        URL url;
        Uri uri = null;

        try {
            url = new URL(image);
            uri = Uri.parse(url.toURI().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }
}
