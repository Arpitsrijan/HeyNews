package com.example.newsproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class newsRVAdpter extends RecyclerView.Adapter<newsRVAdpter.ViewHolder> {
    private ArrayList<Articles> articlesArrayList;
    private Context context;

    public newsRVAdpter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public newsRVAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rv_item,parent,false);

       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull newsRVAdpter.ViewHolder holder, int position) {
    Articles articles =articlesArrayList.get(position);
    holder.subTitleTV.setText(articles.getDescription());
    holder.titleTV.setText(articles.getTitle());
    Picasso.get().load(articles.getUrlToImage()).into(holder.newsTV);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent =new Intent(context,NewsDetailActivity.class);
            intent.putExtra("title",articles.getTitle());
            intent.putExtra("content",articles.getContent());
            intent.putExtra("desc",articles.getDescription());
            intent.putExtra("url",articles.getUrl());
            intent.putExtra("image",articles.getUrlToImage());
            context.startActivity(intent);

        }
    });

    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

   public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTV,subTitleTV;
        private ImageView newsTV;
       public ViewHolder(@NonNull View itemView) {
           super(itemView);
       titleTV=itemView.findViewById(R.id.idTVNewsHeading);
       subTitleTV=itemView.findViewById(R.id.idTVNewsSubHeading);
       newsTV=itemView.findViewById(R.id.idIVNews);


       }

    }
}
