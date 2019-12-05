package com.example.citispotter.Adapter;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citispotter.Model.HistoryClass;
import com.example.citispotter.R;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.http.Url;

public class HistoryArticleAdapter extends RecyclerView.Adapter<HistoryArticleAdapter.ViewHolder> {

   ArrayList<HistoryClass> historyList;
   public HistoryArticleAdapter(ArrayList<HistoryClass> list){
       this.historyList=list;

   }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
   }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tit.setText(""+historyList.get(position).getTitle());
        holder.date.setText(""+historyList.get(position).getDate());
        holder.last.setText(""+historyList.get(position).getLasttime());
        holder.nof.setText(""+historyList.get(position).getNooftime());
        String image=""+historyList.get(position).getImage();
        URL url;
        Uri uri=null;
        try {
            url=new URL(image);
            uri=Uri.parse(url.toURI().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Picasso.get().load(uri).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tit,date,last,nof;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tit=(TextView)itemView.findViewById(R.id.history_title);
            date=(TextView)itemView.findViewById(R.id.history_date);
            last=(TextView)itemView.findViewById(R.id.history_lasttime);
            nof=(TextView)itemView.findViewById(R.id.history_nooftime);
            img=(ImageView)itemView.findViewById(R.id.hist_img);

        }
    }


}
