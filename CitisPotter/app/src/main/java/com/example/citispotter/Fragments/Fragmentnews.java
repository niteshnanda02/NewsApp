package com.example.citispotter.Fragments;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citispotter.Adapter.MainArticleAdapter;
import com.example.citispotter.Model.Article;
import com.example.citispotter.Model.HistoryClass;
import com.example.citispotter.Model.ResponseModel;
import com.example.citispotter.R;
import com.example.citispotter.Utils.OnRecyclerViewItemClickListener;
import com.example.citispotter.WebActivity;
import com.example.citispotter.rests.ApiClient;
import com.example.citispotter.rests.ApiInterface;
import com.example.citispotter.sqlLiteDatabase.HistoryDatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragmentnews extends Fragment implements OnRecyclerViewItemClickListener {
    //for history
    HistoryDatabaseHelper db;
    private static  ArrayList<HistoryClass> list;
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
         ArrayList<HistoryClass> list=new ArrayList<>();

    }


    //for news API
    private static final String API_KEY="22f1c1d8c57f4219ac6158a18ddb447c";
    private RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;
    String str;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.newsactivity,container,false);
        db=new HistoryDatabaseHelper(getActivity());

        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child("Preference");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               str =dataSnapshot.getValue(String.class);
                System.out.println(str);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView=(RecyclerView)view.findViewById(R.id.news_activity_rv);
        setRecyclerView();
        final ApiInterface apiService= ApiClient.getClient().create(ApiInterface.class);
//        Call<ResponseModel> call=apiService.getLatestNews("techcrunch,",API_KEY);
//        Call<ResponseModel> call=apiService.getLatestNews("bbc-news,techcrunch",API_KEY);
        Call<ResponseModel> call=apiService.getNews("in",str,API_KEY);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body().getStatus().equals("ok")){
                    List<Article> articleList=response.body().getArticles();
                    if(articleList.size()>0){
                        final MainArticleAdapter mainArticleAdapter=new
                                MainArticleAdapter(articleList);
                        mainArticleAdapter.setOnRecyclerViewItemClickListener(Fragmentnews.this);
                        recyclerView.setAdapter(mainArticleAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("out",t.toString());
            }
        });
        return view;
    }
    void setRecyclerView(){
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()){
            case R.id.article_adapter_11_parent:
                //for history
                //for webActivity
                Article article=(Article)view.getTag();
                if(!TextUtils.isEmpty(article.getUrl())){
                    //for history
//                    HistoryList data=new HistoryList();
//                    data.setTitle(article.getTitle());
//                    data.setDescription(article.getDescription());
//                    data.setDate(date());
                    boolean trys =db.checkTitle(article.getTitle());
                    if (trys) {
                        boolean check = db.Insert(new HistoryClass(article.getTitle(),  date(),time(),1,article.getUrlToImage()));
                        if (check)
                            Toast.makeText(getActivity(), "Inserted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(), "Not insert", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Already there", Toast.LENGTH_SHORT).show();
                        boolean che=db.updateNoftimes(article.getTitle());
                        if (che)
                            Toast.makeText(getActivity(), "updated", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(), "not updated", Toast.LENGTH_SHORT).show();


                    }
                    //web activity
                    Log.e("Clicked url",article.getUrl());
                    Intent webActivity=new Intent(getActivity(), WebActivity.class);
                    webActivity.putExtra("url",article.getUrl());
                    startActivity(webActivity);
                }
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String date(){
        Date c= Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MMM-yyyy");
        String formatDate=dateFormat.format(c);
        return formatDate;

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String time(){
        Date c=Calendar.getInstance().getTime();
        String currtime=new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(c);
        return currtime;
    }
}
