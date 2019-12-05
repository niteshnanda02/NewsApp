package com.example.citispotter.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citispotter.Model.Article;
import com.example.citispotter.R;
import com.example.citispotter.Utils.OnRecyclerViewItemClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainArticleAdapter extends RecyclerView.Adapter<MainArticleAdapter.ViewHolder> {
    private List<Article> articleArrayList;
    private Context context;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    public MainArticleAdapter(List<Article> articleArrayList) {
        this.articleArrayList = articleArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news_article_adapter,parent,false);

        return new MainArticleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Article article_model=articleArrayList.get(position);
        if (!TextUtils.isEmpty(article_model.getTitle())){
            holder.titleText.setText(article_model.getTitle());
        }
        if (!TextUtils.isEmpty(article_model.getDescription())){
            holder.DescriptionText.setText(article_model.getDescription());
        }
        holder.articleAdapterParentLayout.setTag(article_model);

    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleText;
        private TextView DescriptionText;
        private LinearLayout articleAdapterParentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText=(TextView)itemView.findViewById(R.id.article_adapter_tv_title);
            DescriptionText=(TextView)itemView.findViewById(R.id.article_adapter_tv_desc);
            articleAdapterParentLayout=itemView.findViewById(R.id.article_adapter_11_parent);
            articleAdapterParentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerViewItemClickListener!=null){
                        onRecyclerViewItemClickListener.onItemClick(getAdapterPosition(),view);
                    }
                }
            });
        }

    }
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener){
        this.onRecyclerViewItemClickListener=onRecyclerViewItemClickListener;
    }
}
