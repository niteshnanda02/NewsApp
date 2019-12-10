package com.example.citispotter.Adapter;

import android.content.Context;
import android.icu.util.Calendar;
import android.icu.util.LocaleData;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citispotter.Model.Article;
import com.example.citispotter.R;
import com.example.citispotter.Utils.OnRecyclerViewItemClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Article article_model=articleArrayList.get(position);
        if (!TextUtils.isEmpty(article_model.getTitle())){
            holder.titleText.setText(article_model.getTitle());
        }
        if (!TextUtils.isEmpty(article_model.getDescription())){
            holder.DescriptionText.setText(article_model.getDescription());
        }
        if (!TextUtils.isEmpty(article_model.getUrlToImage())){
            Picasso.get().load(uri(article_model.getUrlToImage())).into(holder.imageView);
        }
        if (!TextUtils.isEmpty(article_model.getAuthor())){
            holder.author.setText(article_model.getAuthor());
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
        private ImageView imageView;
        private TextView author;
        private LinearLayout articleAdapterParentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText=(TextView)itemView.findViewById(R.id.artice_title);
            DescriptionText=(TextView)itemView.findViewById(R.id.article_desc);
            imageView=(ImageView)itemView.findViewById(R.id.article_img);
            author=(TextView)itemView.findViewById(R.id.article_author);
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String time(String d){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.ENGLISH);
        LocalDate date= LocalDate.parse(d,formatter);
        return date.toString();
    }
    public static Uri uri(String image){
        URL url;
        Uri uri= null;

        try {
            url=new URL(image);
            uri=Uri.parse(url.toURI().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }
}
