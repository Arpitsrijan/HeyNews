package com.example.newsproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class categoryRVAdapter extends RecyclerView.Adapter<categoryRVAdapter.ViewHolder> {
    ArrayList<CategoryRVModel> categoryRVModels;
    Context context;
    categoryClickInterface categoryClickInterface;

    public categoryRVAdapter(ArrayList<CategoryRVModel> categoryRVModels, Context context, categoryRVAdapter.categoryClickInterface categoryClickInterface) {
        this.categoryRVModels = categoryRVModels;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public categoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_nv_items,parent,false);
        return new categoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryRVAdapter.ViewHolder holder, int position) {
    CategoryRVModel categoryRVModel  =categoryRVModels.get(position);
    holder.categoryTV.setText(categoryRVModel.getCategory());
        Picasso.get().load(categoryRVModel.getCategoryImageUrl()).into(holder.cateroryIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickInterface.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRVModels.size();
    }
    public interface  categoryClickInterface{
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryTV;
        private ImageView cateroryIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTV=itemView.findViewById(R.id.idTVCategory);
            cateroryIV=itemView.findViewById(R.id.idIVCategory);

        }
    }
}
