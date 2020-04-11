package com.example.citispotter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.citispotter.Adapter.MainArticleAdapter;
import com.example.citispotter.Model.Article;
import com.example.citispotter.Model.ResponseModel;
import com.example.citispotter.Utils.OnRecyclerViewItemClickListener;
import com.example.citispotter.rests.ApiClient;
import com.example.citispotter.rests.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener {
    private static final String API_KEY="22f1c1d8c57f4219ac6158a18ddb447c";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsactivity);
        recyclerView=(RecyclerView)findViewById(R.id.news_activity_rv);
        setRecyclerView();
        final ApiInterface apiService= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseModel> call=apiService.getLatestNews("techcrunch",API_KEY);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body().getStatus().equals("ok")){
                    List<Article> articleList=response.body().getArticles();
                    if(articleList.size()>0){
//                        final MainArticleAdapter mainArticleAdapter=new
//                                MainArticleAdapter(articleList);
//                        mainArticleAdapter.setOnRecyclerViewItemClickListener(NewsActivity.this);
//                        recyclerView.setAdapter(mainArticleAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("out",t.toString());
            }
        });
    }
    void setRecyclerView(){
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()){
            case R.id.article_adapter_11_parent:
                Article article=(Article)view.getTag();
                if(!TextUtils.isEmpty(article.getUrl())){
                    Log.e("Clicked url",article.getUrl());
                    Intent webActivity=new Intent(this,WebActivity.class);
                    webActivity.putExtra("url",article.getUrl());
                    startActivity(webActivity);
                }
                break;
        }
    }
}
