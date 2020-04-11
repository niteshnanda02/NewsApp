package com.example.citispotter.Fragments;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citispotter.Adapter.MainArticleAdapter;
import com.example.citispotter.Fragments.retrofit.JsonStanceApi;
import com.example.citispotter.Fragments.retrofit.RetrofitClient;
import com.example.citispotter.Fragments.retrofit.stanceClass;
import com.example.citispotter.Model.Article;
import com.example.citispotter.Model.HistoryClass;
import com.example.citispotter.Model.ResponseModel;
import com.example.citispotter.Model.StanceModel;
import com.example.citispotter.R;
import com.example.citispotter.Utils.OnRecyclerViewItemClickListener;
import com.example.citispotter.sqlLiteDatabase.HistoryDatabaseHelper;
import com.example.citispotter.sqlLiteDatabase.PreferenceDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllFragment extends androidx.fragment.app.Fragment implements OnRecyclerViewItemClickListener {
    //for history
    HistoryDatabaseHelper db;
    private static ArrayList<HistoryClass> list;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<HistoryClass> list = new ArrayList<>();
    }

    //For preference using SQliteDAtabase
    PreferenceDatabase pd;
    //for news API
    private RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;
    String str;
    //for callNews
    callNews callNews = new callNews();
    //For category
    String category;
    //for progress bar
    ProgressBar progressBar;

    public AllFragment(String category) {
        this.category = category;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        db = new HistoryDatabaseHelper(getActivity());
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar_frag);
        pd = new PreferenceDatabase(getContext());
        str = pd.getData();
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_recView);
        setRecyclerView();
        System.out.println("cat " + category);

        Call<ResponseModel> call = callNews.callModel(category);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().getStatus().equals("ok")) {
                    List<Article> articleList = response.body().getArticles();
                    Log.i("list",articleList.toString());
                    if (articleList.size() > 0) {
                        final MainArticleAdapter mainArticleAdapter = new
                                MainArticleAdapter(articleList);
                        mainArticleAdapter.setOnRecyclerViewItemClickListener(AllFragment.this);
                        recyclerView.setAdapter(mainArticleAdapter);
                    }
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("out", t.toString());
            }
        });

        return view;
    }

    void setRecyclerView() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()) {
            case R.id.article_adapter_11_parent:
                //for history
                callNews.clickwebhis(db, view, getActivity());
                break;
        }
    }

    //    private List<StanceModel> addstance(final List<Article> articleList) {
//        String stance="";
//        final List<StanceModel> list=new ArrayList<>();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (Article article:articleList) {
//                    final StanceModel model=new StanceModel();
//                    model.setArticle(article);
//                    Call<stanceClass> mcall=service.getStance(article.getTitle(),article.getDescription());
//                    mcall.enqueue(new Callback<stanceClass>() {
//                        @Override
//                        public void onResponse(Call<stanceClass> call, Response<stanceClass> response) {
//                            if (!response.isSuccessful()){
//                                model.setStance("Code: "+response.code());
//                                return;
//                            }
//                            model.setStance(response.body().getStance());
//                        }
//
//                        @Override
//                        public void onFailure(Call<stanceClass> call, Throwable t) {
//                            Log.i("fail",t.getMessage());
//                        }
//                    });
//                    list.add(model);
//                }
//            }
//        });
//
//        return list;
//    }
    class stance extends AsyncTask<String, Void, String> {
        MainArticleAdapter.ViewHolder holder;

        public stance(MainArticleAdapter.ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        protected String doInBackground(String... strings) {
            String title = strings[0];
            String article = strings[1];

            return null;
        }
    }
}
